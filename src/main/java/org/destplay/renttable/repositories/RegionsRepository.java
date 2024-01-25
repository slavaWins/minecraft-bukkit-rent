package org.destplay.renttable.repositories;

import org.destplay.renttable.contracts.RentModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import org.json.simple;

public class RegionsRepository {

    private static File file;

    //private JSONObject json; // org.json.simple
    //private JSONParser parser = new JSONParser();
    private static List<RentModel> myList = new ArrayList<>();


    public static RentModel FindByRegion(String name){
        for (RentModel rentModel : myList) {
            if (rentModel.region.equals(name)) {
                return rentModel;
            }
        }
        return null;
    }

    public static List<RentModel> Get() {
        return myList;
    }


    public static boolean Save(List<RentModel> list) {
        myList = list;/*
        try {
           /jsonConfiguration.set("rents", myList);
            jsonConfiguration.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    public static void Init(File dataFoolder) {

        if (!dataFoolder.exists()) dataFoolder.mkdirs();


        file = new File(dataFoolder, "rents.json");

        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

       // List<RentModel> datas =   (List<RentModel>)jsonConfiguration.getList("rents");

       // ArrayList<RentModel> list = new ArrayList<>( jsonConfiguration.getStringList("rents") );

       // myList = new ArrayList<>( (ArrayList<RentModel>) list;

        //myList = (List<RentModel>) myList1;
    }
}
