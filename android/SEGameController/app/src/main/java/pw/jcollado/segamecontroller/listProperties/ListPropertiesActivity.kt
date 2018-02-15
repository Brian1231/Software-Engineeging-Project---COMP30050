package pw.jcollado.segamecontroller.listProperties

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_properties.*
import org.jetbrains.anko.toast
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.Property
import pw.jcollado.segamecontroller.utils.CardsAdapter

class ListPropertiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_properties)
        initAdapter()
        setActionBar()

    }

    fun initAdapter() {
        val property = Property(0, "Marvin Gardens", 2, false, false)

        val items = arrayListOf(property, property, property, property)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CardsAdapter(items, { buildButton(it) }, { mortgageButton(it)})

    }
    fun buildButton(item: Property){
        item.build()
        recyclerView.adapter.notifyDataSetChanged()

        toast("Build Clicked")

    }
    fun mortgageButton(item: Property){
        item.mortgage()
        recyclerView.adapter.notifyDataSetChanged()

        toast("Mortgage Clicked")

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
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
