package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi




/**
 * Created by jcolladosp on 07/03/2018.
 */

data class Request(val id: Int,val action: String,val args: String){
    fun toJSONString(): String {

        return RequestFunctions().jsonAdapter.toJson(this)

    }
}


data class PortRequest(val id: Int,val port: Int){
    fun toJSONString(): String {

        return RequestFunctions().jsonAdapterPort.toJson(this)

    }

}
data class PlayerRequest(val id: Int,val balance: Int,val position: Int){
    fun toJSONString(): String {

        return RequestFunctions().jsonAdapterPlayer.toJson(this)

    }

}




class RequestFunctions{

     val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    val jsonAdapter = moshi.adapter(Request::class.java)
    val jsonAdapterPort = moshi.adapter(PortRequest::class.java)
    val jsonAdapterPlayer = moshi.adapter(PlayerRequest::class.java)
    val jsonAdapterProperty = moshi.adapter(Property::class.java)


    fun requestFromJSONString(json: String): Request? {
        return jsonAdapter.fromJson(json)
    }

    fun propertyFromJSONString(json: String): Property? {
        return jsonAdapterProperty.fromJson(json)
    }


    open fun portFromJSONString(json: String): PortRequest? {
        return jsonAdapterPort.fromJson(json)
    }
    open fun playerFromJSONString(json: String): PlayerRequest? {
        return jsonAdapterPlayer.fromJson(json)
    }

}