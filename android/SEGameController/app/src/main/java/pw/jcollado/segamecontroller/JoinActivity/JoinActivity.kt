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
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.RequestFunctions
import pw.jcollado.segamecontroller.model.preferences
import pw.jcollado.segamecontroller.utils.requestToServer




class JoinActivity : App(), AsyncResponse {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setupUI()

    }

    private fun setupUI(){
        joinButton.onClick { joinServer() }
        idTextView.text = preferences.playerID.toString()

    }

    private fun joinServer(){
        val username  = userNameED.text.toString()
        val joinGameRequest = Request(-1,username)

        idTextView.text = joinGameRequest.toJSONString()

        requestToServer(joinGameRequest.toJSONString())

    }




    private fun getResponseID(response: String){
        Log.i("lol",response)
        val responseRequest = RequestFunctions().fromJSONString(response)
        responseRequest?.id?.let { saveUserID(it) }
        idTextView.text = response
        //startActivity<MainActivity>()

    }
    private fun saveUserID(id: Int){
        preferences.playerID = id
    }

    override fun processFinish(output: String?) {
        handleResponse(output)
    }

    private fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else ->  getResponseID(response)

        }
    }
}
