package jp.ken.interiorshop.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.domain.entity.OrderDetailsEntity;
import jp.ken.interiorshop.domain.entity.OrderEntity;
import jp.ken.interiorshop.domain.entity.ShippingEntity;
import jp.ken.interiorshop.domain.repository.OrderRegistRepository;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;
import jp.ken.interiorshop.presentation.form.OrderDetailsForm;
import jp.ken.interiorshop.presentation.form.OrderForm;
import jp.ken.interiorshop.presentation.form.ShippingForm;

@Service
public class OrderRegistService {

	private OrderRegistRepository orderRegistRepository;
	private ModelMapper modelMapper;
	
	public OrderRegistService(OrderRegistRepository orderRegistRepository, ModelMapper modelMapper) {
		this.orderRegistRepository = orderRegistRepository;
		this.modelMapper = modelMapper;
	}
	
	
	public void orderRegist(MemberLoginForm memberLoginForm, OrderForm orderForm){
		
		
		ShippingEntity shippingEntity;
		
		// ★ 住所オプションを判定
        if ("member".equals(orderForm.getAddressOption())) {
            // 会員情報から住所を設定
            MemberEntity memberEntity = convert(memberLoginForm);
            shippingEntity = new ShippingEntity();
            shippingEntity.setShippingName(memberEntity.getMemberName());
            shippingEntity.setShippingKana(memberEntity.getMemberKana());
            shippingEntity.setShippingphone(memberEntity.getPhoneNumber());
            shippingEntity.setShippingPostalCode(memberEntity.getPostalCode());
            shippingEntity.setShippingAddress1(memberEntity.getAddress1());
            shippingEntity.setShippingAddress2(memberEntity.getAddress2());
            shippingEntity.setShippingAddress3(memberEntity.getAddress3());
        } else {
            // 入力フォームの住所を使用
            shippingEntity = convert(orderForm.getShippingForm());
        }
		
        //MemberLoginFormをMemberEntityに変換
		MemberEntity memberEntity = convert(memberLoginForm);
		
		//OrderFormをOrderEntityに変換
		OrderEntity orderEntity = convert(orderForm);
		
		//OrderDetailsFormをOrderDetailsEntityに変換
		List<OrderDetailsEntity> detailsList = convert(orderForm.getOrderDetailsForm());

		
		//発送情報登録して発送IDを取得
		int shippingId = orderRegistRepository.shippingRegist(shippingEntity);
		
		//注文内容登録して注文IDを取得
		int orderId = orderRegistRepository.orderRegist(shippingId, memberEntity.getMemberId(), orderEntity);
		
		//注文詳細内容登録
		orderRegistRepository.orderDetailsRegist(orderId, detailsList);
		
	}
	
	//ログイン情報変換
	private MemberEntity convert(MemberLoginForm form) {
		
		MemberEntity entity = modelMapper.map(form, MemberEntity.class);
		
		return entity;
	}
	
	//発送情報変換
	private ShippingEntity convert(ShippingForm form) {
		
		ShippingEntity entity = modelMapper.map(form, ShippingEntity.class);
		
		return entity;
	}
	
	//注文情報変換
	private OrderEntity convert(OrderForm form) {
		
		OrderEntity entity = modelMapper.map(form, OrderEntity.class);
		
		return entity;
	}
	
	//注文詳細変換
	private List<OrderDetailsEntity> convert(List<OrderDetailsForm> formList) {
	    List<OrderDetailsEntity> entityList = new ArrayList<>();
	    for (OrderDetailsForm form : formList) {
	        OrderDetailsEntity entity = modelMapper.map(form, OrderDetailsEntity.class);
	        entityList.add(entity);
	    }
	    return entityList;
	}
	
	
	
	
}