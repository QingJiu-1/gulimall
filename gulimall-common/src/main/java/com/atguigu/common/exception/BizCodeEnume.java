package com.atguigu.common.exception;

/**
 * 错误码和错误信息定义枚举类
 *  错误码定义规则为5位数
 *  前两位表示业务场景，最后三位表示错误码
 *  维护错误码后需要维护错误描述，将他们定义为枚举类型
 *      10: 通用
 *          001：参数格式校验
 *      11: 商品
 *      12: 订单
 *      13: 购物车
 *      14: 物流
 */
public enum BizCodeEnume {

    UNKNOW_EXCEPTION(1000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败");

    private Integer code; //状态码
    private String msg; //提示消息

    BizCodeEnume(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

