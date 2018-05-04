package pw.jcollado.segamecontroller.feedActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_feed.*
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.Player
import pw.jcollado.segamecontroller.utils.FeedAdapter
import pw.jcollado.segamecontroller.utils.getPlayerColor
import kotlin.collections.ArrayList

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setActionBar()

        if (Player.action_info.isEmpty()) {
            no_feed_textView.visibility = View.VISIBLE

        } else {

            feedRecyclerView.layoutManager = LinearLayoutManager(this)
            feedRecyclerView.adapter = FeedAdapter(reverse(Player.action_info), this)
            no_feed_textView.visibility = View.GONE

        }
    }

    private fun setActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.game_feed)
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

    fun <T> reverse(list: ArrayList<T>): ArrayList<T> {
        val result = ArrayList(list)
        result.reverse()
        return result
    }
}
