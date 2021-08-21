package HighScore;

import DataClasses.GlobalHighScoreData;
import DataClasses.LocalHighScoreData;
import PlayerData.IPlayerDataManager;
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
    private IPlayerDataManager iPlayerDataManager;

    public GlobalHighscoreController(IPlayerDataManager iPlayerDataManager)
    {
        this.iPlayerDataManager = iPlayerDataManager;
    }

    @Override
    public void submitHighscore(int score)
    {
        Unirest.setTimeouts(0, 0);
        try
        {
            HttpResponse<String> response = Unirest.post(serverUrl+"/submitHighscore")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", token)
                    .field("score", score)
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
            Unirest.setTimeouts(0, 0);
            var responseArray = Unirest.post(serverUrl+"/get").asJson().getBody().getArray();
            var length = responseArray.length();
            List<GlobalHighScoreData> globalHighScoreData = new ArrayList<>();
            for(int i=0; i<length;i++)
            {
                var a= (JSONObject) responseArray.get(i);
                globalHighScoreData.add(new GlobalHighScoreData(a.getString("name"),Integer.parseInt(a.getString("score"))));
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
