package com.gjf.validator;

import com.gjf.validator.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 8:35
 */
public class PhoneValidator implements ConstraintValidator<Phone,String> {

    private Pattern phonePattern = Pattern.compile("0?(13|14|15|18|17)[0-9]{9}");
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){return true;}
        return phonePattern.matcher(value).matches();
    }
}
