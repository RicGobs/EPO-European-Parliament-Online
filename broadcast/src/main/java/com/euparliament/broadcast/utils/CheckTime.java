package com.euparliament.broadcast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CheckTime {

    public static Boolean CheckDatetime(String dateEnd){
        
		Calendar c = Calendar.getInstance();
        Date current = c.getTime();  

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date end = current;
        
        try {
            end = format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        } 

        long duration = end.getTime() - current.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toMillis(duration);

        try {
            TimeUnit.MILLISECONDS.sleep(diffInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
        
        return true;   
    }
}
