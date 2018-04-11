package pw.jcollado.segamecontroller.JoinActivity

import android.os.Bundle
import android.util.Log
import android.util.Log.i

import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.*


class JoinActivity : App(), AsyncResponse {
    lateinit var gamethread: ServerConnectionThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setupUI()
        savePort(8080)

    }

    private fun setupUI(){
        joinButton.onClick { joinServer() }
        idTextView.text = preferences.playerID.toString()

    }

    private fun joinServer(){
        val username  = userNameED.text.toString()
        val joinGameRequest = Request(-1,"connect","0")
        val jsonStringRequest = joinGameRequest.toJSONString()
        idTextView.text = jsonStringRequest
        preferences.username = username

        gamethread = ServerConnectionThread(this, preferences.port)
        gamethread.start()
        gamethread.setMessage(jsonStringRequest)

       // requestToServer(jsonStringRequest)

    }




    private fun getResponseID(response: String){
        Log.i("lol",response)
        if(response.contains("port")){
            val responseRequest = RequestFunctions().portFromJSONString(response)
            responseRequest?.id?.let { saveUserID(it) }
            responseRequest?.port?.let { savePort(it) }
            startActivity<MainActivity>()
            gamethread.kill()


        }

    }


    private fun saveUserID(id: Int){
        preferences.playerID = id
    }
    private fun savePort(port: Int){
        preferences.port = port
    }


    override fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else ->  getResponseID(response)

        }
    }
}
