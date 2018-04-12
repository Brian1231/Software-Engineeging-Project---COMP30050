package pw.jcollado.segamecontroller.listPropertiesActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_list_properties.*
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.Player
import pw.jcollado.segamecontroller.model.Property
import pw.jcollado.segamecontroller.utils.CardsAdapter

class ListPropertiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_properties)
        initAdapter()
        setActionBar()

    }

    private fun initAdapter() {
        if(Player.properties.isEmpty()){
            no_propertiesTX.visibility = View.VISIBLE

        }
        else {

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CardsAdapter(Player.properties, { buildButton(it) })
            no_propertiesTX.visibility = View.GONE

        }
    }
    fun buildButton(item: Property){
       // item.build()
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
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}
