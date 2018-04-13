# SEProject
## Desktop-to-Server JSON on port 8000:
"id": int (0 for desktop) <br />
"game_started": boolean <br />
"action": String ("start"-> starts game) <br />

## Server-to-Desktop JSON:
"player_turn":int <br />
"game_started": boolean <br />
"players": JSONArray of JSONObjects for players <br />
"locations": JSONArray of JSONObjects for locations <br />
"action_info": String <br />

### JSONObject player:
"id": int <br />
"balance": int <br />
"position": int (0 to 39) <br />
"character": String <br />
"fuel": int (fuel amount 0-3) <br />

### JSONObject location:
"id": String <br />
"price": int <br />
"location": int (0 to 39) <br />
"owner": int (player id) <br />
"color": String <br />
"is_mortgaged": boolean <br />

## Phone-to-Server 
### Initial Phone-to-Server JSON on port 8080:
"id": int (-1 means new player)<br />

### Phone-to-Server JSON:
"id": int <br />
"action": String<br />
("roll"-> rolls dice)<br />
("done"-> increments player turn) <br />
("buy"-> attempt to buy tile at player position) <br />
("sell"-> attempt to sell tile at position args[0]) <br />
("boost"-> use vehicle to move ahead one tile )<br />
("mortgage"-> attempt to mortgage tile at position args[0])<br />
("redeem"-> attempt to un-mortgage tile at position args[0])<br />
("build"-> attempt to build on tile at position args[0] with number to build args[1])<br />
("demolish"-> attempt to demolish on tile at position args[0] with number to demolish args[1])<br />
("pay"-> attempt to pay debt to owed player) <br />
"args": Strings separated with "," <br />

## Server-to-Phone:
## Initial Response from port 8080 JSON:
"id": int (Player id)<br />
"port": int (Port listening for player connection) <br />

### Server-to-Phone JSON:
"id": int <br />
"balance": int <br />
"position": int (0 to 39) <br />
"properties": JSONArray of JSONObjects for owned properties <br />
"character": String <br />
"fuel": int (fuel amount 0-3) <br />
