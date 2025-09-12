package jp.ken.interiorshop.domain.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jp.ken.interiorshop.presentation.form.OrderDetailsForm;
import lombok.Data;

@Data
public class OrderEntity {
	
	//注文ID
	private int orderId;
	
	//顧客ID
	private int memberId;
	
	//合計金額
	private int total;
	
	//注文日
	private Date orderDate;
	
	//支払方法
	private String payment;
	
	//発送先ID
	private int shippingId;
	
	//発送フラグ
	private Integer shippingFrag = 0;
	
	// 複数商品を保持するリストに変更
    private List<OrderDetailsForm> orderDetailsForm = new ArrayList<>();

}
