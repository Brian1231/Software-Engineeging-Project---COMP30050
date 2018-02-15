package pw.jcollado.segamecontroller.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.property_card.view.*
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.Property

/**
 * Created by jcolladosp on 13/02/2018.
 */

class CardsAdapter(val items: List<Property>, val listener: (Property) -> Unit,val listener2: (Property) -> Unit) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.property_card))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener,listener2)

    override fun getItemCount() = items.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Property, listenerBuild: (Property) -> Unit,listenerMortgage: (Property) -> Unit) = with(itemView) {
            title.text = item.title
            houses.text = if (item.hotel) "1 Hotel" else "${item.houses} houses"
            buildButton.setOnClickListener { listenerBuild(item) }
            mortgageButton.setOnClickListener{ listenerMortgage(item) }
        }
    }
}

