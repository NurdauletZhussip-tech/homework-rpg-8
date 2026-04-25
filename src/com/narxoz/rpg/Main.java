package com.narxoz.rpg;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.PuzzleFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StunnedState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entry point for Homework 8 — The Haunted Tower: Ascending the Floors.
 *
 * Build your heroes, floors, tower runner, and execute the climb here.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("=== The Haunted Tower ====");
        System.out.println("========================================\n");

        Hero tank = new Hero("Grom", 120, 25, 12, new NormalState());
        Hero wizard = new Hero("Elara", 90, 30, 8, new NormalState());
        List<Hero> party = Arrays.asList(tank, wizard);

        System.out.println("--- Heroes enter the tower ---");
        System.out.printf("%s (HP: %d, ATK: %d)\n", tank.getName(), tank.getHp(), tank.getAttackPower());
        System.out.printf("%s (HP: %d, ATK: %d)\n\n", wizard.getName(), wizard.getHp(), wizard.getAttackPower());

        System.out.println("--- State Transition Demo ---");
        tank.setState(new PoisonedState(3));

        List<TowerFloor> towerLevels = new ArrayList<>(Arrays.asList(
                new CombatFloor("Skeleton Crypt", new Monster("Skeleton Warrior", 60, 18)),
                new TrapFloor("Poison Trap Hallway", 12, 42L),
                new CombatFloor("Orc Camp", new Monster("Orc Berserker", 80, 22)),
                new PuzzleFloor("Riddle Chamber"),
                new CombatFloor("Dragon's Lair", new Monster("Young Dragon", 150, 30))
        ));

        System.out.println("\n--- Tower Floors ---");
        int floorCounter = 1;
        for (TowerFloor floor : towerLevels) {
            System.out.println(floorCounter++ + ". " + floor.getClass().getSimpleName());
        }

        TowerRunner gameSession = new TowerRunner(towerLevels);
        TowerRunResult outcome = gameSession.run(party);

        System.out.println("\n========================================");
        System.out.println("=== TOWER RUN RESULT ===");
        System.out.println("========================================");
        System.out.println("Floors cleared: " + outcome.getFloorsCleared() + " / " + towerLevels.size());
        System.out.println("Heroes surviving: " + outcome.getHeroesSurviving() + " / " + party.size());
        System.out.println("Reached the top: " + (outcome.isReachedTop() ? "YES!" : "NO"));

        System.out.println("\n--- Final Hero Status ---");
        party.forEach(character -> {
            String healthStatus = character.isAlive()
                    ? String.format("HP: %d/%d", character.getHp(), character.getMaxHp())
                    : "DEAD";
            System.out.printf("%s: %s [%s]%n", character.getName(), healthStatus, character.getState().getName());
        });

        System.out.println("\n=== Demo Complete ===");
    }
}
