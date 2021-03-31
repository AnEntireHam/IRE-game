package com.ire.combat.statuseffects.stateffects;

public class MagicUp extends StatEffect {

    public MagicUp(int effectLevel) {
        super("Magic Up", "MAG", "Increases the magic of the afflicted target.",
                1, 5, STANDARD_BUFF_CONDITIONS,
                effectLevel, 1, 0, 0.20f, 0.05f);
    }
}
