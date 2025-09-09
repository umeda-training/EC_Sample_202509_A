package jp.ken.interiorshop.domain.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CategoryEntity implements Serializable{
	private int categoryId;
	
	private String categoryName;
	
	private List<ItemEntity> itemList;
}
