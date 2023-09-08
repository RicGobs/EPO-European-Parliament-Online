package com.euparliament.broadcast.utils;

import java.util.Arrays;
import java.util.List;

public class Parse {
    
    public static List<String> parsingMessage(String message){

        List<String> nations= Arrays.asList(message.split(","));
        return nations;
    }

    

}
