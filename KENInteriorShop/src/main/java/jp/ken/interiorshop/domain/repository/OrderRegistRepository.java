package jp.ken.interiorshop.domain.repository;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.OrderEntity;
import jp.ken.interiorshop.domain.entity.ShippingEntity;

@Repository
public class OrderRegistRepository {
	private JdbcTemplate jdbcTemplate;
	
	//発送内容をDBに保存する
	public int shippingRegist(ShippingEntity shippingEntity) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO shipping");
		sb.append(" (");
		sb.append("shipping_name");
		sb.append(", shipping_kana");
		sb.append(", shipping_phone");
		sb.append(", shipping_postal_code");
		sb.append(", shipping_address1");
		sb.append(", shipping_address2");
		sb.append(", shipping_address3");
		sb.append(")");
		sb.append(" VALUES");
		sb.append(" (?, ?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = {
			        shippingEntity.getShippingName(),
			        shippingEntity.getShippingKana(),
			        shippingEntity.getShippingphone(),
			        shippingEntity.getShippingPostalCode(),
			        shippingEntity.getShippingAddress1(),
			        shippingEntity.getShippingAddress2(),
			        shippingEntity.getShippingAddress3()
			    };
		
		int numberOfRow = jdbcTemplate.update(sql, parameters);
		return numberOfRow;
			}

	//注文内容をDBに保存する
	public int orderRegist(OrderEntity orderEntity) throws Exception{
		
		
		// shipping_id を DB から取得
	   /* Long shippingId = getDefaultShippingId(loginUser.getId());
	    orderEntity.setShippingId(shippingId);*/

		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO orders");
		sb.append(" (");
		sb.append("member_id");
		sb.append(", total");
		sb.append(", order_date");
		sb.append(", payment");
		sb.append(", shipping_id");
		sb.append(", shipping_frag");
		sb.append(")");
		sb.append(" VALUES");
		sb.append(" (?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = {
					orderEntity.getMemberId(),
					orderEntity.getTotal(),
					LocalDateTime.now(), 
					orderEntity.getPayment(),
					orderEntity.getShippingId(),
					orderEntity.getShippingFrag()
			    };
		
		int numberOfRow = jdbcTemplate.update(sql, parameters);
		return numberOfRow;
}
	

}
