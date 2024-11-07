package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.valueextraction.ExtractedValue;
import java.util.HashMap;
import java.util.Map;


/**
 * 集中处理异常
 */
/*@ResponseBody //以Json数据写出去
@ControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller") //可以通过basePackages告诉其统一处理哪个路径下的异常*/
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller") //该注解等于@ResponseBody + @ControllerAdvice相结合
public class GulimallExceptionControllerAdvice {

    /**
     * 处理数据校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)//告诉这话集中处理异常类的这个方法可以处理哪些异常
    public R handleVaildException(MethodArgumentNotValidException e){
        //可以通过日志看到异常类型:class org.springframework.web.bind.MethodArgumentNotValidException
        log.error("数据校验出现问题{},异常类型:{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();

        bindingResult.getFieldErrors().forEach((item) ->{
            //错误数据具体信息
            String message = item.getDefaultMessage();
            //错误数据属性名
            String field = item.getField();
            errorMap.put(field,message);
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    //其他的公共异常处理

    /**
     * 系统错误码的格式
     * 错误码定义规则为5位数】
     * 前两位表示业务
     *  10: 通用
     *  11: 商品
     *  12: 订单
     *  13: 购物车
     *  14: 物流
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = Throwable.class) //可以处理任意的数据异常
    public R handleException(Throwable throwable){
        log.error("公共的数据源异常处理{}，异常类型: {}",throwable.getMessage(),throwable.getClass());
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }

}
