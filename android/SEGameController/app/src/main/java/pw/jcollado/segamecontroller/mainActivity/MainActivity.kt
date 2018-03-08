package pw.jcollado.segamecontroller.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.listPropertiesActivity.ListPropertiesActivity
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences
import pw.jcollado.segamecontroller.utils.requestToServer


class MainActivity : App(), AsyncResponse {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar()


    }


    fun onRoll(){
        requestToServer(Request("roll","").toJSONString())
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

    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.title = preferences.playerID

        }


    }
}
