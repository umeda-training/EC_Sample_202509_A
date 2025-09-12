package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jp.ken.interiorshop.common.validatior.groups.ValidGroup1;
import lombok.Data;

@Data
public class ShippingForm implements Serializable {

	//発送先ID
	private String shippingId;
	
	//発送先氏名
	@NotBlank(message = "必須入力です", groups = ValidGroup1.class)
	private String shippingName;
	
	//発送先フリガナ
	@NotBlank(message = "必須入力です")
	private String shippingKana;
	
	//発送先電話番号
	@NotBlank(message = "必須入力です")
	private String shippingphone;
	
	//発送先郵便番号
	@NotBlank(message = "必須入力です")
	private String shippingPostalCode;
	
	//発送先都道府県
	@NotBlank(message = "必須入力です")
	private String shippingAddress1;
	
	//発送先市区町村
	@NotBlank(message = "必須入力です")
	private String shippingAddress2;
	
	//発送先番地
	@NotBlank(message = "必須入力です")
	private String shippingAddress3;
	
}
