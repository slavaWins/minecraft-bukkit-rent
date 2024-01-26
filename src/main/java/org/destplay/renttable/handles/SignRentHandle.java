package org.destplay.renttable.handles;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.destplay.renttable.ConfigHelper;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.helpers.ChatHelper;
import org.destplay.renttable.helpers.DateHelper;
import org.destplay.renttable.helpers.RgClaimHelper;
import org.destplay.renttable.helpers.VaultHelper;
import org.destplay.renttable.repositories.RegionsRepository;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class SignRentHandle {


    public static void UpdateRentsAll() {


        if (ConfigHelper.IsDebug()) {
            System.out.println(ChatColor.YELLOW + ChatHelper.PREFIX + " Запущенно выселение просроченых аренды");
        }

        boolean isUpdated = false;
        for (int i = 0; i < RegionsRepository.Get().size(); i++) {
            RentModel map = RegionsRepository.Get().get(i);
            if (UpdateRent(map)) isUpdated = true;
        }

        if (isUpdated) {
            try {
                RegionsRepository.Save(RegionsRepository.Get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean UpdateRent(RentModel rentModel) {

        if (rentModel.currentRentLogin.isEmpty()) return false;
        if (rentModel.IsLocked()) return false;

        if (ConfigHelper.IsDebug()) {
            System.out.println(ChatColor.YELLOW + ChatHelper.PREFIX + " Выселение    из региона  " + rentModel.region + " игрока " + rentModel.currentRentLogin);
        }
        RgClaimHelper.RemoveMember(rentModel.region, rentModel.currentRentLogin);
        rentModel.currentRentLogin = "";
        return true;
    }


    public static void InteractRentTable(Player player, String[] lines) {

        // Ваш код для обработки события
        String region = lines[2]; // получение значения Arg0

        RentModel rentModel = RegionsRepository.FindByRegion(lines[2]);

        if (rentModel == null) {
            player.sendMessage("Не найдена инфа о регионе");
            return;
        }

        if (UpdateRent(rentModel)) {
            try {
                RegionsRepository.Save(RegionsRepository.Get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        player.sendMessage(ChatColor.BOLD + "----------------ИНФОРМАЦИЯ О АРЕНДЕ----------\n\n");

        if (rentModel.IsLocked()) {
            player.sendMessage(ChatColor.RESET + "Владелец: " + ChatColor.AQUA + rentModel.currentRentLogin);
            player.sendMessage("Аренда истекает через: " + DateHelper.calculateDateDifference(new Date(), rentModel.currentRentTo));
        } else {
            player.sendMessage(ChatColor.GREEN + "РЕГИОН СВОБОДЕН ДЛЯ АРЕНДЫ");
        }

        player.sendMessage(ChatColor.RESET + "Цена: " + ChatColor.AQUA + (rentModel.price * ConfigHelper.GetConfig().getInt("valute-view-coficient", 1)) + ChatColor.RESET + " " + VaultHelper.ValuteName() + " в сутки");
        player.sendMessage(ChatColor.GRAY + " * В реальные сутки, 24 часа реального времени. ");


        Material itemMaterial = VaultHelper.ValuteId();


        if (player.getItemInHand().getType() != itemMaterial) {
            if (!rentModel.IsLocked()) {
                player.sendMessage(ChatColor.GRAY + "Для  аренды возьмите в руки " + VaultHelper.ValuteName() + " и кликнете по табличке  ");
                return;
            }

            if (rentModel.IsLocked() && rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
                player.sendMessage(ChatColor.GRAY + "Для продления аренды возьмите в руки " + VaultHelper.ValuteName() + " и кликнете по табличке  ");
                return;
            }
            return;
        }

        if (rentModel.IsLocked() && !rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
            player.sendMessage(ChatColor.RED + "Вы не можете арендовать уже занятый дом!");
            return;
        }


        int _count = VaultHelper.getGoldIngotCount(player);
        if (_count < rentModel.price) {
            player.sendMessage("У вас не хватает " + VaultHelper.ValuteName() + ". Сейчас у вас " + _count + " из  " + rentModel.price);
            return;
        }

        VaultHelper.takeGoldIngots(player, rentModel.price);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //Если я продляюсь
        if (rentModel.IsLocked() && rentModel.currentRentLogin.equalsIgnoreCase(player.getDisplayName())) {
            calendar.setTime(rentModel.currentRentTo);
        } else {
            RgClaimHelper.AddMember(rentModel.region, player.getDisplayName());
        }

        calendar.add(Calendar.SECOND, ConfigHelper.GetConfig().getInt("rent-duration")); // Добавляем 10 секунд
        rentModel.currentRentLogin = player.getDisplayName();
        rentModel.currentRentTo = calendar.getTime();


        try {
            RegionsRepository.Save(RegionsRepository.Get());
        } catch (IOException e) {
            e.printStackTrace();
        }


        player.sendMessage(ChatColor.GREEN + "----- Вы арендовали этот дом! " + " \n Аренда истекает через: " + DateHelper.calculateDateDifference(new Date(), rentModel.currentRentTo));

    }


}