package top.bestplayer.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String dateFormate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(date);
    }
}
