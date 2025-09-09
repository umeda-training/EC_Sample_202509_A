package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.domain.repository.MemberRepository;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@Service
public class LoginService {
	
	private MemberRepository memberRepository;
	private ModelMapper modelMapper;
	
	public LoginService(MemberRepository memberRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<MemberLoginForm> getMemberList(MemberLoginForm form) throws Exception {
		
		List<MemberEntity> entityList = null;
		List<MemberLoginForm> formList = null;
		
		entityList = memberRepository.getMemberAllList();
		
		formList = convert(entityList);
		
		return formList;
	}
	
	private List<MemberLoginForm> convert(List<MemberEntity> entityList) {
		
		List<MemberLoginForm> formList = new ArrayList<MemberLoginForm>();
		
		for (MemberEntity entity : entityList) {
			MemberLoginForm form = modelMapper.map(entity, MemberLoginForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
