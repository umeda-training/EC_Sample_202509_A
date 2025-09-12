package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.domain.entity.CategoryEntity;
import jp.ken.interiorshop.domain.entity.ItemEntity;
import jp.ken.interiorshop.domain.repository.ItemSearchRepository;
import jp.ken.interiorshop.presentation.form.CategoryForm;
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
		
		formList = convertItemForm(entityList);
		
		return formList;
	}
	
	//ItemSearchRepositoryからDBのデータを取得するメソッドを呼び出す
	public List<CategoryForm> getCategoryList() throws Exception {
		
		List<CategoryEntity> entityList = null;
		List<CategoryForm> formList = null;
		
		entityList = itemSearchRepository.getCategoryAllList();
		
		formList = convertCategoryForm(entityList);
		
		return formList;
	}
	
	//EntityからFormへ値を変換(アイテム)
	public List<ItemForm> convertItemForm(List<ItemEntity> entityList) {
		
		List<ItemForm> formList = new ArrayList<ItemForm>();
		
		for (ItemEntity entity : entityList) {
			ItemForm form = modelMapper.map(entity, ItemForm.class);
			formList.add(form);
		}
		
		return formList;
	}
	
	//EntityからFormへ値を変換(カテゴリー)
	public List<CategoryForm> convertCategoryForm(List<CategoryEntity> entityList) {
		
		List<CategoryForm> formList = new ArrayList<CategoryForm>();
		
		for (CategoryEntity entity : entityList) {
			CategoryForm form = modelMapper.map(entity, CategoryForm.class);
			formList.add(form);
		}
		
		return formList;
	}
	
	//カート処理
	//カートに追加ボタンのURL格納
		private static final String session_cart = "cart";
		
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

		    boolean found = false;
		    for (ItemForm item : cart) {
		        if (item.getItemId().equals(form.getItemId())) {
		            // 同じ商品なら数量を加算
		            item.setItemQuantity(item.getItemQuantity() + form.getItemQuantity());
		            found = true;
		            break;
		        }
		    }

		    if (!found) {
		        // 新しい商品なら追加
		        ItemForm newItem = new ItemForm();
		        newItem.setItemId(form.getItemId());
		        newItem.setItemName(form.getItemName());
		        newItem.setItemPrice(form.getItemPrice());
		        newItem.setItemQuantity(form.getItemQuantity());
		        cart.add(newItem);
		    }

		    session.setAttribute(session_cart, cart);
		}
		
		//カートの商品を削除するメソッド
		public void removeCart(HttpSession session, String itemId) {
			List<ItemForm> cart = getCart(session);
			List<ItemForm> newCart = new ArrayList<>();
			for (ItemForm item : cart) {
				if(!item.getItemId().equals(String.valueOf(itemId))) {
					newCart.add(item);
				}
			}
			session.setAttribute(session_cart, newCart);
		}
			
		//カートを全削除(セッション破棄)をするメソッド
		public void clearCart(HttpSession session) {
			session.removeAttribute(session_cart);
		}
		
		//カートの数量を変更するメソッド
		public void updateQuantity(HttpSession session, String itemId, int itemQuantity) {
		    List<ItemForm> cart = getCart(session);
		    for(ItemForm item : cart) {
		        if(item.getItemId().equals(itemId)) {
		          item.setItemQuantity(itemQuantity);
		            break;
		        }
		    }
		    session.setAttribute(session_cart, cart);
		}
		
		//検索結果を表示するメソッド
		 public List<ItemEntity> searchItem(String keyword, Integer categoryId) throws Exception {
		        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
		        boolean hasCategory = categoryId != null;

		        if (hasKeyword && hasCategory) {
		            return itemSearchRepository.getItemByKeywordAndCategoryList(keyword, categoryId);
		        } else if (hasKeyword) {
		            return itemSearchRepository.getItemByKeywordList(keyword);
		        } else if (hasCategory) {
		            return itemSearchRepository.getItemByCategoryList(categoryId);
		        } else {
		            return itemSearchRepository.getItemAllList();
		        }
		 }

			//商品IDから商品詳細を取得するメソッド
			public ItemForm getItemById(int itemId) throws Exception {
				// RepositoryからEntityを取得
				ItemEntity entity = itemSearchRepository.getItemById(itemId);
				// Entity → Form へ変換
				ItemForm form = modelMapper.map(entity, ItemForm.class);
				return form;
			}

}
