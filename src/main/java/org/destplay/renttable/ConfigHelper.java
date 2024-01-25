package org.destplay.renttable;

import org.bukkit.configuration.file.YamlConfiguration;
import org.destplay.renttable.contracts.RentModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigHelper {

    private static File file;
    private static YamlConfiguration yamlConfiguration;

    public static void DefYmlConfi() {
        yamlConfiguration.set("join-mess", "Привет игрок @p");
        yamlConfiguration.set("enabled", true);

        List<RentModel> listRegions = new ArrayList<>();
        yamlConfiguration.set("regions", listRegions);
    }


    public static void Save(){
        try {
            yamlConfiguration.save(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static YamlConfiguration GetConfig(){
        return yamlConfiguration;
    }

    public static void Init(File dataFoolder) {
        if (!dataFoolder.exists()) dataFoolder.mkdirs();


        file = new File(dataFoolder, "rento.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
                DefYmlConfi();
                yamlConfiguration.save(file);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }


}
