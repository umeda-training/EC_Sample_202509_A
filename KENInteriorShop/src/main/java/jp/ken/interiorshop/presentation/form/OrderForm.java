package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jp.ken.interiorshop.common.annotation.CustomValidation;
import jp.ken.interiorshop.common.validatior.groups.ValidGroup1;
import lombok.Data;

@Data
@CustomValidation(groups = ValidGroup1.class)
public class OrderForm implements Serializable {

	//登録住所or別住所
	private String addressOption = "other";
	
	//注文ID
	private String orderId;
	
	//顧客ID
	private String memberId;
	
	//合計金額
	private String total;
	
	//注文日
	private String orderDate;
	
	 @NotBlank(message = "支払い方法を選択してください")
	//支払方法
	private String payment;
	
	 @NotBlank(message = "配送方法を選択してください")
	//配送指定
	private String shipping;
	
	//発送先ID
	private String shippingId;
	
	//発送フラグ
	private String shippingFrag;
	
	// 複数商品を保持するリストに変更
	 @Valid 
    private List<OrderDetailsForm> orderDetailsForm = new ArrayList<>();
	
	//発送のネストForm
  
	private ShippingForm shippingForm = new ShippingForm();
	
}
