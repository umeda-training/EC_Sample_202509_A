package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.domain.repository.MemberRepository;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

public class LoginService {
	
	private MemberRepository memberRepository;
	private ModelMapper modelMapper;
	
	public LoginService(MemberRepository memberRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<MemberLoginForm> getEmployeesList(MemberLoginForm form) throws Exception{
		
		//EntityとLoginFormの初期化
		List<MemberEntity> entityList = null;
		List<MemberLoginForm> formList = null;
		//MemberEntity entity = null;
		
		//formからIDとパスワードを取得
		String loginId = form.getLoginId();
		String password = form.getPassword();
		
		//会員全体のリストを取得
		if(!loginId.isEmpty() && !password.isEmpty()) {
			entityList = memberRepository.getMemberAllList();
		}
		
		//List<MemberEntity>からList<MemberLoginForm>に変換
		formList = convert(entityList);
		
		return formList;
	}
	
	private List<MemberLoginForm> convert(List<MemberEntity> entityList){
		
		//formListの初期化
		List<MemberLoginForm> formList = new ArrayList<MemberLoginForm>();
		
		//entityListの中身をformListに格納
		for(MemberEntity entity : entityList) {
			MemberLoginForm form = modelMapper.map(entity, MemberLoginForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
