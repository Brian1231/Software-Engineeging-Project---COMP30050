package pw.jcollado.segamecontroller.auctionActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_auction.*
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.App
import pw.jcollado.segamecontroller.model.Player
import pw.jcollado.segamecontroller.model.Request
import pw.jcollado.segamecontroller.model.preferences

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
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.auction)

    }
}

