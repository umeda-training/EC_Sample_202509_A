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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showCart(@ModelAttribute("userLoggedIn") boolean addUserLoggedIn, Model model, HttpSession session) {
		if(addUserLoggedIn) {
			
			//現在のURLをcartに(ログイン後の画面遷移に必要)
		    session.setAttribute("currentUrl", "/cart");
		    
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
	        
	        session.setAttribute("totalExclTax", totalExclTax);
	        session.setAttribute("totalTax", totalTax);
	        session.setAttribute("totalInclTax", totalInclTax);
	
	        model.addAttribute("totalExclTax", totalExclTax);
	        model.addAttribute("totalTax", totalTax);
	        model.addAttribute("totalInclTax", totalInclTax);
	
	        return "cart";
		} else {
			return "redirect:/login";
		}
    }
	
	//カートに追加ボタン押下
	@PostMapping(value = "/cart/add")
	public String addToCart(@ModelAttribute ItemForm item, @RequestParam String redirectUrl, HttpSession session, RedirectAttributes redirectAttributes) {
		itemService.addToCart(session, item);
		
		// フラッシュ属性でメッセージを設定
		redirectAttributes.addFlashAttribute("message", item.getItemName() + " をカートに追加しました");
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
	@GetMapping("/search")
	public String searchItems(
	        @RequestParam(name = "keyword", required = false) String keyword,
	        @RequestParam(name = "categoryId", required = false) Integer categoryId,
	        Model model, HttpSession session) throws Exception {

		 	// カテゴリ一覧取得（CategoryFormのリスト）
	    	List<CategoryForm> categoryForm = itemService.getCategoryList();
	    	model.addAttribute("categoryForm", categoryForm);
	    	model.addAttribute("keyword", keyword);
	    	model.addAttribute("categoryId", categoryId);
	    	
	    	//入力判定
	    	boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
	        boolean hasCategory = categoryId != null;
	    	
	        // 両方未入力の場合のみエラー
	    	if (!hasKeyword && !hasCategory) {
	    		model.addAttribute("errorMessage", "キーワードもしくはカテゴリを入力してください");
	    		List<ItemForm> formItemList = itemService.getItemList();
	    		List<CategoryForm> forCategorymList = itemService.getCategoryList();
	    		model.addAttribute("itemForm", formItemList);
	    		model.addAttribute("categoryForm", forCategorymList);
	    		model.addAttribute("itemNewForm", new ItemForm());

	    	    // 現在のURL（簡易的）
	    	    session.setAttribute("currentUrl", "/item");
	    	    
	    	    return "item";
	    	}
		
	        // 商品検索（Entity → Form に変換）
	        List<ItemEntity> itemEntity = itemService.searchItem(keyword, categoryId);
	        List<ItemForm> itemForm = itemService.convertItemForm(itemEntity); // 既存の変換メソッドを活用

	        // カテゴリ一覧取得（CategoryFormのリスト）
	        categoryForm = itemService.getCategoryList();

	        // Modelに渡す
	        model.addAttribute("itemForm", itemForm);
	        model.addAttribute("categoryForm", categoryForm);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("categoryId", categoryId);
	        
	        //現在のURLを取得
	        session.setAttribute("currentUrl" ,"/search");
	        
	        // 検索結果が0件ならメッセージを追加
	        if (itemForm.isEmpty()) {
	            model.addAttribute("infoMessage", "該当商品はありません");
	        }

	        return "search";
	}

	
    // 商品詳細画面表示
	@GetMapping("/item/detail/{itemId}")
	public String showItemDetail(
	    @PathVariable("itemId") int itemId,
	    @RequestParam(name = "from", required = false, defaultValue = "item") String from,
	    @RequestParam(name = "keyword", required = false) String keyword,
	    @RequestParam(name = "categoryId", required = false) Integer categoryId,
	    Model model, HttpSession session) throws Exception {
		
		//現在のURLを取得
		session.setAttribute("currentUrl", "/item/detail/" + itemId);
		
		//DBから商品取得
	    ItemForm item = itemService.getItemById(itemId);
	    
	    // ---------- 追加: 税込価格を計算 ----------
	    int price = Integer.parseInt(item.getItemPrice()); // itemPriceはString型なのでintに変換
	    int taxIncludedPrice = (int) Math.floor(price * 1.1); // 消費税10%
	    model.addAttribute("taxIncludedPrice", taxIncludedPrice);
	    
	    //モデルにセット
	    model.addAttribute("item", item);
	    model.addAttribute("from", from);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("categoryId", categoryId);

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
