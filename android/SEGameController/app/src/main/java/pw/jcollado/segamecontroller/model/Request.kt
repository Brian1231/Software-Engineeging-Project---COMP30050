package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi




/**
 * Created by jcolladosp on 07/03/2018.
 */
data class Request(val id: Int,val action: String){
    fun toJSONString(): String {

        return RequestFunctions().toJSONString(this)

    }

}

class RequestFunctions{

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    private val jsonAdapter = moshi.adapter(Request::class.java)

    fun toJSONString(request: Request): String {

        return jsonAdapter.toJson(request)

    }
    fun fromJSONString(json: String): Request? {
        return jsonAdapter.fromJson(json)
    }
}