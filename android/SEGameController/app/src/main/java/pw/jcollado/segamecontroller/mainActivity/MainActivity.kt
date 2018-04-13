package pw.jcollado.segamecontroller.mainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONObject
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.listPropertiesActivity.ListPropertiesActivity
import org.json.JSONArray
import pw.jcollado.segamecontroller.model.*


class MainActivity : App(), AsyncResponse {
    lateinit var gamethread: ServerConnectionThread




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar()
        rollButton.onClick { onRoll() }
        buyButton.onClick { onBuy() }
        finishTurnButton.onClick { onFinish() }
        connectToNewPort()
    }

    private fun onFinish() {
        gamethread.setMessage(Request(preferences.playerID, "done","0").toJSONString())
    }

    fun connectToNewPort(){
        val joinGameRequest = Request(preferences.playerID,"connect","0")
        val jsonStringRequest = joinGameRequest.toJSONString()
        gamethread = ServerConnectionThread(this, preferences.port)
        gamethread.start()
        gamethread.setMessage(jsonStringRequest)

    }

    fun onRoll() {
        gamethread.setMessage(Request(preferences.playerID, "roll","0").toJSONString())
    }
    fun onBuy() {
        gamethread.setMessage(Request(preferences.playerID, "buy","0").toJSONString())
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.propertiesMenu -> {
                startActivity(Intent(this, ListPropertiesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun getResponse(response: String){
        Log.i("response",response)
        parseResponsetoPlayer(response)


    }
    fun parseResponsetoPlayer(response: String){
        val responseJSON = JSONObject(response)

        Player.character = responseJSON.getString("character")
        Player.balance = responseJSON.getInt("balance")
        Player.fuel = responseJSON.getInt("fuel")
        Player.id = responseJSON.getInt("id")
        Player.position = responseJSON.getInt("position")

        if (!Player.properties.isEmpty()) {
            Player.properties.clear()
        }

        val jArray = responseJSON.getJSONArray("properties") as JSONArray
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                RequestFunctions().propertyFromJSONString(jArray.getString(i))?.let { Player.properties.add(it) }
            }
        }

        updateGameState()

    }
    fun updateGameState(){
        supportActionBar?.title = Player.character
        balanceTX.text = "${Player.balance} $"
        positionTX.text = Player.position.toString()
    }

    override fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else -> getResponse(response)
        }
    }



    private fun setActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }
}
