package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi


/**
 * Created by jcolladosp on 13/02/2018.
 */

data class Property(val id: String,val location: String,val price: Int,val color: Int,var is_mortgaged: Boolean,
                    var owner: Int,var houses: Int, var hasTrap: Boolean) {


    fun toJSONString(): String {

        return RequestFunctions().jsonAdapterProperty.toJson(this)

    }


}