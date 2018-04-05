package pw.jcollado.segamecontroller.model

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi




/**
 * Created by jcolladosp on 07/03/2018.
 */

data class Request(val id: Int,val action: String)


data class PortRequest(val id: Int,val port: Int){

}





class RequestFunctions{

     val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    val jsonAdapter = moshi.adapter(Request::class.java)
    val jsonAdapterPort = moshi.adapter(PortRequest::class.java)

    fun requestToJSONString(request: Request): String {

        return RequestFunctions().jsonAdapter.toJson(request)

    }
    fun requestFromJSONString(json: String): Request? {
        return RequestFunctions().jsonAdapter.fromJson(json)
    }

    open fun portToJSONString(portRequest: PortRequest): String {

        return RequestFunctions().jsonAdapterPort.toJson(portRequest)

    }
    open fun portFromJSONString(json: String): PortRequest? {
        return RequestFunctions().jsonAdapterPort.fromJson(json)
    }

}