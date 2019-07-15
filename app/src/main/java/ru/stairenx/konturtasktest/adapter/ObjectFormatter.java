package ru.stairenx.konturtasktest.adapter;

import java.util.StringTokenizer;

public class ObjectFormatter {

    public static String getValidNumber(String formatterNumber){
        String result = "";
        StringTokenizer tokenizer = new StringTokenizer(formatterNumber,"+()- ");
        while (tokenizer.hasMoreTokens()){
            result+=tokenizer.nextToken();
        }
        return result;
    }

    public static String getValidDate(String formatterDate){
        return formatterDate.substring(8,10)+"."+formatterDate.substring(5,7)+"."+formatterDate.substring(0,4);
    }

}
