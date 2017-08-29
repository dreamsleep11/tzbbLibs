package com.dhcc.workbench.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式帮助类
 * Created by 张立伟 on 2016/7/12.
 */
public class RegExp {

    /**
     * 正则表达式替换方程
     */
    public abstract static class ReplaceFunc {

        /**
         * 当前处于第几次替换
         * 从0开始
         */
        protected int index;

        /**
         * 根据输入的匹配项返回替换的对象
         *
         * @param args args[0]=matcher args[1]=$1,args[2]=$2 ......
         * @return 替换字符串
         * @throws ReplaceException
         */
        public abstract String func(String... args) throws ReplaceException;
    }

    /***
     * 根据正则表达式替换字符串并返回
     * @param str 被替换的字符串
     * @param regexp 正则表达式
     * @param func 替换function 每次接受匹配到的字符串 并返回替换的字符串
     * @return 替换好的字符串
     * @throws ReplaceException
     */
    public static String replace(String str, String regexp, ReplaceFunc func) throws ReplaceException {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        func.index = 0;
        while (matcher.find()) {
            String parms[] = new String[matcher.groupCount() + 1];
            for (int i = 0; i < parms.length; i++) {
                parms[i] = matcher.group(i);
            }
            if (parms.length != 0) {
                matcher.appendReplacement(sb, func.func(parms));
                func.index++;
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 根据正则表达式替换字符串并返回
     *
     * @param str         被替换的字符串
     * @param regexp      正则表达式
     * @param replaceMent 替换文本
     * @return 替换好的字符串
     */
    public static String replace(String str, String regexp, String replaceMent) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, replaceMent);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 返回是否验证成功
     *
     * @param str    待验证字符串
     * @param regexp 正则表达式
     * @return 验证成功返回true 否则返回false
     */
    public static boolean test(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static List<String> match(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                list.add(matcher.group(i));
            }
        }
        return list;
    }
}
