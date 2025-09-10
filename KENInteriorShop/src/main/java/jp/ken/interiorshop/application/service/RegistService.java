package jp.ken.interiorshop.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.domain.repository.MemberRepository;
import jp.ken.interiorshop.presentation.form.MemberRegistForm;

@Service
public class RegistService {
	private MemberRepository memberRepository;
	private ModelMapper modelMapper;
	
	public RegistService(MemberRepository memberRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
	}
	
	public int registMembers(MemberRegistForm form) throws Exception{
		MemberEntity entity = null;
		
		//メールアドレス重複チェック
		if(memberRepository.existsByMail(form.getMail())) {
			return 0;
		}
		
		entity = convert(form);
		
		int resultRow = memberRepository.regist(entity);
		
		return resultRow;
	}
	
	private MemberEntity convert(MemberRegistForm form) {
		
		MemberEntity entity = modelMapper.map(form, MemberEntity.class);
		
		return entity;
	}
}
