package jp.ken.interiorshop.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {
	
	private int memberId;
	
	private String memberName;
	
	private String memberKana;
	
	private String mail;
	
	private String password;
	
	private String phoneNumber;
	
	private String postalCode;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private Integer creditNo;
	
	//退会フラグ。初期値は0で固定
	private int cancel = 0;

}
