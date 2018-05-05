package pw.jcollado.segamecontroller.mainActivity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.squareup.picasso.Picasso
import com.warkiz.widget.IndicatorSeekBar
import kotlinx.android.synthetic.main.activity_auction.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.feedActivity.FeedActivity
import pw.jcollado.segamecontroller.listPropertiesActivity.ListPropertiesActivity
import pw.jcollado.segamecontroller.joinActivity.JoinActivity
import pw.jcollado.segamecontroller.model.*
import pw.jcollado.segamecontroller.utils.getPlayerColor
import pw.jcollado.segamecontroller.utils.getPropertyImageURL


class MainActivity : App() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar()
        rollButton.onClick { onRoll() }
        buyButton.onClick { onBuy() }
        finishTurnButton.onClick { onFinish() }
        payButton.onClick { onPay() }
        boostButton.onClick { onBoost() }
        bankruptButton.onClick { onBankrupt() }


        connectToNewPort()
    }

    private fun onFinish() {
        setMessageToServer(Request(preferences.playerID, "done","0").toJSONString())
    }


    fun connectToNewPort(){
        val joinGameRequest = Request(preferences.playerID,"connect","0")
        val jsonStringRequest = joinGameRequest.toJSONString()
        startServer(this)
        setMessageToServer(jsonStringRequest)

    }

    fun onRoll() {
        setMessageToServer(Request(preferences.playerID, "roll","0").toJSONString())
    }
    fun onPay() {
        setMessageToServer(Request(preferences.playerID, "pay","0").toJSONString())
    }
    fun onBuy() {
        setMessageToServer(Request(preferences.playerID, "buy","0").toJSONString())
    }
    fun onBoost(){
        setMessageToServer(Request(preferences.playerID, "boost","0").toJSONString())

    }
    fun onBankrupt(){
        alert("Are you sure that you want to leave the game?","Bankrupt" ) {
            yesButton {
                setMessageToServer(Request(preferences.playerID, "bankrupt","0").toJSONString())
                killGameThread()
                preferences.port = 8080
                startActivity(Intent(applicationContext, JoinActivity::class.java))

            }
            noButton {}
        }.show()


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
                startActivity(Intent(this, ListPropertiesActivity::class.java))
                true
            }
            R.id.feedMenu -> {
                startActivity(Intent(this, FeedActivity::class.java))
                true
            }
            R.id.auctionMenu -> {
                //startActivity(Intent(this, AuctionActivity::class.java))
                onBidButton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun updateGameState(){
        runOnUiThread {

            supportActionBar?.title = Player.character
            supportActionBar?.setBackgroundDrawable(getPlayerColor())
            balanceTX.text = Player.balance.toString()
            positionTX.text = Player.position

            Picasso.get().load(getPropertyImageURL(Player.position)).placeholder(R.drawable.joinlogo).into(positionImage)

        }
    }

    fun onBidButton(){
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.activity_auction,null)
        dialog.setView(dialogView)
        dialog.setCancelable(true)
        val seekbar = dialogView.findViewById<IndicatorSeekBar>(R.id.priceSeekBar)
        val buttonbid = dialogView.findViewById<Button>(R.id.biddButton)

        val customDialog = dialog.create()
        customDialog.show()
        setSeekBar(seekbar,buttonbid)
    }

    fun setSeekBar(seekBar: IndicatorSeekBar, bidButton2: Button){
        seekBar.max = Player.balance.toFloat()
        bidButton2.text = "BID ${seekBar.progress} SHM"

        seekBar.setOnSeekChangeListener(object : IndicatorSeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: IndicatorSeekBar, progress: Int, progressFloat: Float, fromUserTouch: Boolean) {
                bidButton2.text = "BID $progress SHM"

            }

            override fun onSectionChanged(seekBar: IndicatorSeekBar, thumbPosOnTick: Int, textBelowTick: String, fromUserTouch: Boolean) {
                //only callback on discrete series SeekBar type.
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar, thumbPosOnTick: Int) {}

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {}
        })

        bidButton2.onClick {
            setMessageToServer(Request(preferences.playerID, "bid",seekBar.progress.toString()).toJSONString())
            toast("${getString(R.string.you_bid)} ${seekBar.progress} SHM")
        }
    }



    private fun setActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }
}

