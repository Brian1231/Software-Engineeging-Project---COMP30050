package pw.jcollado.segamecontroller.model


/**
 * Created by jcolladosp on 13/02/2018.
 */

data class Property(val id: Int, val title: String, var houses: Int,var hotel: Boolean, var mortgage: Boolean) {

    fun build() {
        when (houses){
            in 0..3 -> houses++
            else -> hotel = true
        }
    }
    fun mortgage(){
        if(mortgage){
            mortgage = false
            //code to unmortgage
        }
        else{
            mortgage = true
            //code to mortgage
        }
    }

}