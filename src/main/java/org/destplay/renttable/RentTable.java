package org.destplay.renttable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.handles.SignRentHandle;
import org.destplay.renttable.listeners.ExampleListener;
import org.destplay.renttable.listeners.SignListener;
import org.destplay.renttable.repositories.RegionsRepository;

import java.util.List;

public final class RentTable extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigHelper.Init(getDataFolder());
        RegionsRepository.Init(getDataFolder());
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

            SignRentHandle.ListCommand(sender);

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