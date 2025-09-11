package jp.ken.interiorshop.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.application.service.OrderRegistService;
import jp.ken.interiorshop.presentation.form.ItemForm;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;
import jp.ken.interiorshop.presentation.form.OrderDetailsForm;
import jp.ken.interiorshop.presentation.form.OrderForm;

@Controller
public class OrderController {

	
	private OrderRegistService orderRegistService;
	
	public OrderController(OrderRegistService orderRegistService) {
		this.orderRegistService = orderRegistService;
	}

	    
	    
	    
	    
	@GetMapping(value="/order/confirm")
    public String confirmOrder(Model model, HttpSession session,
                                  @SessionAttribute("loginUser") MemberLoginForm loginUser) {

        OrderForm orderForm = new OrderForm();

        // セッションのカート情報を OrderDetailsForm リストに変換してセット
        List<ItemForm> cartItems = (List<ItemForm>) session.getAttribute("cart");
        if (cartItems != null) {
            List<OrderDetailsForm> detailsList = new ArrayList<>();

            for (ItemForm item : cartItems) {
                OrderDetailsForm details = new OrderDetailsForm();
                details.setItemId(item.getItemId());
                details.setItemQuantity(String.valueOf(item.getItemQuantity()));
                details.setSubtotal(String.valueOf(item.getItemPrice()));
                detailsList.add(details);
            }

            orderForm.setOrderDetailsForm(detailsList);
        }
        
        Integer totalExclTax = (Integer) session.getAttribute("totalExclTax");
	    Integer totalTax = (Integer) session.getAttribute("totalTax");
	    Integer totalInclTax = (Integer) session.getAttribute("totalInclTax");

        // 会員住所オプション
        orderForm.setAddressOption("member");

	    model.addAttribute("totalExclTax", totalExclTax);
	    model.addAttribute("totalTax", totalTax);
	    model.addAttribute("totalInclTax", totalInclTax);
	    model.addAttribute("cartItemNames", cartItems);
	    model.addAttribute("orderForm", orderForm);

	    return "ordercheck"; //ordercheck.html
	}
	
	@PostMapping(value="/ordercomp")
	public String completeOrder(@ModelAttribute OrderForm orderForm,
	        @SessionAttribute("loginUser") MemberLoginForm form) {
		
		orderRegistService.orderRegist(form, orderForm);
		
		

		 return "order/complete";
	}
	
	
}
