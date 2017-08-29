package com.dhcc.workbench.kernel;

/**
 * 异步异常
 * Created by 张立伟 on 2016/8/12.
 */
public class TPromiseException extends RuntimeException {

    private Exception catchException;

    public TPromiseException(Exception throwable) {
        super(throwable);
        catchException=throwable;
    }

    public Exception getCatchException(){
        return catchException;
    }
}
