package pw.jcollado.segamecontroller.auctionActivity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_auction.*
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Player
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences
import pw.jcollado.segamecontroller.utils.getPlayerColor

class AuctionActivity : App() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auction)
        setSeekBar()
        biddButton.onClick {
            setMessageToServer(Request(preferences.playerID, "bid",priceSeekBar.progress.toString()).toJSONString())

        }
        setActionBar()

    }
    fun setSeekBar(){
        priceSeekBar.max = Player.balance
        priceSeekBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {
              biddButton.text = "BID ${priceSeekBar.progress} SHM"
            }

            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {}

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {}
        })
    }

    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.auction)
            actionBar.setBackgroundDrawable(getPlayerColor())


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

