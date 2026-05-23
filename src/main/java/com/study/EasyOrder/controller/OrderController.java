package com.study.EasyOrder.controller;

import com.study.EasyOrder.dto.OrderPageResponse;
import com.study.EasyOrder.dto.OrderRequest;
import com.study.EasyOrder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** 주문 관리 목록 조회 */
    @GetMapping("/list")
    public String orderList(@RequestParam(defaultValue = "0") int page, 
                            @RequestParam(defaultValue = "") String search,
                            org.springframework.ui.Model model) {
        OrderPageResponse orders = orderService.findAll(page, search);
        model.addAttribute("orders", orders);
        model.addAttribute("search", search);
        return "order/list";
    }

    /** 상품 구매 처리 */
    @PostMapping
    public String createOrder(@ModelAttribute OrderRequest request, 
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String search,
                              org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        orderService.create(request);
        redirectAttributes.addFlashAttribute("message", "구매가 완료되었습니다.");
        
        // 검색어와 페이지 번호를 안전하게 URL 파라미터로 추가 (자동 인코딩됨)
        redirectAttributes.addAttribute("page", page);
        if (search != null && !search.isEmpty()) {
            redirectAttributes.addAttribute("search", search);
        }
        
        return "redirect:/products/gallery/" + request.getProductId();
    }

}
