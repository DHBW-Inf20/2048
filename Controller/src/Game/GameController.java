package Game;

import Game.DataClasses.GameModes;
import Game.DataClasses.Tile;
import Game.TileCreator.CooperativeTileCreator;
import Game.TileCreator.ITileCreator;
import Game.TileCreator.MinMaxTileCreator;
import Game.TileCreator.RandomTileCreator;


import java.util.Observable;

public class GameController implements IGameController
{
    private GameModes gameMode = GameModes.random;
    private boolean aiEnabled = false;
    private ITileCreator iTileCreator;
    private final TileChangeListener tileChangeListener;


    public GameController(TileChangeListener tileChangeListener)
    {
        this.tileChangeListener = tileChangeListener;
    }

    @Override
    public void startGame(GameModes mode, boolean aiEnabled, int dimension)
    {
        this.gameMode = mode;
        this.aiEnabled = aiEnabled;

        switch (gameMode)
        {

            case random -> {
                iTileCreator=new RandomTileCreator();
            }
            case cooperative -> {
                iTileCreator= new CooperativeTileCreator();
            }
            case minMax -> {
                iTileCreator = new MinMaxTileCreator();
            }
        }
        var field = iTileCreator.generateField(dimension);
        tileChangeListener.change(field);
    }


}

