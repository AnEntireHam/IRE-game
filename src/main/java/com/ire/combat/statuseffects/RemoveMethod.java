package com.ire.combat.statuseffects;

import com.ire.entities.Entity;

public interface RemoveMethod {

    boolean checkRemove(RemoveCondition condition, Entity e);
    boolean checkTakeDamage(RemoveCondition condition, Entity target, int damage, int maxHlh);
}
