package org.destplay.renttable.handles;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.helpers.DateHelper;
import org.destplay.renttable.helpers.VaultHelper;
import org.destplay.renttable.repositories.RegionsRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignRentHandle {



    public static void InteractRentTable(Player player, String[] lines) {

        // Ваш код для обработки события
        String region = lines[2]; // получение значения Arg0

        RentModel rentModel = RegionsRepository.FindByRegion(lines[2]);

        if (rentModel == null) {
            player.sendMessage("Не найдена инфа о регионе");
            return;
        }


        player.sendMessage(ChatColor.BOLD + "\n----------------ИНФОРМАЦИЯ О АРЕНДЕ----------\n");

        if (rentModel.IsActual()) {
            player.sendMessage(ChatColor.RESET + "Владелец: " + ChatColor.AQUA + rentModel.currentRentLogin);
            player.sendMessage( "Аренда истекает через: "+ DateHelper.calculateDateDifference(new Date(), rentModel.currentRentTo));
        } else {
            player.sendMessage(ChatColor.GREEN + "РЕГИОН СВОБОДЕН ДЛЯ АРЕНДЫ");
        }

        player.sendMessage(ChatColor.RESET + "Цена: " + ChatColor.AQUA + (rentModel.price * 100) + ChatColor.UNDERLINE + " золота в сутки" + ChatColor.GRAY + " \n * В реальные сутки, 24 часа реального времени ");


        Material itemMaterial = Material.GOLD_INGOT;

        if (player.getItemInHand().getType() != itemMaterial) {
            if (!rentModel.IsActual()) {
                player.sendMessage(ChatColor.GRAY + "Для  аренды возьмите в руки золото и кликнете по табличке  ");
                return;
            }

            if (rentModel.IsActual() && rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
                player.sendMessage(ChatColor.GRAY + "Для продления аренды возьмите в руки золото и кликнете по табличке  ");
                return;
            }
            return;
        }

        if (rentModel.IsActual() && !rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
            player.sendMessage(ChatColor.RED + "Вы не можете арендовать уже занятый дом!");
            return;
        }


        int _count = VaultHelper.getGoldIngotCount(player);
        if (_count < rentModel.price) {
            player.sendMessage("Не хватает золота у вас: " + _count);
            return;
        }
        player.sendMessage("У вас золота: " + _count);
        VaultHelper.takeGoldIngots(player, rentModel.price);
        player.sendMessage("Тепрерь у вас золота: " + VaultHelper.getGoldIngotCount(player));


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //Если я продляюсь
        if (rentModel.IsActual() && rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
            calendar.setTime(    rentModel.currentRentTo );
        }

        calendar.add(Calendar.SECOND, 12); // Добавляем 10 секунд
        rentModel.currentRentLogin = player.getDisplayName();
        rentModel.currentRentTo = calendar.getTime();

        player.sendMessage(ChatColor.GREEN + "----- Вы арендовали этот дом! " + " \n Аренда истекает через: "+ DateHelper.calculateDateDifference(new Date(), rentModel.currentRentTo));

    }

/*
    public static void InteractRentTableOld(Player player, String[] lines) {

        // Ваш код для обработки события
        String arg0 = lines[1]; // получение значения Arg0
        // Выполнение нужных действий
        player.sendMessage("Вы нажали на табличку с текстом [RENT] " + arg0);

        int _need = 0;

        try {
            String _s = lines[1].replace("золота/час", "").trim();
            _need = Integer.parseInt(_s);
        } catch (NumberFormatException e) {
            player.sendMessage("Не удалось считать сумму таблички");
            System.out.println("Неверный формат числа");
            return;
        }

        _need /= 100;


        int _count = VaultHelper.getGoldIngotCount(player);
        if (_count < _need) {
            player.sendMessage("Не хватает золота у вас: " + _count);
            return;
        }
        player.sendMessage("У вас золота: " + _count);
        VaultHelper.takeGoldIngots(player, _need);
        player.sendMessage("Тепрерь у вас золота: " + VaultHelper.getGoldIngotCount(player));


        player.sendMessage(ChatColor.BOLD + "\n----------------ИНФОРМАЦИЯ О АРЕНДЕ----------");
        player.sendMessage(ChatColor.RESET + "Арендодатель: " + ChatColor.AQUA + "Гильдия");
        player.sendMessage(ChatColor.RESET + "Стоимость аренды: " + ChatColor.AQUA + (_need * 100) + ChatColor.UNDERLINE + " золота в сутки" + ChatColor.GRAY + " (В реальные сутки, 24 часа) ");
        player.sendMessage(ChatColor.RESET + "Для аренды нажмите на команду " + ChatColor.GREEN + "/rent id");
    }*/


    public static void ListCommand(CommandSender sender) {

        List<RentModel> regions = RegionsRepository.Get();
        sender.sendMessage("[RentTable] Зарегано регионов: "+regions.size());
        for (int i = 0; i < regions.size(); i++) {
            RentModel map = regions.get(i);

            String msg = "";
            if(map.IsActual()) {
                msg+= ChatColor.RED +  "[Занято] ";
            }else {
                msg+= ChatColor.GRAY +  "[СВОБОДНО] ";
            }

            msg+=ChatColor.RESET +map.region+  ChatColor.RESET +" цена "+ map.price+ ChatColor.GRAY;

            if(map.IsActual()) {
              msg+=" Владелец: " + ChatColor.GREEN + map.currentRentLogin + " \n Истекает через: "+ DateHelper.calculateDateDifference(new Date(), map.currentRentTo);
            }
            sender.sendMessage(msg);
        }

    }
}
