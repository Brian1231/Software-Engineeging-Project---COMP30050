package pw.jcollado.segamecontroller.utils
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.property_card.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.model.Property

/**
 * Created by jcolladosp on 13/02/2018.
 */

class CardsAdapter(val items: List<Property>,val sellFunction: (Property) -> Unit,val mortgageFunction: (Property) -> Unit
                   ,val buildFunction: (Property) -> Unit,val demolishFunction: (Property) -> Unit,val trapFunction: (Property) -> Unit)
    : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.property_card))

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        
        holder.initData(items[position])
        holder.bindMortgage(items[position],mortgageFunction)
        holder.bindSell(items[position],sellFunction)
        holder.bindBuild(items[position],buildFunction)
        holder.bindDemolish(items[position],demolishFunction)
        holder.bindTrap(items[position],trapFunction)



    }

    override fun getItemCount() = items.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initData(item: Property) = with(itemView) {
            title.text = item.id
            priceTx.text = "${item.price} $"
            Picasso.get().load("http://52.48.249.220/worlds/${item.id}.jpg").placeholder(R.drawable.placeholder).into(thumbnail)
            val intColor = item.color
            val hexColor = "#" + Integer.toHexString(intColor).substring(2)
            val color = Color.parseColor(hexColor)
            linearLY.setBackgroundColor(color)

            if (item.hasTrap){trapTX.text = resources.getString(R.string.hasTrap)}
            else{trapTX.text = resources.getString(R.string.noTrap)}
            if (item.houses > 4){
                houses.text = "1 hotel"
            }
            else{
                 houses.text = "${item.houses} houses"
             }

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
                mortgageButton.text = "REDEEM"

            }

        }
        fun bindSell(item: Property,sellFunction: (Property) -> Unit) = with(itemView){
            sellButton.onClick { sellFunction(item) }
        }
        fun bindMortgage(item: Property, mortgageFunction: (Property) -> Unit) = with(itemView) {

            mortgageButton.onClick { mortgageFunction(item) }

        }
        fun bindDemolish(item: Property, demolishFunction: (Property) -> Unit) = with(itemView) {

            demolishButton.onClick { demolishFunction(item) }

        }

        fun bindBuild(item: Property, buildFunction: (Property) -> Unit) = with(itemView) {

            buildButton.onClick { buildFunction(item) }

        }
        fun bindTrap(item: Property, trapFunction: (Property) -> Unit) = with(itemView) {

            trapButton.onClick { trapFunction(item) }

        }



    }
}

