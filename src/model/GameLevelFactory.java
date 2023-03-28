package model;

public class GameLevelFactory {

    public IGameLevel createGameLevel(GameLevel gameLevel) {
        IGameLevel iGameLevel;

        switch (gameLevel) {
            case EASY:
                iGameLevel = new EasyLevel();
                break;
            case MEDIUM:
                iGameLevel = new MediumLevel();
                break;
            case HARD:
                iGameLevel = new HardLevel();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gameLevel);
        }
        return iGameLevel;
    }
}
