package com.zry.framework.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {
    public static String formatNumberString(long number){
        if(number < 10000){
            return String.valueOf(number);
        }else if(10000<=number&&number<100000000){
            double result = number / 10000.0;
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.DOWN);
            String formatted = df.format(result);
            return formatted + "万";
        }else{
            return "9999万+";
        }
    }
}
