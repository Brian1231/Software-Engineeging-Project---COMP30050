package pw.jcollado.segamecontroller.joinActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join.*
import pw.jcollado.segamecontroller.R

import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ClientThread
import pw.jcollado.segamecontroller.model.Connections


class joinActivity : AppCompatActivity(),AsyncResponse {
    override fun processFinish(output: String?) {
        handleResponse(output)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        joinButton.onClick { joinServer() }
    }

    fun joinServer(){

        val thread = ClientThread(this@joinActivity)

        thread.execute("{ \"id\": -1 }", Connections.IP.value, Connections.PORT.value)
    }

    private fun handleResponse(s: String?) {

        when (s) {
            null -> idTextView.text = "null"
            "" -> idTextView.text = "Blank response!"
            else -> idTextView.text = s
        }
    }
}
