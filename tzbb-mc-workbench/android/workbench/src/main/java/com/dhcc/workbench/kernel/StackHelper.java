package com.dhcc.workbench.kernel;

import android.util.Log;

/**
 * 打印Throwable的堆栈异常到logcat
 * Created by 张立伟 on 2016/7/12.
 */
public class StackHelper {

    private static final String TAG="blpapp";

    /**
     * 打印前面的调用堆栈
     * @param line
     */
    public static void printStack(int line){
        StackTraceElement[] elems = new Throwable().getStackTrace();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=1;i<line&&i<elems.length;i++){
            StackTraceElement elem = elems[i];
            stringBuilder.append("at ").append(elem.getClassName())
                    .append(".").append(elem.getMethodName())
                    .append("(").append(elem.getFileName())
                    .append(":").append(elem.getLineNumber())
                    .append(")\n");
        }
        Log.e(TAG,stringBuilder.toString());
    }

    private static void readErrorLog(Throwable throwable, StringBuilder stringBuilder){
        StackTraceElement[] stack = throwable.getStackTrace();

        String message=throwable.getMessage();
        stringBuilder.append(throwable.getClass().getName());
        if(message!=null)
                    stringBuilder.append(":")
                    .append(message);
        stringBuilder.append("\n");
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement element = stack[i];
            stringBuilder.append("at ").append(element.getClassName())
                    .append(".").append(element.getMethodName())
                    .append("(").append(element.getFileName())
                    .append(":").append(element.getLineNumber())
                    .append(")\n");
        }
        Throwable cause=throwable.getCause();
        if(cause!=null){
            stringBuilder.append("Caused by: ");
            readErrorLog(cause,stringBuilder);
        }
    }

    /**
     * 打印异常堆栈
     * @param throwable
     */
    public static void printStack(Throwable throwable){
        StringBuilder stringBuilder=new StringBuilder();
        readErrorLog(throwable,stringBuilder);
        String info=stringBuilder.toString();
        String infos[]=info.split("\\n");
        for(int i=0;i<infos.length;i++) {
            Log.e(TAG,"\t"+infos[i] + "\n");
        }
    }

}
