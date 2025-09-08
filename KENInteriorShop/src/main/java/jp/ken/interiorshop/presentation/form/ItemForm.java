package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemForm implements Serializable {
	
	//商品ID
	private String itemid;
	
	//商品名
	private String itemname;
	
	//カテゴリーID
	private String categoryid;
	
	//価格
	private String itemprice;
	
	//発売開始日
	private String rsdate;
	
	//画像データ
	private String image;
	
	//商品説明
	private String explanation;
	
	//在庫数
	private String stock;
	
	//categoryフォームの情報を保持
	private CategoryForm category;
}
