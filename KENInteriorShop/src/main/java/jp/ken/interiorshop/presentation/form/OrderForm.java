package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderForm implements Serializable {

	//登録住所or別住所
	private String addressOption;
	
	//注文ID
	private String orderId;
	
	//顧客ID
	private String memberId;
	
	//合計金額
	private String total;
	
	//注文日
	private String orderDate;
	
	//支払方法
	private String payment;
	
	//発送先ID
	private String shippingId;
	
	//発送フラグ
	private String shippingFrag;
	
	//注文詳細のネストForm
	private OrderDetailsForm orderDetailsForm;
	
	//発送のネストForm
	private ShippingForm shippingForm;
}
