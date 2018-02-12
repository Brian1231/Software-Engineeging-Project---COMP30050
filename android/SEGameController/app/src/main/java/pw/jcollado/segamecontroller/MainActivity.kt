package pw.jcollado.segamecontroller

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.model.Connections
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity(), AsyncResponse {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        setActionBar()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun handleResponse(s: String?) {

        when (s) {
            null -> balanceTitle.text = "null"
            "" -> balanceTitle.text = "Blank response!"
            else -> balanceTitle.text = s
        }
    }

    override fun processFinish(output: String?) {
        handleResponse(output)
    }

    private fun initUI(){



    }
    fun requestToServer(request: String){
        ClientThread(this@MainActivity).execute(request, Connections.IP.value, Connections.PORT.value)
    }

    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.title = getString(R.string.main_screen)

        }


    }
}
