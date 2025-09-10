package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.application.service.ItemService;
import jp.ken.interiorshop.application.service.RegistService;
import jp.ken.interiorshop.domain.entity.ItemEntity;
import jp.ken.interiorshop.presentation.form.CategoryForm;
import jp.ken.interiorshop.presentation.form.ItemForm;

@Controller
@SessionAttributes("loginUser")
public class ItemController {

	private ItemService itemService;
	private RegistService registService;
	
	public ItemController(ItemService itemService, RegistService registService) {
		this.itemService = itemService;
		this.registService = registService;
	}
	
	@GetMapping(value = "/item")
	public String showItem(Model model, HttpSession session) throws Exception {
		List<ItemForm> formItemList = itemService.getItemList();
		List<CategoryForm> forCategorymList = itemService.getCategoryList();
		model.addAttribute("itemForm", formItemList);
		model.addAttribute("categoryForm", forCategorymList);
		model.addAttribute("itemNewForm", new ItemForm());

	    // 現在のURL（簡易的）
	    session.setAttribute("currentUrl", "/item");

		return "item";
	}
	
	@GetMapping(value = "/cart")
	public String showCart(Model model, HttpSession session) {
        List<ItemForm> cartItems = itemService.getCart(session);
        model.addAttribute("cartItems", cartItems);

        // 合計金額を計算（仮）
        int totalExclTax = 0;

        for (ItemForm item : cartItems) {
            int price = Integer.parseInt(item.getItemPrice());
            int quantity = item.getItemQuantity();
            totalExclTax += price * quantity;
        }
        int totalTax = (int)(totalExclTax * 0.1);
        int totalInclTax = totalExclTax + totalTax;

        model.addAttribute("totalExclTax", totalExclTax);
        model.addAttribute("totalTax", totalTax);
        model.addAttribute("totalInclTax", totalInclTax);

        return "cart"; //
    }
	
	//カートに追加ボタン押下
	@PostMapping(value = "/cart/add")
	public String addToCart(@ModelAttribute ItemForm item, @RequestParam String redirectUrl, HttpSession session) {
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
	public String searchItems(
	        @RequestParam(name = "keyword", required = false) String keyword,
	        @RequestParam(name = "categoryId", required = false) Integer categoryId,
	        Model model) throws Exception {

	        // 商品検索（Entity → Form に変換）
	        List<ItemEntity> itemEntity = itemService.searchItem(keyword, categoryId);
	        List<ItemForm> itemForm = itemService.convertItemForm(itemEntity); // 既存の変換メソッドを活用

	        // カテゴリ一覧取得（CategoryFormのリスト）
	        List<CategoryForm> categoryForm = itemService.getCategoryList();

	        // Modelに渡す
	        model.addAttribute("itemForm", itemForm);
	        model.addAttribute("categoryForm", categoryForm);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("categoryId", categoryId);

	        return "search";
	}

	
    // 商品詳細画面表示
    @GetMapping("/item/detail/{itemId}")
    public String showItemDetail(
            @PathVariable("itemId") int itemId,
            @RequestParam(name = "from", required = false, defaultValue = "item") String from,
            Model model) throws Exception {

    	// DBから商品取得
        ItemForm item = itemService.getItemById(itemId);

        // モデルにセット
        model.addAttribute("item", item);
        model.addAttribute("from", from);

        return "itemDetails";
    }

    // 商品詳細ページからカート追加
    @PostMapping("/item/detail/{itemId}/add-to-cart")
    public String addToCartOnDetail(
            @PathVariable("itemId") int itemId,
            HttpSession session,
            Model model) throws Exception {

    	 // DBから商品取得
        ItemForm item = itemService.getItemById(itemId);

        // セッションに追加
        itemService.addToCart(session, item);

        // モデルに再セットして同じページに戻す
        model.addAttribute("item", item);
        model.addAttribute("from", "item");

        return "itemDetails";
    }
    
    

}
