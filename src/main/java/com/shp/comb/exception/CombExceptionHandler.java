package com.shp.comb.exception;


import com.shp.comb.common.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * Created by shp on 19/10/21.
 */
@RestControllerAdvice
public class CombExceptionHandler {


    /**
     * @param
     */
    @ExceptionHandler(ServerBizException.class)
    public Result handServerBizException(ServerBizException e) {
        return Result.error(e.getErrCode().getCode(), e.getMessage(),"");
    }


    /**
     * @param e shiro权限验证异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleAuthorizationException(AuthorizationException e) {
        return Result.error(403, "权限异常","");
    }

    /**
     * shrio 身份认证异常
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthcEx(UnauthenticatedException e) {
        return Result.error(403, "身份认证异常","");
    }

}
