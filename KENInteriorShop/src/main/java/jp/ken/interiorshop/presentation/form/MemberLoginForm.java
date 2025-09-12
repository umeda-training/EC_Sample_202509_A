package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginForm implements Serializable {
	
	private int memberId;
	
	private String memberName;
	
	private String memberKana;

	@NotEmpty(message = "メールアドレスを入力して下さい")
	private String mail;
	
	@NotEmpty(message = "パスワードを入力して下さい")
	private String password;
	
	private String phoneNumber;
	
	private String postalCode;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private String creditNo;
	
	private String creditMonth;
	
	private String creditYear;
	
	private String creditName;
	
	private String securityCode;
	
	private int cancel;
}
