package com.study.EasyOrder.service;

import com.study.EasyOrder.dto.ProductPageResponse;
import com.study.EasyOrder.dto.ProductResponse;
import com.study.EasyOrder.dto.ProductRequest;
import com.study.EasyOrder.entity.Product;
import com.study.EasyOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    // ==========================================
    // [1] 상품 등록 (Create)
    // ==========================================

    /** 상품 저장 처리 */
    @Override
    @Transactional
    public void create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        
        // 이미지 저장 처리
        String savedImageUrl = saveImage(request.getImage());
        product.setImageUrl(savedImageUrl);
        
        product.setDeleteYn(false);
        productRepository.save(product);
    }

    // ==========================================
    // [2] 상품 조회 (Read)
    // ==========================================

    /** 상품 목록 조회 */
    @Override
    public ProductPageResponse findAll(int page, boolean activeOnly) {
        // 1. 페이징 및 정렬 조건 설정
        // PageRequest.of(페이지번호, 한_페이지당_데이터_개수, 정렬조건)
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "productId"));

        // 2. JPA가 조회 결과를 담아올 Page 객체 변수 선언
        // 데이터 목록(List)과 '전체 페이지 수', '전체 데이터 개수' 등 페이징에 필요한 메타데이터를 함께 가지고 있습니다.
        Page<Product> productPage;

        // 3. 판매 상태에 따른 분개 처리
        if (activeOnly) {
            // 판매중인 상품만 조회
            productPage = productRepository.findByDeleteYnFalse(pageable);
        } else {
            // 판매중, 판매중지 상품 모두 조회
            productPage = productRepository.findAll(pageable);
        }

        // 4. 엔티티(Entity)를 응답용 DTO(Data Transfer Object)로 변환
        // DB에서 꺼내온 productPage 안의 순수한 '상품 엔티티 목록'을 꺼내서(getContent())
        // 스트림(Stream)을 이용해 각각의 Product 엔티티를 화면이나 API 응답에 적합한 ProductResponse DTO로 변환(map)
        List<ProductResponse> content = productPage.getContent().stream()
                .map(this::convertToResponse) // 변환 메서드 호출 (Entity -> DTO) 매서드 참조
                .collect(Collectors.toList());


        // 5. 화면에서 페이징 UI를 그리기 편하도록 묶어서 반환
        // 빌더(Builder) 패턴을 사용해서 최종 응답 객체인 ProductPageResponse를 채워줍니다.
        return ProductPageResponse.builder()
                .content(content)
                .currentPage(productPage.getNumber())
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();

    }

    /** 상품 상세 조회 */
    @Override
    public ProductResponse findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + productId));

        return convertToResponse(product);
    }

    // ==========================================
    // [3] 상품 수정 (Update)
    // ==========================================

    /** 상품 수정 처리 */
    @Override
    @Transactional
    public void update(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + productId));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        
        // 이미지 수정 처리 로직
        MultipartFile newImage = request.getImage();
        if (newImage != null && !newImage.isEmpty()) {
            // 1. 새로운 이미지가 업로드된 경우: 새 이미지 저장
            product.setImageUrl(saveImage(newImage));

        } else if (request.getImageUrl() == null || request.getImageUrl().trim().isEmpty()) {
            // 2. 새 이미지가 없고, 기존 이미지 삭제 버튼을 누른 경우 (빈 값 전송): null 처리
            product.setImageUrl(null);
        }

        // 3. 그 외 (이미지 변경 안 함): 기존 product.imageUrl 유지

    }

    // ==========================================
    // [4] 상품 관리 (Delete / Restore)
    // ==========================================

    /** 선택 상품 판매 중지 (Soft Delete) */
    @Override
    @Transactional
    public void deleteProducts(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) return;
        List<Product> products = productRepository.findAllById(productIds);
        products.forEach(product -> product.setDeleteYn(true));
    }

    /** 선택 상품 판매 재개 (Restore) */
    @Override
    @Transactional
    public void restoreProducts(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) return;
        List<Product> products = productRepository.findAllById(productIds);
        products.forEach(product -> product.setDeleteYn(false));
    }

    // ==========================================
    // [기타]
    // ==========================================

    /** 이미지 파일 서버 저장 */
    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        try {
            // 폴더 자동 생성
            Path uploadDirectory = Paths.get(uploadPath);
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            // 고유 파일명 생성
            String originalFilename = image.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String savedFilename = uuid + "_" + originalFilename;

            // 물리적 파일 저장
            Path filePath = uploadDirectory.resolve(savedFilename);
            image.transferTo(filePath.toFile());

            // DB에 저장할 상대 경로 반환
            return "/upload/" + savedFilename;

        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 중 오류가 발생했습니다.", e);
        }
    }

    /** Entity를 Response DTO로 변환 */
    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .deleteYn(product.getDeleteYn())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
