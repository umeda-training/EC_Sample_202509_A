package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
