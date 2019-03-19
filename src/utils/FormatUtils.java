package utils;

public class FormatUtils {

    public static String formatMonetaryValue(Long cents) {
        String full = Long.toString(cents / 100, 10);
        String decimals = Long.toString(cents % 100, 10);
        if (decimals.length() == 1) {
            decimals = "0" + decimals;
        }
        return full + "," + decimals + " PLN";
    }

}
