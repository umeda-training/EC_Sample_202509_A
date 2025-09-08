package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.domain.entity.ItemEntity;
import jp.ken.interiorshop.domain.repository.ItemSearchRepository;
import jp.ken.interiorshop.presentation.form.ItemForm;

@Service
public class ItemService {

	private ItemSearchRepository itemSearchRepository;
	private ModelMapper modelMapper;
	
	public ItemService(ItemSearchRepository itemSearchRepository, ModelMapper modelMapper) {
		this.itemSearchRepository = itemSearchRepository;
		this.modelMapper = modelMapper;
	}
	
	//ItemSearchRepositoryからDBのデータを取得するメソッドを呼び出す
	public List<ItemForm> getItemList() throws Exception {
		
		List<ItemEntity> entityList = null;
		List<ItemForm> formList = null;
		
		entityList = itemSearchRepository.getItemAllList();
		
		formList = convert(entityList);
		
		return formList;
	}
	
	//EntityからFormへ値を変換
	private List<ItemForm> convert(List<ItemEntity> entityList) {
		
		List<ItemForm> formList = new ArrayList<ItemForm>();
		
		for (ItemEntity entity : entityList) {
			ItemForm form = modelMapper.map(entity, ItemForm.class);
			formList.add(form);
		}
		
		return formList;
	}
	
	
	
	//カートに追加ボタンのURL格納
	private static final String session_cart = "";
	
	//セッションのカート情報を取得
	public List<ItemForm> getCart(HttpSession session){
		List<ItemForm> cart = (List<ItemForm>) session.getAttribute(session_cart);
		if(cart == null) {
			cart = new ArrayList<>();
			session.setAttribute(session_cart, cart);
		}
		
		return cart;
	}
	
	//カートに追加するメソッド
	public void addToCart(HttpSession session, ItemForm form) {
		List<ItemForm> cart = getCart(session);
		
		cart.add(form);
		
		session.setAttribute(session_cart, cart);
	}
	
	//カートの商品を削除するメソッド
	public void removeCart(HttpSession session, int itemId) {
		List<ItemForm> cart = getCart(session);
		List<ItemForm> newCart = new ArrayList<>();
		for (ItemForm item : cart) {
			if(!item.getItemid().equals(itemId)) {
				newCart.add(item);
			}
		}
		session.setAttribute(session_cart, newCart);
	}
		
	//カートを全削除(セッション破棄)をするメソッド
	public void clearCart(HttpSession session) {
		session.removeAttribute(session_cart);
	}
	
	
	
}
