package jp.ken.interiorshop.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {
	
	private int memberId;
	
	private String memberName;
	
	private String memberKana;
	
	private String mail;
	
	private String password;
	
	private String phone_number;
	
	private String postalCode;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private int creditNo;
	
	private int cancel;

}
