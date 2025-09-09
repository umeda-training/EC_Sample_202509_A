package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.application.service.ItemService;
import jp.ken.interiorshop.presentation.form.CategoryForm;
import jp.ken.interiorshop.presentation.form.ItemForm;

@Controller
public class ItemController {

	private ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping(value = "/item")
	public String showItem(Model model, HttpSession session) throws Exception {
		List<ItemForm> formItemList = itemService.getItemList();
		List<CategoryForm> forCategorymList = itemService.getCategoryList();
		model.addAttribute("itemForm", formItemList);
		model.addAttribute("categoryForm", forCategorymList);
		model.addAttribute("itemNewForm", new ItemForm());
		
		 // ログイン判定
	    Boolean loggedIn = (session.getAttribute("user") != null);
	    model.addAttribute("userLoggedIn", loggedIn);

	    // 現在のURL（簡易的）
	    model.addAttribute("currentUrl", "/item");

		return "item";
	}
	
	@GetMapping(value = "/cart")
	public String showCart(Model model, HttpSession session) {
        List<ItemForm> cartItems = itemService.getCart(session);
        model.addAttribute("cartItems", cartItems);

        // 合計金額を計算（仮）
        int totalExclTax = cartItems.stream()
                .mapToInt(item -> Integer.parseInt(item.getItemPrice()))
                .sum();
        int totalTax = (int)(totalExclTax * 0.1);
        int totalInclTax = totalExclTax + totalTax;

        model.addAttribute("totalExclTax", totalExclTax);
        model.addAttribute("totalTax", totalTax);
        model.addAttribute("totalInclTax", totalInclTax);

        return "cart"; //
    }
	
	//カートに追加ボタン押下
	@PostMapping(value = "/cart/add")
	public String addToCart(@RequestParam String itemId, @RequestParam String itemName, @RequestParam String itemPrice, @RequestParam int itemQuantity, @RequestParam String redirectUrl, HttpSession session) {
		ItemForm item = new ItemForm();
		item.setItemId(itemId);
		item.setItemName(itemName);
		item.setItemPrice(itemPrice);
		item.setItemQuantity(itemQuantity);
		itemService.addToCart(session, item);
	
		//元のページに戻る
        return "redirect:" + redirectUrl;
	}
	
	//カート削除ボタン押下
	@PostMapping("/cart/remove")
	public String removeCart(@RequestParam("removeitemId") String itemId, HttpSession session) {
	    itemService.removeCart(session, itemId);
	    return "redirect:/cart"; 
	}
	
	//全削除ボタン押下
	@PostMapping("/cart/clear")
	public String clearCart(HttpSession session) {
	    itemService.clearCart(session); 
	    return "redirect:/cart";        
	}
	
	//カートの数量変更
	@PostMapping("/cart/updateQuantity")
public String updateQuantity(@RequestParam("itemId") String itemId, @RequestParam("itemQuantity") int itemQuantity, HttpSession session) {
	    itemService.updateQuantity(session, itemId, itemQuantity);
	    return "redirect:/cart";
	}
	
	//検索結果表示メソッドの呼び出し
	@PostMapping("/search")
	public String showSearchResult(@RequestParam("keyword") String keyword, @RequestParam("categoryId") int categoryId) throws Exception {
		itemService.searchItem(keyword, categoryId);
		return "search";
	}
}
