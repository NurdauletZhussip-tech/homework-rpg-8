package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.ArrayList;
import java.util.List;

public class TowerRunner {
    private final List<TowerFloor> floors;
    public TowerRunner(List<TowerFloor> floors) {
        this.floors = new ArrayList<>(floors);
    }
    public TowerRunResult run(List<Hero> heroes) {
        int floorsCleared = 0;
        List<Hero> currentParty = new ArrayList<>(heroes);

        for (TowerFloor floor : floors) {
            if (currentParty.stream().noneMatch(Hero::isAlive)) {
                System.out.println("heroes fallen!");
                break;
            }

            System.out.println("\n=== next floor ===");
            FloorResult result = floor.explore(currentParty);

            if (result.isCleared()) {
                floorsCleared++;
                System.out.println("Floor cleared " + result.getSummary());
            } else {
                System.out.println("Failed to clear floor: " + result.getSummary());
                break;
            }
        }

        int heroesSurviving = (int) currentParty.stream().filter(Hero::isAlive).count();
        boolean reachedTop = floorsCleared == floors.size() && heroesSurviving > 0;

        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }
}