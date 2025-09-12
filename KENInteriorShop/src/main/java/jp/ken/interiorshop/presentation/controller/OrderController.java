package jp.ken.interiorshop.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.application.service.OrderRegistService;
import jp.ken.interiorshop.common.validatior.groups.ValidGroup1;
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

	    
	    
	    
	    
	@PostMapping("/order/confirm")
    public String confirmOrder(Model model, HttpSession session,
                                  @SessionAttribute("loginUser") MemberLoginForm loginUser) {
		
        OrderForm orderForm = new OrderForm();
        

        // セッションのカート情報を OrderDetailsForm リストに変換してセット
        List<ItemForm> cartItems = (List<ItemForm>) session.getAttribute("cart");
        List<OrderDetailsForm> detailsList = new ArrayList<>();
        int totalQuantity = 0;
        
        if (cartItems != null) {
            for (ItemForm item : cartItems) {
                OrderDetailsForm details = new OrderDetailsForm();
                details.setItemId(item.getItemId());
                details.setItemQuantity(String.valueOf(item.getItemQuantity()));
                details.setSubtotal(String.valueOf(Integer.parseInt(item.getItemPrice()) * item.getItemQuantity()));
                details.setItemName(item.getItemName());
                detailsList.add(details);
                
                totalQuantity += item.getItemQuantity();
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
	    model.addAttribute("totalQuantity", totalQuantity);
	    

	    return "ordercheck"; //ordercheck.html
	}
	
	@PostMapping(value="/ordercomp")
	public String completeOrder( @Validated(ValidGroup1.class) @ModelAttribute OrderForm orderForm,
	        BindingResult result, @SessionAttribute("loginUser") MemberLoginForm form,
	        HttpSession session, Model model) throws Exception  {
		
		
		
	    // addressOption のチェック
	    if ("member".equals(orderForm.getAddressOption())) {
	        // 会員住所を使用する場合、shippingForm に入力があればエラー
	        if (orderForm.getShippingForm() != null &&
	            (orderForm.getShippingForm().getShippingName() != null && !orderForm.getShippingForm().getShippingName().isEmpty() ||
	             orderForm.getShippingForm().getShippingKana() != null && !orderForm.getShippingForm().getShippingKana().isEmpty() ||
	             orderForm.getShippingForm().getShippingphone() != null && !orderForm.getShippingForm().getShippingphone().isEmpty() ||
	             orderForm.getShippingForm().getShippingPostalCode() != null && !orderForm.getShippingForm().getShippingPostalCode().isEmpty() ||
	             orderForm.getShippingForm().getShippingAddress1() != null && !orderForm.getShippingForm().getShippingAddress1().isEmpty() ||
	             orderForm.getShippingForm().getShippingAddress2() != null && !orderForm.getShippingForm().getShippingAddress2().isEmpty() ||
	             orderForm.getShippingForm().getShippingAddress3() != null && !orderForm.getShippingForm().getShippingAddress3().isEmpty()
	            )) {
	        	
	        	model.addAttribute("message", "会員住所を使用する場合、手入力の住所は無効です");
	            return "error";
	        }
	    }
		
		if(result.hasErrors()){
	    // セッションのカート情報を OrderDetailsForm リストに変換してセット
	       List<ItemForm> cartItems = (List<ItemForm>) session.getAttribute("cart");
	       List<OrderDetailsForm> detailsList = new ArrayList<>();
	        
	       if (cartItems != null) {
	         for (ItemForm item : cartItems) {
	           OrderDetailsForm details = new OrderDetailsForm();
	           details.setItemId(item.getItemId());
	           details.setItemQuantity(String.valueOf(item.getItemQuantity()));
	           details.setSubtotal(String.valueOf(Integer.parseInt(item.getItemPrice()) * item.getItemQuantity()));
	           details.setItemName(item.getItemName());
	           detailsList.add(details);
	           }

	         orderForm.setOrderDetailsForm(detailsList);
	        }
	        
	        Integer totalExclTax = (Integer) session.getAttribute("totalExclTax");
		    Integer totalTax = (Integer) session.getAttribute("totalTax");
		    Integer totalInclTax = (Integer) session.getAttribute("totalInclTax");

		    model.addAttribute("totalExclTax", totalExclTax);
		    model.addAttribute("totalTax", totalTax);
		    model.addAttribute("totalInclTax", totalInclTax);
		    model.addAttribute("cartItemNames", cartItems);
		    model.addAttribute("orderForm", orderForm);
			
			return "ordercheck";
		}
		
		
		// サービスで注文情報を登録（発送、注文、注文詳細の3テーブル）
		orderRegistService.orderRegist(form, orderForm);
		
		// カートのセッションを破棄
	    session.removeAttribute("cart");
	    session.removeAttribute("totalExclTax");
	    session.removeAttribute("totalTax");
	    session.removeAttribute("totalInclTax");

		 return "orderComplete";
	}
	
	
}
