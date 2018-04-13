package pw.jcollado.segamecontroller.utils

import android.content.res.Resources
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.property_card.view.*
import org.jetbrains.anko.backgroundColor
import pw.jcollado.segamecontroller.R
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
            title.text = item.id
            Picasso.get().load("http://52.48.249.220/worlds/${item.id}.jpg").into(thumbnail)
            val color = resources.getIdentifier(item.color, "color", context.packageName)
            Log.i("color",color.toString())

            linearLY.setBackgroundColor(ContextCompat.getColor(context,color))
            houses.text = "0 houses"
                    //if (item.hotel) "1 Hotel" else "${item.houses} houses"
            priceTx.text = "${item.price} $"
            buildButton.setOnClickListener { listenerBuild(item) }

        }
        fun bindMortgage(item: Property) = with(itemView) {
            mortgageButton.setOnClickListener {
                if (!item.is_mortgaged) {
                    infoRL.alpha = 1F
                    infoRL.backgroundColor = ContextCompat.getColor(context, R.color.white)
                    mortgageText.visibility = View.INVISIBLE
                    mortgageButton.text = "MORTGAGE"
                } else {
                    infoRL.alpha = 0.3F
                    infoRL.backgroundColor = ContextCompat.getColor(context, R.color.grey)
                    mortgageText.visibility = View.VISIBLE
                    mortgageText.text = "MORTGAGED FOR ${item.price} $"
                    mortgageButton.text = "UNMORTGAGE"

                }
                item.mortgage()
            }

        }

    }
}

