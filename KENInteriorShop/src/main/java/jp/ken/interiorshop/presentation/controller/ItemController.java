package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.application.service.ItemService;
import jp.ken.interiorshop.presentation.form.ItemForm;

@Controller
public class ItemController {

	private ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping(value = "/item")
	public String showItem(Model model) throws Exception {
		List<ItemForm> formList = itemService.getItemList();
		model.addAttribute("itemForm", formList);
		
		return "item";
	}
	
	//カートに追加ボタン押下
			@PostMapping(value = "/cart/add")
			public String addToCart(@RequestParam String itemId, @RequestParam String itemname, @RequestParam String itemprice, @RequestParam String redirectUrl, HttpSession session) {
				ItemForm item = new ItemForm();
				item.setItemid(itemId);
				item.setItemname(itemname);
				item.setItemprice(itemprice);
				itemService.addToCart(session, item);
			
				//元のページに戻る
		        return "redirect:" + redirectUrl;
			}
}
