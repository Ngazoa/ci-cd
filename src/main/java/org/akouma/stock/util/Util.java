package org.akouma.stock.util;

import java.util.Calendar;

public class Util {
    public String getReferenece() {
        Calendar calendar = Calendar.getInstance();
        String referenece = "" + calendar.getTimeInMillis();
        return referenece;
    }
}
