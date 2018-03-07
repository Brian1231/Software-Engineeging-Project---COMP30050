package pw.jcollado.segamecontroller.JoinActivity

import android.os.Bundle
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Player
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences


class JoinActivity : App(),AsyncResponse {
    override fun processFinish(output: String?) {
        handleResponse(output)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        joinButton.onClick { joinServer() }
        idTextView.text = preferences.playerID
    }

    fun joinServer(){
        val joinGameRequest = Request("-1","Hi")
        val result = Klaxon().toJsonString(joinGameRequest)
        idTextView.text = result
        //saveUserID("Player 2")
        //startActivity<MainActivity>()
       //requestToServer("{ \"id\": -1 }")

    }

    fun saveUserID(id: String){
        preferences.playerID = id
    }

    private fun handleResponse(response: String?) {

        when (response) {
            null -> idTextView.text = "null"
            "" -> idTextView.text = "Blank response!"
            else -> saveUserID(response)
        }
    }
}
