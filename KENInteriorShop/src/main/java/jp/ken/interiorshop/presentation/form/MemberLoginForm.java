package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginForm implements Serializable {
	
	@NotEmpty(message = "ログインIDは必須です")
	private String loginId;
	
	@NotEmpty(message = "パスワードは必須です")
	private String password;
}
