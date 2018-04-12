package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi


/**
 * Created by jcolladosp on 13/02/2018.
 */

data class Property(val id: String,val location: String,val price: Int,val color: String,var is_mortgaged: Boolean,
                    var owner: Int) {



    fun toJSONString(): String {

        return RequestFunctions().jsonAdapterProperty.toJson(this)

    }



    /*
    fun build() {
        if (canBuild) {
            when (houses) {
                in 0..3 -> houses++
                else -> {
                    hotel = true
                    canBuild = false
                }
            }
        }
    }
    */


    fun mortgage() {
        if (is_mortgaged) {
            is_mortgaged = false
            //code to unmortgage
        } else {
            is_mortgaged = true
            //code to isMortgaged
        }
    }

}