package IRE.Combat.Actions;

import IRE.Entities.Entity;

public abstract class Action {

    protected String name;
    protected String description;

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(Entity attacker, Entity defender);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
