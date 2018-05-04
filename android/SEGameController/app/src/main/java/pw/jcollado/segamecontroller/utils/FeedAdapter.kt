package pw.jcollado.segamecontroller.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pw.jcollado.segamecontroller.R
import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolderFeed>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFeed {
        return ViewHolderFeed(LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderFeed, position: Int) {
        holder?.feed_item_textView?.text = items[position]
    }


}

class ViewHolderFeed (view: View) : RecyclerView.ViewHolder(view) {
    val feed_item_textView = view.feed_item_text!!
}