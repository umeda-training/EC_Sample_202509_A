package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginForm implements Serializable {

	@NotEmpty(message = "メールアドレスを入力して下さい")
	private String mail;
	
	@NotEmpty(message = "パスワードを入力して下さい")
	private String password;
}
