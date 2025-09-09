package jp.ken.interiorshop.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {
	
	private int member_id;
	
	private String member_name;
	
	private String member_kana;
	
	private String mail;
	
	private String password;
	
	private String phone_number;
	
	private String postal_code;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private int credit_no;
	
	private int cancel;

}
