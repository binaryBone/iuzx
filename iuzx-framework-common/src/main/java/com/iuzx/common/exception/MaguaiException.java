package com.iuzx.common.exception;

import com.iuzx.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统一异常处理类")
public class MaguaiException extends RuntimeException{

    @ApiModelProperty("错误码")
    Integer code;

    String massage;

    public MaguaiException(Integer code,String massage){
        super(massage);
        this.code = code;
    }

    public MaguaiException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "MaguaiException{" +
                "code=" + code +
                ", massage='" + this.getMassage() + '\'' +
                '}';
    }
}
