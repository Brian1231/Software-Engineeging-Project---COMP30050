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
     var properties: List<String> = ArrayList()

    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    val jsonAdapter = moshi.adapter(Player::class.java)

    open fun getInstance(): Player {
        return this

    }

    fun fromJSONString(json: String): Player? {
        var aux = jsonAdapter.fromJson(json) as Player
        character = aux.character
        return aux
    }
    fun toJSONString(): String {

        return jsonAdapter.toJson(this)

    }

}