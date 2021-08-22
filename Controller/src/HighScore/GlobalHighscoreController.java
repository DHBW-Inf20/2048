package HighScore;

import DataClasses.GameOptions;
import DataClasses.GlobalHighScoreData;
import PlayerData.IPlayerDataController;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GlobalHighscoreController implements IGlobalHighscoreController
{

    private final String serverUrl  = "http://88.218.227.159:3001";
    private final String token = "a$dfjveÖS3ir_§2";
    private final IPlayerDataController iPlayerDataManager;

    public GlobalHighscoreController(IPlayerDataController iPlayerDataManager)
    {
        this.iPlayerDataManager = iPlayerDataManager;
    }

    @Override
    public void submitHighscore(int score, GameOptions gameOptions)
    {
        Unirest.setTimeouts(1000, 0);
        try
        {
            HttpResponse<String> response = Unirest.post(serverUrl+"/submitHighscore")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", token)
                    .field("score", score)
                    .field("dimensions",gameOptions.getFieldDimensions())
                    .field("name", iPlayerDataManager.getPlayerName())
                    .asString();
        } catch (UnirestException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<GlobalHighScoreData> getGlobalHighscores()
    {
        try
        {
            Unirest.setTimeouts(1000, 0);
            var responseArray = Unirest.post(serverUrl+"/get").asJson().getBody().getArray();
            var length = responseArray.length();
            List<GlobalHighScoreData> globalHighScoreData = new ArrayList<>();
            for(int i=0; i<length;i++)
            {
                var a= (JSONObject) responseArray.get(i);
                globalHighScoreData.add(new GlobalHighScoreData(a.getString("name"),Integer.parseInt(a.getString("score")), Integer.parseInt(a.getString("dimensions"))));
            }
            return globalHighScoreData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
