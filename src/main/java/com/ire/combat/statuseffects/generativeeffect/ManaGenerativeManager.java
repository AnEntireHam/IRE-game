package com.ire.combat.statuseffects.generativeeffect;

public class ManaGenerativeManager extends GenerativeManager {

    public ManaGenerativeManager() {
        super();
    }

    @Override
    public void execute() {

    }

    @Override
    public void add(GenerativeEffect ge) {

        if (ge instanceof ManaGenerative) {

            this.generativeEffects.add(ge);
            if (ge.getStrength() > 0) {
                this.regenEffects.add(ge);
                return;
            }
            this.bleedEffects.add(ge);
        }
        throw new IllegalArgumentException("Unexpected Value: " + ge);
    }

    @Override
    public void remove(GenerativeEffect ge) {

        if (ge instanceof ManaGenerative) {

            this.generativeEffects.remove(ge);
            if (ge.getStrength() > 0) {
                this.regenEffects.remove(ge);
                return;
            }
            this.bleedEffects.remove(ge);
        }
        throw new IllegalArgumentException("Unexpected Value: " + ge);
    }
}
