package jp.ken.interiorshop.common.validatior;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jp.ken.interiorshop.common.annotation.CustomValidation;
import jp.ken.interiorshop.common.validatior.groups.ValidGroup1;
import jp.ken.interiorshop.presentation.form.OrderForm;
import jp.ken.interiorshop.presentation.form.ShippingForm;

public class ShippingFormValidator implements ConstraintValidator<CustomValidation, OrderForm> {
	 @Override
	    public boolean isValid(OrderForm orderForm, ConstraintValidatorContext context) {
	        if ("other".equals(orderForm.getAddressOption())) {
	            // 'other'が選択されている場合、ShippingFormのバリデーションを行う
	            Set<ConstraintViolation<ShippingForm>> violations = 
	                Validation.buildDefaultValidatorFactory().getValidator().validate(orderForm.getShippingForm(), ValidGroup1.class);
	            
	            if (!violations.isEmpty()) {
	            	context.disableDefaultConstraintViolation();
	            	for (ConstraintViolation<ShippingForm> violation : violations) {
	                    context.buildConstraintViolationWithTemplate(violation.getMessage())
	                           .addPropertyNode("shippingForm." + violation.getPropertyPath().toString())
	                           .addConstraintViolation();
	                }
	                return false;
	            }
	        }
	        return true;
	    }
}
