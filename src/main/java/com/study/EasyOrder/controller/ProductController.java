package com.study.EasyOrder.controller;

import com.study.EasyOrder.dto.ProductPageResponse;
import com.study.EasyOrder.dto.ProductResponse;
import com.study.EasyOrder.dto.ProductRequest;
import com.study.EasyOrder.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ==========================================
    // [1] 상품 등록 (Create)
    // ==========================================

    /** 상품 등록 폼 이동 */
    @GetMapping("/new")
    public String productCreateForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        return "product/create";
    }

    /** 상품 저장 처리 */
    @PostMapping("/create")
    public String productCreate(@ModelAttribute ProductRequest request) {
        productService.create(request);
        return "redirect:/products/list";
    }

    // ==========================================
    // [2] 상품 조회 (Read)
    // ==========================================

    /** 상품 관리 목록 조회 (전체) */
    @GetMapping("/list")
    public String productList(@RequestParam(defaultValue = "0") int page, Model model) {
        ProductPageResponse products = productService.findAll(page, false);
        model.addAttribute("products", products);
        return "product/list";
    }

    /** 상품 갤러리 조회 (판매중만) */
    @GetMapping("/gallery")
    public String productGallery(@RequestParam(defaultValue = "0") int page, Model model) {
        ProductPageResponse products = productService.findAll(page, true);
        model.addAttribute("products", products);
        return "product/gallery";
    }

    /** 상품 상세 조회 */
    @GetMapping("/detail/{productId}")
    public String productDetail(@PathVariable Long productId, Model model) {
        ProductResponse product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product/detail";
    }

    /** 구매 전용 상품 상세 조회 */
    @GetMapping("/gallery/{productId}")
    public String productGalleryDetail(@PathVariable Long productId, Model model) {
        ProductResponse product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product/gallery-detail";
    }

    // ==========================================
    // [3] 상품 수정 (Update)
    // ==========================================

    /** 상품 수정 폼 이동 */
    @GetMapping("/edit/{productId}")
    public String productEditForm(@PathVariable Long productId, Model model) {
        ProductResponse product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product/edit";
    }

    /** 상품 수정 처리 */
    @PostMapping("/edit/{productId}")
    public String productUpdate(@PathVariable Long productId, @ModelAttribute ProductRequest request) {
        productService.update(productId, request);
        return "redirect:/products/detail/" + productId;
    }

    // ==========================================
    // [4] 상품 관리 (Delete / Restore)
    // ==========================================

    /** 선택 상품 판매 중지 (Soft Delete) */
    @PostMapping("/delete")
    public String deleteProducts(@RequestParam(name = "productIds", required = false) List<Long> productIds,
                                 @RequestParam(defaultValue = "0") int page) {
        if (productIds != null && !productIds.isEmpty()) {
            productService.deleteProducts(productIds);
        }
        return "redirect:/products/list?page=" + page;
    }

    /** 선택 상품 판매 재개 (Restore) */
    @PostMapping("/restore")
    public String restoreProducts(@RequestParam(name = "productIds", required = false) List<Long> productIds,
                                  @RequestParam(defaultValue = "0") int page) {
        if (productIds != null && !productIds.isEmpty()) {
            productService.restoreProducts(productIds);
        }
        return "redirect:/products/list?page=" + page;
    }
}
