package jp.ken.interiorshop.domain.entity;

import lombok.Data;

@Data
public class OrderDetailsEntity {
	
	//商品詳細ID
	private int detail_id;
	
	//注文ID
	private int order_id;
	
	//商品ID
	private int item_id;
	
	//注文数
	private int item_quantity;
	
	//小計
	private int subtotal;
}
