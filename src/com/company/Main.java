package com.company;

import java.util.Random;

/*ДЗ:
Добавить 4-го игрока Medic, у которого есть способность лечить после каждого раунда на N-ное количество единиц
 здоровья только одного из членов команды, имеющего здоровье менее 100 единиц. Мертвых героев медик оживлять не может,
  и лечит он до тех пор пока жив сам. Медик не участвует в бою, но получает урон от Босса. Сам себя медик лечить не может.
ДЗ на сообразительность:
Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар.
Может принимать на себя 1/5 часть урона исходящего от босса по другим игрокам.
Добавить n-го игрока, Lucky, имеет шанс уклонения от ударов босса.
Добавить n-го игрока, Berserk, блокирует часть удара босса по себе и прибавляет заблокированный урон к своему урону и возвращает его боссу
Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд, вследствие чего босс пропускает 1 раунд
и не наносит урон героям. // random.nextBoolean(); - true, false
Дэдлайн 10.02.2022 19 00*/
public class Main {
    public static String bossDefenceType;
    public static int bossDamage = 65;
    public static int bossHealth = 900;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem","Medic","Lucky"};
    public static int[] heroesDamage = {25, 20, 15, 5,0,23};
    public static int[] heroesHealth = {280, 270, 250, 1000,230,240};
    public static int roundNumber = 0;


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }

    }
     public static void supportingOfMedic(){
       Random random = new Random();
     int healing=random.nextInt(60)+9;
    int heroesIndex = random.nextInt(heroesHealth.length-2);
     if (heroesHealth[heroesIndex]<100 && heroesHealth[heroesIndex]>0){
       heroesHealth[heroesIndex]=heroesHealth[heroesIndex]+healing;
     System.out.println("Medic heal "+ heroesAttackType[heroesIndex]+" on "+String.valueOf(healing)+" level");
          }
    }
    public static void round() {
        roundNumber++;
        bossDefenceType = chooseDefence();
        bossHits();
        heroesHit();
        printStatistics();
        supportingOfMedic();

    }


    public static String chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        System.out.println("Boss chose " + heroesAttackType[randomIndex]);
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        int golemesHelp = 0;
        golemesHelp = bossDamage / 5;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                }else heroesHealth[i] =heroesHealth[i]- bossDamage+golemesHelp;
            }
        }
        for (int i = 0; i <1 ; i++) {
            heroesHealth[3]=heroesHealth[3]-(golemesHelp*(heroesHealth.length-1));
            if (heroesHealth[3]<0){
                heroesHealth[3]=0;
            }
        }
        System.out.println("Голем принял на себя: "+golemesHelp*(heroesHealth.length-1)+"урона!");
    }


    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] + " x "
                            + coeff + " = " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/

        int totalHealth = 0;
        for (int health : heroesHealth) {
            totalHealth += health; // totalHealth = totalHealth + health;
        }
        if (totalHealth <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND _________________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }


}
