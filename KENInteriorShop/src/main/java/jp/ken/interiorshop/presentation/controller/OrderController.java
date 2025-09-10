package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.ItemForm;
import jp.ken.interiorshop.presentation.form.OrderForm;

@Controller
public class OrderController {

	
	
	@GetMapping(value="/order/confirm")
	public String confirmOrder(HttpSession session, Model model) {
	    Integer total = (Integer) session.getAttribute("cartTotal");
	    List<ItemForm> itemNames = (List<ItemForm>) session.getAttribute("cart");
	    
	    int totalQuantity = 0;
	    if (itemNames != null) {
	        for (ItemForm item : itemNames) {
	            totalQuantity += item.getItemQuantity(); // Itemに数量フィールドがある想定
	        }
	    }
	    
	    
	    
	    OrderForm orderForm = new OrderForm(); 
	    
	    model.addAttribute("totalQuantity", totalQuantity);
	    model.addAttribute("cartTotal", total);
	    model.addAttribute("cartItemNames", itemNames);
	    model.addAttribute("orderForm", orderForm);

	    return "ordercheck"; //ordercheck.html
	}
	
	/*@PostMapping(value="/ordercomp")
	public String completeOrder(@ModelAttribute OrderForm orderForm,
	        @SessionAttribute("loginUser") MemberForm form) {
		
		
	}*/
	
	
}
