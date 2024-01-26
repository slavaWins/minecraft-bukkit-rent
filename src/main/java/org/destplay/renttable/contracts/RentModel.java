package org.destplay.renttable.contracts;

import java.util.Date;

public class RentModel {

    public Integer price;
    public String currentRentLogin;
    public String region;
    public Date currentRentTo;
    public double  x;
    public double  y;
    public double  z;
    public String world;

    /**
     * Актуальна ли аренда сейчас?
     * @return
     */
    public boolean IsLocked(){
        Date currentDate = new Date();
        return currentRentTo.compareTo(currentDate) > 0;
    }
}
