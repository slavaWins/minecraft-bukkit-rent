# Плагин для аренды
### 1.16.5 bukkit


# Требуется:
Плагин WorldGuard 


# Команды:
/rent list что бы увидеть свои аренды, для игрока

/rent all что бы увидеть все аренды сервера

/rent reload что бы перезагрузить конфиг



## Настройки конфига
````
debug: true  //выводить логи
valute: IRON_INGOT //предмет которым расплачивается игрок за аренду
valute-name: железа //название предмета, как будет писаться в чат
rent-duration: 15 //длительность аренды в секундах, для теста 15 сек
valute-view-coficient: 3 //коэффициент на который умножается сумма валюты когда показывается игроку, можно оставить 1
regions: []
````




## Как пользоваться
Создаем регион /rg claim home1 

Ставим рядом табличку и пишем в ней:
````
[RENT]
2
home1
````
 2 - это цена аренды за сутки