package pw.jcollado.segamecontroller.listProperties

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_list_properties.*
import kotlinx.android.synthetic.main.property_card.*
import org.jetbrains.anko.backgroundColor
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

    private fun initAdapter() {
        val property = Property(0,"Marvin Gardens",150)
        val property2 = Property(1,"Dublin",150)
        val property3 = Property(2,"Cork",150)

        val items = arrayListOf(property, property2, property3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CardsAdapter(items, { buildButton(it) }, { mortgageButton(it)})

    }
    fun buildButton(item: Property){
        item.build()
        recyclerView.adapter.notifyDataSetChanged()

    }
    fun mortgageButton(item: Property){
        if (item.isMortgaged){
            unmortgageProperyView(item)
        }else{
            mortgageProperyView(item)

        }
        item.mortgage()

        recyclerView.adapter.notifyDataSetChanged()

        //toast("Mortgage Clicked")

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

    private fun mortgageProperyView(item: Property){
        thumbnail.alpha = 0.3F
        thumbnail.backgroundColor = ContextCompat.getColor(this,R.color.grey)
        mortgageText.visibility = View.VISIBLE
        mortgageText.text = "MORTGAGED FOR ${item.mortgageValue} $"
    }
    private fun unmortgageProperyView(item: Property){
        thumbnail.alpha = 1F
        thumbnail.backgroundColor = ContextCompat.getColor(this,R.color.white)

        mortgageText.visibility = View.INVISIBLE

    }

}
