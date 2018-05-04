package pw.jcollado.segamecontroller.listPropertiesActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_list_properties.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.model.*
import pw.jcollado.segamecontroller.utils.CardsAdapter

class ListPropertiesActivity : App() {
    var properties: ArrayList<Property> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_properties)
        initAdapter()
        setActionBar()
        App.listActivity = this
        App.isList = true
    }

    private fun initAdapter() {
        if (Player.properties.isEmpty()) {
            no_propertiesTX.visibility = View.VISIBLE

        } else {

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CardsAdapter(Player.properties, { sellButton(it) }, { mortgageButton(it) }, { buildButton(it) }, { demolishButton(it) }, { trapButton(it) })
            no_propertiesTX.visibility = View.GONE
            updateProperties()

        }
    }

    fun sellButton(item: Property) {
        setMessageToServer(Request(preferences.playerID, "sell", item.location).toJSONString())
    }

    fun mortgageButton(item: Property) {
        if (item.is_mortgaged) {
            setMessageToServer(Request(preferences.playerID, "redeem", item.location).toJSONString())

        } else {
            setMessageToServer(Request(preferences.playerID, "mortgage", item.location).toJSONString())
        }


    }

    fun demolishButton(item: Property) {

        setMessageToServer(Request(preferences.playerID, "demolish", item.location + ",1").toJSONString())

    }

    fun buildButton(item: Property) {

        setMessageToServer(Request(preferences.playerID, "build", item.location + ",1").toJSONString())
    }

    fun trapButton(item: Property) {

        setMessageToServer(Request(preferences.playerID, "trap", item.location).toJSONString())

    }


    open fun updateProperties() {
        recyclerView.adapter.notifyDataSetChanged()
    }


    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.properties)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                App.isList = false
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
