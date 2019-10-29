package com.shp.comb.exception;


import com.shp.comb.common.ErrorCodeEnum;

public class ServerBizException extends RuntimeException {

    private static final long serialVersionUID = -1557974392802481353L;

    private ErrorCodeEnum errCode;

    /**
     * 默认构造函数
     */
    public ServerBizException() {
        super(ErrorCodeEnum.SERVER_SYSERR.getRemark());
        this.errCode = ErrorCodeEnum.SERVER_SYSERR;
    }

    /**
     * 默认构造函数
     */
    public ServerBizException(String errorMessage) {
        super(errorMessage);
        this.errCode = ErrorCodeEnum.SERVER_SYSERR;
    }
    
    /**
     * @param errCode
     */
    public ServerBizException(ErrorCodeEnum errCode) {
        super(errCode.getRemark());
        this.errCode = errCode;
    }

    /**
     * @param message
     * @param errCode
     */
    public ServerBizException(String message, ErrorCodeEnum errCode) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * @param cause
     * @param errCode
     */
    public ServerBizException(Throwable cause, ErrorCodeEnum errCode) {
        super(cause);
        this.errCode = errCode;
    }

    /**
     * @param message
     * @param cause
     * @param errCode
     */
    public ServerBizException(String message, Throwable cause, ErrorCodeEnum errCode) {
        super(message, cause);
        this.errCode = errCode;
    }

    public ErrorCodeEnum getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrorCodeEnum errCode) {
        this.errCode = errCode;
    }
}
