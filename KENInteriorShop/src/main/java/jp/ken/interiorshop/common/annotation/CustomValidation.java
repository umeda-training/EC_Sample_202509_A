package jp.ken.interiorshop.common.annotation;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.ken.interiorshop.common.validatior.ShippingFormValidator;
@Retention(RUNTIME) 
@Target(ElementType.TYPE) 
@Constraint(validatedBy = ShippingFormValidator.class) 
public @interface CustomValidation { 
 
  String message() default "必須入力です"; ; 
 
  Class<?>[] groups() default {}; 
   
  Class<? extends Payload>[] payload() default {}; 
} 
