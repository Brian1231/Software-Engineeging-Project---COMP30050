# SEProject
## Desktop-to-Server JSON:
"id": int (0 means desktop) <br />  
"player_turn":int <br />
"game_started": boolean <br />


## Server-to-Desktop JSON:
"player_turn":int <br />
"game_started": boolean <br />
"players": JSONArray of JSONObjects for players

### JSONObject player:
"balance": int <br />
"id": int <br />
"position": Point <br />

## Phone-to-Server JSON:
"id": int  (1 means new player)<br />
"action": String <br />

## Server-to-Phone JSON:
"id":int <br />
"action": String <br />
