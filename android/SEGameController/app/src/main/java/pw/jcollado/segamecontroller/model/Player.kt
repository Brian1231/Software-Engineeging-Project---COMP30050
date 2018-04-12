package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

/**
 * Created by jcolladosp on 15/02/2018.
 */
object Player {
    // {"character":"John Keats","balance":1000,"fuel":1,"id":1,"position":0,"properties":[]}


     var character: String = ""
     var balance: Int = 0
     var position: Int = 0
     var fuel: Int = 0
     var id: Int = 0
     var properties: ArrayList<Property> = ArrayList()



}