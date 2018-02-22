package pw.jcollado.segamecontroller.utils

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.property_card.*
import kotlinx.android.synthetic.main.property_card.view.*
import org.jetbrains.anko.backgroundColor
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.R.id.mortgageText
import pw.jcollado.segamecontroller.R.id.thumbnail
import pw.jcollado.segamecontroller.model.Property

/**
 * Created by jcolladosp on 13/02/2018.
 */

class CardsAdapter(val items: List<Property>, val listener: (Property) -> Unit) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.property_card))

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bindBuild(items[position], listener)
        holder.bindMortgage(items[position])

    }

    override fun getItemCount() = items.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindBuild(item: Property, listenerBuild: (Property) -> Unit) = with(itemView) {
            title.text = item.title
            houses.text = if (item.hotel) "1 Hotel" else "${item.houses} houses"
            buildButton.setOnClickListener { listenerBuild(item) }

        }
        fun bindMortgage(item: Property) = with(itemView) {
            mortgageButton.setOnClickListener {
                if (item.isMortgaged) {
                    thumbnail.alpha = 1F
                    thumbnail.backgroundColor = ContextCompat.getColor(context, R.color.white)
                    mortgageText.visibility = View.INVISIBLE
                } else {
                    thumbnail.alpha = 0.3F
                    thumbnail.backgroundColor = ContextCompat.getColor(context, R.color.grey)
                    mortgageText.visibility = View.VISIBLE
                    mortgageText.text = "MORTGAGED FOR ${item.mortgageValue} $"
                }
                item.mortgage()
            }

        }

    }
}

