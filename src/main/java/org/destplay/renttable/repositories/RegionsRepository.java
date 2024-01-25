package org.destplay.renttable.repositories;

import com.google.gson.Gson;
import org.destplay.renttable.ConfigHelper;
import org.destplay.renttable.contracts.RentModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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


    public static boolean Save(List<RentModel> list)  throws IOException{
        myList = list;

         Gson gson = new Gson();
        System.out.println(file.getAbsolutePath());

        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(myList, writer);
        writer.flush();
        writer.close();


        return true;
    }

    public static void Load() throws IOException  {
        Gson gson = new Gson();

        if (file.exists()){
            Reader reader = new FileReader(file);
            RentModel[] n = gson.fromJson(reader, RentModel[].class);
            myList = new ArrayList<>(Arrays.asList(n));
        }
    }


    public static void Init(File dataFoolder) {

        if (!dataFoolder.exists()) dataFoolder.mkdirs();


        file = new File(dataFoolder, "rents-data.json");

        if(ConfigHelper.IsDebug()) {
        System.out.println("----- [RENT TABLE] Init.");
        }


        try {
            Load();
        } catch (IOException e) {

            System.out.println("----- [RENT TABLE] Ошибка загрузки rents-data.json возможно файл повережден");
            e.printStackTrace();
        }


    }
}
