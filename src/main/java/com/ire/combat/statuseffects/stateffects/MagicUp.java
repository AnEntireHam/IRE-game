package com.ire.combat.statuseffects.stateffects;

public class MagicUp extends StatEffect {

    public MagicUp(int effectLevel) {
        super("Magic Up", "MAG", "Increases the magic of the afflicted target.",
                true, true, 1, 5, effectLevel, 1, 0,
                0.20f, 0.05f);
    }
}
