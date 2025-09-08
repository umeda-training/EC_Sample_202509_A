package jp.ken.interiorshop.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {
	//顧客ID
	private int memberid;
	
	//顧客名
	private String membername;
	
	//フリガナ
	private String memberkana;
	
	//メールアドレス
	private String mail;
	
	//パスワード
	private String password;
	
	//電話番号
	private String phonenumber;
	
	//郵便番号
	private String postalcode;
	
	//住所1(都道府県)
	private String address1;
	
	//住所2(市区町村)
	private String address2;	

	//住所3(番地以降)
	private String address3;
	
	//クレジットカード番号
	private int creditno;
	
	//退会フラグ
	private int cancel;
}
