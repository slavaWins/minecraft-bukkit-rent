package org.destplay.renttable.contracts;

import java.util.Date;

public class RentModel {

    public Integer price;
    public String currentRentLogin;
    public String region;
    public Date currentRentTo;

    /**
     * Актуальна ли аренда сейчас?
     * @return
     */
    public boolean IsActual(){
        Date currentDate = new Date();
        return currentRentTo.compareTo(currentDate) > 0;
    }
}
