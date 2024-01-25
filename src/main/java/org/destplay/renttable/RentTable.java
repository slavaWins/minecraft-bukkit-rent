package org.destplay.renttable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.listeners.ExampleListener;
import org.destplay.renttable.listeners.SignListener;
import org.destplay.renttable.repositories.RegionsRepository;

import java.util.List;

public final class RentTable extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigHelper.Init(getDataFolder());
        getServer().getPluginManager().registerEvents(new ExampleListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("rnt")) return false;

        if (!sender.isOp()) {
            sender.sendMessage("Не хватает прав для выполнения команды");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("[RentTable] Нужен аргумент команды");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            // что-то выполняется
            ConfigHelper.Init(getDataFolder());
            reloadConfig();
            saveConfig();
            sender.sendMessage("[RentTable] Конфигурация была перезагружена!");

            return true;
        }


        if (args[0].equalsIgnoreCase("list")) {
            List<RentModel> regions = RegionsRepository.Get();
            sender.sendMessage("[RentTable] Зарегано регионов: "+regions.size());
            for (int i = 0; i < regions.size(); i++) {
                RentModel map = regions.get(i);
                sender.sendMessage(ChatColor.GREEN +map.region+  ChatColor.RESET +" цена "+ map.price+ ChatColor.GRAY+ ". Владелец: "+  ChatColor.GREEN + map.currentRentLogin);
            }
            return true;
        }

        return false;
    }

    /*
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }*/
}