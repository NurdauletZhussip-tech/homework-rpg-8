package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;
import java.util.List;
import java.util.Random;

public class TrapFloor extends TowerFloor {
    private final String floorName;
    private final int trapDamage;
    private final Random random;
    private int totalDamageTaken;

    public TrapFloor(String name, int trapDamage, long seed) {
        this.floorName = name;
        this.trapDamage = trapDamage;
        this.random = new Random(seed);
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n*** " + floorName + " ***");
        System.out.println("You hear clicking sounds... Traps are everywhere!");
    }

    @Override
    protected void setup(List<Hero> party) {
        totalDamageTaken = 0;
        System.out.println("Spikes, arrows, and pressure plates surround you.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            if (random.nextDouble() < 0.6) {
                hero.takeDamage(trapDamage);
                totalDamageTaken += trapDamage;
                System.out.println("  " + hero.getName() + " triggers a trap! Takes " + trapDamage + " damage!");

                if (random.nextDouble() < 0.4) {
                    hero.setState(new PoisonedState(3));
                }
            } else {
                System.out.println("  " + hero.getName() + " dodges a trap!");
            }

            if (!hero.isAlive()) {
                System.out.println("  " + hero.getName() + " has fallen to the traps!");
            }
        }

        boolean cleared = party.stream().anyMatch(Hero::isAlive);
        return new FloorResult(cleared, totalDamageTaken,
                cleared ? "Survived the trap floor" : "Everyone died to traps");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared() && result.getDamageTaken() < 20;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Loot: Emeralds");
    }
}