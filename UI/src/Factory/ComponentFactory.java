package Factory;

import Game.GameController;
import Game.IGameController;
import HighScore.HighScoreController;
import HighScore.IHighScoreController;
import Menu.IMenuController;
import Menu.MenuController;

/*
Hier werden alle Abhängigkeiten zu Implementierungen aus dem Controller-Module durch das Factory-Pattern und Interfaces entkoppelt
-> Mapping von Interface und Implementierung
 */
public class ComponentFactory
{
    public static IGameController getGameController()
    {
        return new GameController();
    }
    public static IHighScoreController getHighScroeController()
    {
        return new HighScoreController();
    }
    public static IMenuController getMenuController()
    {
        return new MenuController();
    }
}
