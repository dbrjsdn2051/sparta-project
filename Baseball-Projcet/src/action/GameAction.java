package action;

import java.util.HashMap;

public enum GameAction {
    LEVEL(0), START(1), LIST(2), EXIT(3);

    private final int actionNumber;
    private static final HashMap<Integer, GameAction> gameActionHashMap = new HashMap<>();

    GameAction(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    static {
        for (GameAction value : values()) {
            gameActionHashMap.put(value.actionNumber, value);
        }
    }

    public static GameAction valueOf(int gameSet) {
        return gameActionHashMap.get(gameSet);
    }
}
