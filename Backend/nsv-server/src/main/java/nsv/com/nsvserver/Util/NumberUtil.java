package nsv.com.nsvserver.Util;

import java.text.DecimalFormat;

public class NumberUtil {
    public static String removeTrailingZero(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedNumber = decimalFormat.format(number);

        // If the formatted number ends with ".0", remove it
        if (formattedNumber.endsWith(".0")) {
            formattedNumber = formattedNumber.substring(0, formattedNumber.length() - 2);
        }

        return formattedNumber;
    }
}
