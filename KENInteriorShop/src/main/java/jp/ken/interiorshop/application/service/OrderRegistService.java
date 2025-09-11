package jp.ken.interiorshop.application.service;

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
	
	
	public void orderRegist(MemberLoginForm memberLoginForm, OrderForm orderForm) throws Exception{
		
		
		ShippingEntity shippingEntity = convert(orderForm.getShippingForm());
		MemberEntity memberEntity = convert(memberLoginForm);
		OrderEntity orderEntity = convert(orderForm);
		OrderDetailsEntity orderDetailsEntity = convert(orderForm.getOrderDetailsForm());
		
		//発送情報登録して発送IDを取得
		int shippingId = orderRegistRepository.shippingRegist(shippingEntity);
		//注文内容登録
		int orderId = orderRegistRepository.orderRegist(shippingId, memberEntity.getMemberId(), orderEntity);
		//注文詳細内容登録
		orderRegistRepository.orderDetailsRegist(orderId, orderForm.getOrderDetailsForm());
		
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
		private OrderDetailsEntity convert(List<OrderDetailsForm> form) {
			
			OrderDetailsEntity entity = modelMapper.map(form, OrderDetailsEntity.class);
			
			return entity;
		}
	
	/* public void completeOrder(OrderForm orderForm, MemberLoginForm loginUser) {
		 
		 ShippingEntity shipping = new ShippingEntity();
		    if ("member".equals(orderForm.getAddressOption())) {
		        shipping.setName(loginUser.getName());
		        shipping.setNameKana(loginUser.getNameKana());
		        shipping.setPhone(loginUser.getPhone());
		        shipping.setZipCode(loginUser.getZipCode());
		        shipping.setPrefecture(loginUser.getPrefecture());
		        shipping.setCity(loginUser.getCity());
		        shipping.setStreet(loginUser.getStreet());
		    } else {
		        shipping.setName(orderForm.getName());
		        shipping.setNameKana(orderForm.getNameKana());
		        shipping.setPhone(orderForm.getPhone());
		        shipping.setZipCode(orderForm.getZipCode());
		        shipping.setPrefecture(orderForm.getPrefecture());
		        shipping.setCity(orderForm.getCity());
		        shipping.setStreet(orderForm.getStreet());
		    }
	 }*/
	
	
}
