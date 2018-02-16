package pw.jcollado.segamecontroller.mainActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import pw.jcollado.segamecontroller.model.Connections
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.*
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.listProperties.ListPropertiesActivity


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.propertiesMenu -> {
                startActivity(Intent(this,ListPropertiesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleResponse(s: String?) {

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
