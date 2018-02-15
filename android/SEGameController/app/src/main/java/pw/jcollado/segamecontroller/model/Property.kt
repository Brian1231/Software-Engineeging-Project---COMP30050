package pw.jcollado.segamecontroller.model


/**
 * Created by jcolladosp on 13/02/2018.
 */

data class Property(val id: Int, val title: String, var mortgageValue: Int) {
    var canBuild: Boolean = true
    var isMortgaged: Boolean = false
    var hotel: Boolean = false
    var houses: Int = 0

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

    fun mortgage() {
        if (isMortgaged) {
            isMortgaged = false
            //code to unmortgage
        } else {
            isMortgaged = true
            //code to isMortgaged
        }
    }

}