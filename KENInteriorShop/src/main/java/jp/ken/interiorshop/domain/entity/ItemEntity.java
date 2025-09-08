package jp.ken.interiorshop.domain.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class ItemEntity {

	//商品ID
	private int itemid;
	
	//商品名
	private String itemname;
	
	//カテゴリーID
	private int categoryid;
	
	//価格
	private int itemprice;
	
	//発売開始日
	private Date rsdate;
	
	//画像データ
	private String image;
	
	//商品説明
	private String explanation;
	
	//在庫数
	private int stock;
	
	private CategoryEntity category;
}
