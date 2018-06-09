package iaccounting.csie.com.iaccounting.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zixuan Zhao on 2/27/17.
 */

public class Tools {

    private static DateFormat onlyDateFormat = new SimpleDateFormat("MM dd, yyyy");
    private static DateFormat dateTimeformat = new SimpleDateFormat("MM dd, yyyy    HH:mm");

    public static Locale getCurrentLocale(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        }else{
            return context.getResources().getConfiguration().locale;
        }
    }

    public static String getCurrentDate(){
        return onlyDateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateTime(){
        return dateTimeformat.format(Calendar.getInstance().getTime());
    }

    public static String setDateFormat(Calendar calendar){
        return onlyDateFormat.format(calendar.getTime());
    }
}
