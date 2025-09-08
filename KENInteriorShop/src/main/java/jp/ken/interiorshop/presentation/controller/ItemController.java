package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
