package com.ire.combat.actions.attackactions;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.Action;

public abstract class AttackAction extends Action {

    protected final AudioStream SOUND;
    protected final int DURATION;
    protected final int DELAY;

    // Remember cases where masteries may impact coefficient stat
    protected int damage;
    protected float coefficient;

    public AttackAction(String name, String description, AudioStream SOUND, int DURATION,
                        int DELAY, float coefficient) {
        super(name, description);

        this.SOUND = SOUND;
        this.DURATION = DURATION;
        this.DELAY = DELAY;
        this.coefficient = coefficient;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void incrementDamage(int increment) {
        this.damage += increment;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public AudioStream getSOUND() {
        return SOUND;
    }
}
