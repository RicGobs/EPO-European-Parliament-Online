package com.euparliament.broadcast.utils;

import java.util.Arrays;
import java.util.List;

public class Parse {
    
    public static List<String> splitStringByComma(String message){

        return Arrays.asList(message.split(","));

    }

    public static String joinListByComma(List<String> list){

        return String.join(",", list);
        
    }

    

}
