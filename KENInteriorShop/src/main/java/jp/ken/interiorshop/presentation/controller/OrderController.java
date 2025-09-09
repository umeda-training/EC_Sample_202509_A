package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.ItemForm;

public class OrderController {

	
	
	@GetMapping("/order/confirm")
	public String confirmOrder(HttpSession session, Model model) {
	    Integer total = (Integer) session.getAttribute("cartTotal");
	    List<ItemForm> itemNames = (List<ItemForm>) session.getAttribute("session_cart");

	    model.addAttribute("cartTotal", total);
	    model.addAttribute("cartItemNames", itemNames);

	    return "orderConfirm"; // orderConfirm.html
	}
}
