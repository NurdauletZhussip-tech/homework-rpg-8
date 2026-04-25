package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    private int duration;

    public BerserkState(int duration) {
        this.duration = duration;
    }

    @Override
    public String getName() {
        return "Berserk for (" + duration + " seconds)";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 1.5);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 1.3);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println("(BERSERK) " + hero.getName() + " attacks with anger");
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