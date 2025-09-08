package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CategoryForm implements Serializable {
	//カテゴリーID
	private int categoryid;
	
	//カテゴリー名
	private String categoryname;
	
	private List<ItemForm> itemList;
}
