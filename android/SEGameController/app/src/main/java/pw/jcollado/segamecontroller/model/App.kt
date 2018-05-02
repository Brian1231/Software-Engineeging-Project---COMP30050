package pw.jcollado.segamecontroller.model

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_list_properties.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.listPropertiesActivity.ListPropertiesActivity
import pw.jcollado.segamecontroller.utils.Prefs

/**
 * Created by jcolladosp on 07/03/2018.
 */
val preferences: Prefs by lazy {
    App.prefs!!
}

abstract class App : AppCompatActivity(), AsyncResponse {
    var listActivity: ListPropertiesActivity? = null
   // var onList = false

    companion object {
        var prefs: Prefs? = null
        var gamethread: ServerConnectionThread? = null


    }

    override fun onCreate(savedInstanceState: Bundle?) {

        prefs = Prefs(applicationContext)
        super.onCreate(savedInstanceState)
    }


    fun startServer(context: Context) {
        if (gamethread == null) {
            gamethread = ServerConnectionThread(context, preferences.port)

        }
        gamethread?.start()
    }

    fun setMessageToServer(message: String) {
        Log.i("send",message)
        gamethread?.setMessage(message)

    }

    fun killGameThread() {
        gamethread?.kill()
    }
    fun setActivityResponse(activity: ListPropertiesActivity){
        listActivity = activity
    }

    override fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else -> getResponse(response)
        }
    }

    fun getResponse(response: String) {
        Log.i("response", response)
        parseResponsetoPlayer(response)


    }

    fun parseResponsetoPlayer(response: String) {
        val responseJSON = JSONObject(response)
        val charactername = responseJSON.getString("character")

        Player.character = charactername
        Player.balance = responseJSON.getInt("balance")
        Player.fuel = responseJSON.getInt("fuel")
        Player.id = responseJSON.getInt("id")
        Player.colour = responseJSON.getInt("colour")
        Player.position = responseJSON.getString("position")
        Player.action_info.add(responseJSON.getString("action_info"))

        preferences.character = charactername
        if (!Player.properties.isEmpty()) {
            Player.properties.clear()
        }

        val jArray = responseJSON.getJSONArray("properties") as JSONArray
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                RequestFunctions().propertyFromJSONString(jArray.getString(i))?.let {
                    Player.properties.add(it)
                }
            }
        }

            updateGameState()
            if(listActivity!=null){
                listActivity!!.updateProperties()

        }

    }

    open fun updateGameState(){


    }
}