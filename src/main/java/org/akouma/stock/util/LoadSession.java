package org.akouma.stock.util;


import org.akouma.stock.entity.Boutique;

import javax.servlet.http.HttpSession;


public class LoadSession {
    public static boolean sessionExiste(HttpSession httpSession) {
        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        if (boutique != null) {
            return true;
        }
        return false;
    }
}
