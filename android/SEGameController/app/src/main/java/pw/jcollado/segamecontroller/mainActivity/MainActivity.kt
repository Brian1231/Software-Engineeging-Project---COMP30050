package pw.jcollado.segamecontroller.mainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.listPropertiesActivity.ListPropertiesActivity
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences
import pw.jcollado.segamecontroller.utils.requestToServer


class MainActivity : App(), AsyncResponse {
    lateinit var gamethread: ServerConnectionThread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar()
        rollButton.onClick { onRoll() }
        connectToNewPort()
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
        Log.i("lol",response)
    }

    override fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else -> getResponse(response)
        }
    }



    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.title = preferences.username

        }


    }
}
