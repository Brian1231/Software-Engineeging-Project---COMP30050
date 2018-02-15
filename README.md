# SEProject
## Desktop-to-Server JSON:
"id": int (0 means desktop) <br />
"player_turn":int <br />
"game_started": boolean <br />
"action": String (Used to start game if value is "Start")


## Server-to-Desktop JSON:
"player_turn":int <br />
"game_started": boolean <br />
"players": JSONArray of JSONObjects for players <br />
"action_info": String

### JSONObject player:
"balance": int <br />
"id": int <br />
"position": int (0 to 39) <br />

## Phone-to-Server JSON:
"id": int  (1 means new player)<br />
"action": String <br />
"args": String

## Server-to-Phone JSON:
"id":int <br />
"action": String <br />
