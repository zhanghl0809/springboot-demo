package com.example.demo.common.exception;


import com.example.demo.common.enums.ResultEnum;
import com.example.demo.common.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {


    /**
     * 统一未知异常处理
     * @param e
     * @return object
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Object> unityExceptionHandler(Exception e) {
        log.error("unityExceptionHandler-未知异常", e);
        return ApiResult.EXCEPTION(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ApiResult<Object> unityThrowableHandler(Throwable e) {
        log.error("unityThrowableHandler-未知异常", e);
        return ApiResult.EXCEPTION(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getMessage());
    }

    /**
     * 统一未知错误处理
     * @param e
     * @return object
     */
    @ExceptionHandler(Error.class)
    public ApiResult<Object> unityErrorHandler(Error e) {
        log.error("unityErrorHandler-未知错误", e);
        return ApiResult.EXCEPTION(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }


    /**
     * 自定义异常处理 ServerBusyException
     * @param e
     * @return object
     */
    @ExceptionHandler(BizException.class)
    public ApiResult<Object> bizExceptionHandler(BizException e) {
        log.error("bizExceptionHandler-业务异常", e);
        return ApiResult.EXCEPTION(e.getCode(),e.getMessage());
    }

    /**
     *
     * @param e
     * @return object
     */
    @ExceptionHandler(TokenException.class)
    public ApiResult<Object> TokenException(TokenException e) {
        log.error("TokenException-业务异常", e);
        return ApiResult.EXCEPTION(e.getCode(),e.getMessage());
    }

}
