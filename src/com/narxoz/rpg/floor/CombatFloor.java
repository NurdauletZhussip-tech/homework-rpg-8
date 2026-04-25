package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

public class CombatFloor extends TowerFloor {
    private final String floorName;
    private final Monster monster;
    private int totalDamageTaken;

    public CombatFloor(String name, Monster monster) {
        this.floorName = name;
        this.monster = monster;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        totalDamageTaken = 0;
        System.out.println("A " + monster.getName() + " blocks the way!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        Monster currentMonster = monster;
        int round = 0;

        while (currentMonster.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            round++;
            System.out.println("\n  Round " + round);

            for (Hero hero : party) {
                if (!hero.isAlive() || !currentMonster.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int damage = hero.getEffectiveDamage();
                    currentMonster.takeDamage(damage);
                    System.out.println("    " + hero.getName() + " hits " + currentMonster.getName() + " for " + damage);
                } else {
                    System.out.println("    " + hero.getName() + " cannot act!");
                }

                hero.onTurnEnd();
            }

            if (currentMonster.isAlive()) {
                for (Hero hero : party) {
                    if (!hero.isAlive() || !currentMonster.isAlive()) continue;

                    int monsterDamage = currentMonster.getAttackPower();
                    int reducedDamage = hero.getEffectiveDefense();
                    int finalDamage = Math.max(1, monsterDamage - 2);
                    hero.takeDamage(finalDamage);
                    totalDamageTaken += finalDamage;
                    System.out.println("    " + monster.getName() + " hits " + hero.getName() + " for " + finalDamage);

                    if (!hero.isAlive()) {
                        System.out.println("    " + hero.getName() + " is fallen!");
                    }
                }
            }
        }

        boolean cleared = currentMonster.getHp() <= 0;
        return new FloorResult(cleared, totalDamageTaken,
                cleared ? "Defeated " + monster.getName() : "Failed to defeat " + monster.getName());
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot: Diamonds");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(15);
                }
            }
        }
    }
}