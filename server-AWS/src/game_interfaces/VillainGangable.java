package game_interfaces;

import game_models.Player;
import org.json.JSONException;
import org.json.JSONObject;

public interface VillainGangable {
    boolean isActive();

    int position();

    int turnsToLive();

    void activate(int location);

    void deactivate();

    void update();

    String attackPlayer(Player player);

    JSONObject getInfo() throws JSONException;
}
