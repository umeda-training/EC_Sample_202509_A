package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class ShippingForm implements Serializable {

	//発送先ID
	private String shippingId;
	
	//発送先氏名
	private String shippingName;
	
	//発送先フリガナ
	private String shippingKana;
	
	//発送先電話番号
	private String shippingphone;
	
	//発送先郵便番号
	private String shippingPostalCode;
	
	//発送先都道府県
	private String shippingAddress1;
	
	//発送先市区町村
	private String shippingAddress2;
	
	//発送先番地
	private String shippingAddress3;
	
}
