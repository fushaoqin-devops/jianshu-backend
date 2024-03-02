package com.fushaoqin.jianshu.common;

import lombok.Data;

/**
 * Custom exception class
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "ChicChoiceException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
