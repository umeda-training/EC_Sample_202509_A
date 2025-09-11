package jp.ken.interiorshop.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.OrderDetailsEntity;
import jp.ken.interiorshop.domain.entity.OrderEntity;
import jp.ken.interiorshop.domain.entity.ShippingEntity;

@Repository
public class OrderRegistRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public OrderRegistRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//発送情報をDBに保存する
	public int shippingRegist(ShippingEntity shippingEntity){
		
		String sql = "INSERT INTO shipping (shipping_name, shipping_kana, shipping_phone, " +
	             "shipping_postal_code, shipping_address1, shipping_address2, shipping_address3) " +
	             "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, shippingEntity.getShippingName(),
                shippingEntity.getShippingKana(),
                shippingEntity.getShippingphone(),
                shippingEntity.getShippingPostalCode(),
                shippingEntity.getShippingAddress1(),
                shippingEntity.getShippingAddress2(),
                shippingEntity.getShippingAddress3());

		//登録直後の発送IDを取得
		Integer shippingId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

		return shippingId;
}
		//注文情報をDBに保存する
		public int orderRegist(int shippingId, int memberId, OrderEntity orderEntity) {
		String sql = "INSERT INTO orders (member_id, total, order_date, " +
	             "payment, shipping_id, shipping_frag" +
	             "VALUES (?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, memberId,
                orderEntity.getTotal(),
                LocalDate.now(),
                orderEntity.getPayment(),
                shippingId, 
                orderEntity.getShippingId());
		
		//登録直後の注文IDを取得
		Integer orderId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

		return orderId;
	}
	
	//注文詳細情報をDBに保存する	
	public void orderDetailsRegist(int orderId, List<OrderDetailsEntity> form) {
		String sql = "INSERT INTO order_details (order_id, item_id, item_quantity, " +
	             "subtotal" +
	             "VALUES (?, ?, ?, ?)";
		for(OrderDetailsEntity regist : form) {
		jdbcTemplate.update(sql, orderId,
                orderId,
                regist.getItemId(),
                regist.getItemQuantity(),
                regist.getSubtotal());
		}
	}
}
