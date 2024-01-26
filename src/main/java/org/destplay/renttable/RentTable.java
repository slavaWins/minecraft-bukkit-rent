package org.destplay.renttable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.destplay.renttable.handles.CommandsHandle;
import org.destplay.renttable.handles.SignRentHandle;
import org.destplay.renttable.helpers.ChatHelper;
import org.destplay.renttable.listeners.SignListener;
import org.destplay.renttable.repositories.RegionsRepository;

public final class RentTable extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigHelper.Init(getDataFolder());
        RegionsRepository.Init(getDataFolder());
        //getServer().getPluginManager().registerEvents(new ExampleListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);



        //Запускаем Таск для чистки аренд
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                // Вызов функции, которую вы хотите выполнить на сервере
                SignRentHandle.UpdateRentsAll();
            }
        };

        // Запуск задачи с периодом в 20 секунд (другие опции также доступны)
        task.runTaskTimer(this, 0, 60*20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("rent")) return false;



        if (args.length == 0) {
            sender.sendMessage(ChatHelper.PREFIX + " Нужен аргумент команды например /rent list что бы посмотреть свои аренды");
            return true;
        }
        if ((sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("list")) {
                CommandsHandle.ListCommand(sender, sender.getName());
                return true;
            }
        }


        if (!sender.isOp()) {
            sender.sendMessage("Не хватает прав для выполнения команды");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            // что-то выполняется
            ConfigHelper.Init(getDataFolder());
            reloadConfig();
            saveConfig();
            sender.sendMessage(ChatHelper.PREFIX + " Конфигурация была перезагружена!");

            return true;
        }


        if (args[0].equalsIgnoreCase("all")) {

            CommandsHandle.ListCommand(sender,"");

            return true;
        }


        if (args[0].equalsIgnoreCase("tp") && args.length == 2) {

            CommandsHandle.TpTo(sender, args[1]);

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