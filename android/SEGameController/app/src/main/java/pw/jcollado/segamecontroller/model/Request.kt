package pw.jcollado.segamecontroller.model

import com.beust.klaxon.Klaxon

/**
 * Created by jcolladosp on 07/03/2018.
 */
data class Request(val id: Int,val action: String){



    fun toJSONString(): String {
        return Klaxon().toJsonString(this)
    }
}

