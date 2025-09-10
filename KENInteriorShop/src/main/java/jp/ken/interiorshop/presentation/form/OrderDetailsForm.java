package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderDetailsForm implements Serializable {
	
	//商品詳細ID
	private String detail_id;
	
	//注文ID
	private String order_id;
	
	//商品ID
	private String item_id;
	
	//注文数
	private String item_quantity;
	
	//小計
	private String subtotal;
}
