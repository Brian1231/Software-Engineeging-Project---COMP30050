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


class MainActivity : App() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar()
        rollButton.onClick { onRoll() }
        buyButton.onClick { onBuy() }
        finishTurnButton.onClick { onFinish() }
        payButton.onClick { onPay() }
        boostButton.onClick { onBoost() }



        connectToNewPort()
    }

    private fun onFinish() {
        setMessageToServer(Request(preferences.playerID, "done","0").toJSONString())
    }

    fun connectToNewPort(){
        val joinGameRequest = Request(preferences.playerID,"connect","0")
        val jsonStringRequest = joinGameRequest.toJSONString()
        startServer(this)
        setMessageToServer(jsonStringRequest)

    }

    fun onRoll() {
        setMessageToServer(Request(preferences.playerID, "roll","0").toJSONString())
    }
    fun onPay() {
        setMessageToServer(Request(preferences.playerID, "pay","0").toJSONString())
    }
    fun onBuy() {
        setMessageToServer(Request(preferences.playerID, "buy","0").toJSONString())
    }
    fun onBoost(){
        setMessageToServer(Request(preferences.playerID, "boost","0").toJSONString())

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



    override fun updateGameState(){
        runOnUiThread {

            supportActionBar?.title = Player.character
            balanceTX.text = "${Player.balance} ${'$'}"
            positionTX.text = Player.position.toString()
        }
    }





    private fun setActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }
}

