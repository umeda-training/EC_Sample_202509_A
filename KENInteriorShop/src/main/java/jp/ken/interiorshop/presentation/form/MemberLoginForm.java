package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberLoginForm implements Serializable {

	private String mail;
	
	private String password;
}
