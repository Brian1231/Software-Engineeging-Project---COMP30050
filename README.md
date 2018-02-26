# SEProject
## Desktop-to-Server JSON:
"id": int (0 means desktop) <br />
"game_started": boolean <br />
"action": String ("start"-> starts game)


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
"action": String <br /> ("roll"-> rolls dice)
"args": String

## Server-to-Phone JSON:
"id":int <br />
"action": String <br />
