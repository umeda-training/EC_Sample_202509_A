package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberRegistForm implements Serializable {
	//氏名
	@NotEmpty(message = "必須入力です")
	private String memberName;
	
	//フリガナ
	@NotEmpty(message = "必須入力です")	
	private String memberKana;

	//メールアドレス
	@NotEmpty(message = "必須入力です")
	private String mail;

	//パスワード
	@NotEmpty(message = "必須入力です")
	private String password;

	//電話番号
	@NotEmpty(message = "必須入力です")
	private String phoneNumber;
	
	//郵便番号
	private String postalCode;

	//住所1(都道府県)
	@NotEmpty(message = "必須入力です")
	private String address1;

	//住所2(市区町村)
	@NotEmpty(message = "必須入力です")
	private String address2;

	//住所3(番地以降)
	@NotEmpty(message = "必須入力です")
	private String address3;
	
	//クレジットカード番号
	private int creditNo;
	
	//有効期限(月)
	private int creditMonth;
	
	//有効期限(年)
	private int creditYear;
	
	//カード名義
	private String creditName;
	
	//セキュリティコード
	private int securityCode;
}
