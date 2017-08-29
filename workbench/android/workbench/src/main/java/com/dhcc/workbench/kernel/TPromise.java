package com.dhcc.workbench.kernel;

import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 异步模型
 * Created by 张立伟 on 2016/7/13.
 */
public abstract class TPromise extends Thread {

    protected List<OnResolve> callbacks;
    //记录前面的promise
    private TPromise prePromise;
    protected OnError errorCallback;
    private boolean isEnded;

    public static OnError defaultError = new OnError() {
        @Override
        public void onError(Exception e) {
//            StackHelper.printStack(e);
            e.printStackTrace();
        }
    };

    public TPromise() {
        callbacks = new ArrayList<>();
    }

    public static abstract class OnResolve<T> {

        public abstract Object onResolve(T t);

        /**
         * 拦截错误
         *
         * @param e
         */
        public Object onError(Exception e) {
            throw new TPromiseException(e);
        }
    }

    /**
     * 实现 OnResolve<String>
     */
    public static abstract class OnJsonResolve extends OnResolve<String> {
        @Override
        public abstract Object onResolve(String json);
    }

    ;

    /**
     * 实现 OnResolve<Map>
     */
    public static abstract class OnMapResolve extends OnResolve<Map> {
        @Override
        public abstract Object onResolve(Map map);
    }

    ;

    public interface OnError {
        void onError(Exception e);
    }


    @NonNull
    public TPromise then(OnResolve callback) {
        if (callback == null) throw new RuntimeException("参数不能为空");
        callbacks.add(callback);
        return this;
    }

    @NonNull
    public TPromise error(OnError onError) {
        if (onError == null) throw new RuntimeException("参数不能为空");
        errorCallback = onError;
        return this;
    }

    protected void resolve(Object result) {
        try {
            boolean isTheLastPromise = true;
            while (callbacks.size() > 0) {
                OnResolve onResolve = callbacks.remove(0);
                try {
                    result = onResolve.onResolve(result);
                } catch (Exception e) {
                    result = onResolve.onError(e);
                }
                if (result == null) {
                    break;
                }
                if (result instanceof TPromise) {
                    isTheLastPromise = false;
                    ((TPromise) result).appendAll(callbacks).error(errorCallback).addPrePromise(this).start();
                    break;
                }
            }
            if (isTheLastPromise) {
                onPromiseEnd();
            }
        } catch (Exception e) {
            if (e instanceof TPromiseException) {
                reject(((TPromiseException) e).getCatchException());
            } else {
                reject(e);
            }
        }
    }

    protected void reject(Exception e) {
        if (errorCallback == null) {
            errorCallback = defaultError;
        }
        errorCallback.onError(e);
        onPromiseEnd();
    }

    protected TPromise appendAll(List<OnResolve> callbacks) {
        this.callbacks.addAll(callbacks);
        return this;
    }

    private TPromise addPrePromise(TPromise promise) {
        this.prePromise = promise;
        return this;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (final Exception e) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                AttrGet.postMain(new Runnable() {
                    @Override
                    public void run() {
                        reject(e);
                    }
                });
            } else {
                reject(e);
            }
        }
    }

    /**
     * 结束
     */
    private void onPromiseEnd() {
        if (!isEnded) {
            if (prePromise != null) {
                prePromise.onPromiseEnd();
            }
            onEnd();
            callbacks.clear();
            callbacks = null;
            errorCallback = null;
            isEnded = true;
        }
    }

    /**
     * 执行异步模型
     */
    protected abstract void execute();

    /**
     * 异步模型结束
     */
    protected abstract void onEnd();
}
