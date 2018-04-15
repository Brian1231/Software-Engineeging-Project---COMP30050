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
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.Property

/**
 * Created by jcolladosp on 13/02/2018.
 */

class CardsAdapter(val items: List<Property>,val listener: (Property) -> Unit) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.property_card))

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        
        holder.initData(items[position])
        holder.bindMortgage(items[position],this)
        holder.bindSell(items[position],listener)

    }

    override fun getItemCount() = items.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initData(item: Property) = with(itemView) {
            title.text = item.id
            Picasso.get().load("http://52.48.249.220/worlds/${item.id}.jpg").placeholder(R.drawable.placeholder).into(thumbnail)
            val color = resources.getIdentifier(item.color, "color", context.packageName)

            linearLY.setBackgroundColor(ContextCompat.getColor(context,color))
            houses.text = "0 houses"
                    //if (item.hotel) "1 Hotel" else "${item.houses} houses"
            priceTx.text = "${item.price} $"

        }
        fun bindSell(item: Property,listenerSell: (Property) -> Unit) = with(itemView){
            sellButton.onClick { listenerSell(item) }
        }
        fun bindMortgage(item: Property, adapter: CardsAdapter) = with(itemView) {
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
                adapter.notifyDataSetChanged()
            }

        }


    }
}

