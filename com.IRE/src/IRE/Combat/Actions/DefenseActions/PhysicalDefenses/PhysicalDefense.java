package IRE.Combat.Actions.DefenseActions.PhysicalDefenses;

import IRE.Combat.Actions.DefenseActions.DefenseAction;

public abstract class PhysicalDefense extends DefenseAction {

    protected float physBoost;
    protected float physResist;

    public PhysicalDefense(String NAME, String DESCRIPTION, float physBoost, float physResist) {
        super(NAME, DESCRIPTION);

        this.physBoost = physBoost;
        this.physResist = physResist;
    }

    public float getPhysBoost() {
        return physBoost;
    }

    public void setPhysBoost(float physBoost) {
        this.physBoost = physBoost;
    }

    public float getPhysResist() {
        return physResist;
    }

    public void setPhysResist(float physResist) {
        this.physResist = physResist;
    }
}
