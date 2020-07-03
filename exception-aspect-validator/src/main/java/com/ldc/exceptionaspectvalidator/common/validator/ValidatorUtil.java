package com.ldc.exceptionaspectvalidator.common.validator;

import com.ldc.exceptionaspectvalidator.common.exception.GlobalParamException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/4 0:07
 */
public class ValidatorUtil {

    private static Validator validator = (Validation.byProvider(HibernateValidator.class).configure()).failFast(true).buildValidatorFactory().getValidator();

    /**
     * Description: 实体校验
     * @author ldc
     * @date 2020-07-01 17:13:55
     */
    public static <T> void validate(T obj, Class<?>... group) throws GlobalParamException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, group);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> validateInfo = (ConstraintViolation<T>) constraintViolations.iterator().next();
            throw new GlobalParamException(validateInfo.getMessage());
        }
    }

    /**
     * Description: 实体校验
     * @author ldc
     * @date 2020-07-01 17:13:55
     */
    public static <T> void validate(T obj) throws GlobalParamException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> validateInfo = (ConstraintViolation<T>) constraintViolations.iterator().next();
            throw new GlobalParamException(validateInfo.getMessage());
        }
    }

    public static final String REGEX_DIGIT = "^[0-9]*$";//数字

    private static boolean isMatch(String regex, CharSequence input) {
        if (input == null || "".equals(input)) return false;
        return Pattern.matches(regex, input);
    }

    /**
     * Description: 数字校验
     * @author ldc
     * @date 2020-07-01 11:13:19
     */
    public static boolean IsDigit(String field) {
        return isMatch(REGEX_DIGIT, field);
    }

    /**
     * Description: 坐标校验
     * @author ldc
     * @date 2020-07-01 11:10:27
     */
    public static void isCoordinateCheck(String field, String message) throws GlobalParamException {
        String[] coordinateArray = field.split(";");
        if (coordinateArray == null || coordinateArray.length != 8) {
            throw new GlobalParamException(message);
        }
        for (String str : coordinateArray) {
            String[] strArray = str.split(",");
            if (strArray == null || strArray.length != 3) {
                throw new GlobalParamException(message);
            }
            for (String item : strArray) {
                if (!IsDigit(item)) {
                    throw new GlobalParamException(message);
                }
            }
        }
    }

}
