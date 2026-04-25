package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.BerserkState;
import java.util.List;

public class PuzzleFloor extends TowerFloor {
    private final String floorName;
    private int totalDamageTaken;
    private boolean puzzleSolved;

    public PuzzleFloor(String name) {
        this.floorName = name;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        totalDamageTaken = 0;
        puzzleSolved = false;
        System.out.println("A riddle echoes through the chamber...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("  Riddle: 'What gets wetter as it dries?'");
        System.out.println("  The heroes think carefully...");

        puzzleSolved = true;

        if (puzzleSolved) {
            System.out.println("  The heroes answer correctly: 'A towel!'");
            System.out.println("  The door opens!");

            for (Hero hero : party) {
                if (hero.isAlive() && hero.getHp() < hero.getMaxHp() * 0.5) {
                    hero.setState(new BerserkState(3));
                }
            }
        }

        return new FloorResult(puzzleSolved, totalDamageTaken,
                puzzleSolved ? "Solved the puzzle!" : "Failed the puzzle");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot: Gold");
            for (Hero hero : party) {
                hero.heal(10);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The puzzle room fades away behind you.");
    }
}