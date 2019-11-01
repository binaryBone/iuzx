package com.iuzx.common.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.iuzx.common.constants.ResultCodeEnum;
import com.iuzx.common.exception.MaguaiException;
import com.iuzx.common.util.ExceptionUtil;
import com.iuzx.common.vo.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(ExceptionUtil.getMessage(e));
        return R.error();
    }

   /* @ExceptionHandler(MySQLSyntaxErrorException.class)
    @ResponseBody
    public R error(MySQLSyntaxErrorException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }*/

    /**
     * sql异常
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(MaguaiException.class)
    @ResponseBody
    public R error(MaguaiException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }


    /**
     * json格式异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(JsonParseException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }
}
