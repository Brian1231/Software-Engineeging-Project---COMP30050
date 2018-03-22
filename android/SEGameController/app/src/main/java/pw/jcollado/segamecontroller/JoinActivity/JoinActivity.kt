package pw.jcollado.segamecontroller.JoinActivity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences


class JoinActivity : App(), AsyncResponse {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setupUI()

    }

    private fun setupUI(){
        joinButton.onClick { joinServer() }
        idTextView.text = preferences.playerID

    }

    private fun joinServer(){
        val joinGameRequest = Request("-1","Hi")

        idTextView.text = joinGameRequest.toJSONString()
        startActivity<MainActivity>()

    }

    private fun saveUserID(id: String){
        preferences.playerID = id
    }

    override fun processFinish(output: String?) {
        handleResponse(output)
    }

    private fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else -> saveUserID(response)
        }
    }
}
