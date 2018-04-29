package pw.jcollado.segamecontroller.JoinActivity

import android.os.Bundle
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.*
import pw.jcollado.segamecontroller.utils.closeLoadingDialog
import pw.jcollado.segamecontroller.utils.loadDialog


open class JoinActivity : App(), AsyncResponse {
    open lateinit var gamethread: ServerConnectionThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setupUI()
        savePort(8080)


    }

    private fun setupUI(){
        if(preferences.port != 8080){
            resumeTX.text = "${getString(R.string.character_name_resume)} ${preferences.character}"
            joinButton.text = getString(R.string.resume_game)

        }

            joinButton.onClick { joinServerNewGame() }

    }

    private fun joinServerNewGame(){
        runOnUiThread {
            loadDialog(this, getString(R.string.connecting))
        }

        val joinGameRequest = Request(-1,"connect","0")
        val jsonStringRequest = joinGameRequest.toJSONString()
        try {
            gamethread = ServerConnectionThread(this, preferences.port)
            gamethread.start()
            gamethread.setMessage(jsonStringRequest)
        }catch (e: Exception){
            Log.e("error",e.getStackTraceString())
        }

    }




    private fun getResponseID(response: String){

        Log.i("lol",response)
        if(response.contains("port")){
            val responseRequest = RequestFunctions().portFromJSONString(response)
            responseRequest?.id?.let { saveUserID(it) }
            responseRequest?.port?.let { savePort(it) }
            runOnUiThread {
                closeLoadingDialog()
            }
            gamethread.kill()
            startActivity<MainActivity>()


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
