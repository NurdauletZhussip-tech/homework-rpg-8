package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int duration;

    public PoisonedState(int duration) {
        this.duration = duration;
    }

    @Override
    public String getName() {
        return "Poisoned (" + duration + " seconds)";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 0.8);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        int poisonDamage = 5;
        hero.takeDamage(poisonDamage);
        System.out.println("(POISON) " + hero.getName() + " takes " + poisonDamage + " poison DMG");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        duration--;
        if (duration <= 0) {
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}