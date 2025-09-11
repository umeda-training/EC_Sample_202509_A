package jp.ken.interiorshop.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.interiorshop.domain.repository.OrderRegistRepository;

@Service
public class OrderRegistService {

	private OrderRegistRepository orderRegistRepository;
	private ModelMapper modelMapper;
	
	public OrderRegistService(OrderRegistRepository orderRegistRepository, ModelMapper modelMapper) {
		this.orderRegistRepository = orderRegistRepository;
		this.modelMapper = modelMapper;
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
