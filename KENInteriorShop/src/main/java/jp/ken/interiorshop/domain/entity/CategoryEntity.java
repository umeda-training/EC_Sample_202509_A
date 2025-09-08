package jp.ken.interiorshop.domain.entity;

import java.util.List;

import lombok.Data;

@Data
public class CategoryEntity {
	//カテゴリーID
	private int categoryid;
	
	//カテゴリー名
	private String categoryname;
	
	private List<ItemEntity> itemList;
}
