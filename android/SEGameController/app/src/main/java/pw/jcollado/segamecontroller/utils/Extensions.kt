package pw.jcollado.segamecontroller.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.model.preferences

/**
 * Created by jcolladosp on 13/02/2018.
 */

internal var thread: ServerConnectionThread? = null

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Context.requestToServer(request: String){
    Log.i("lol",request)
    if(thread == null){
        thread = ServerConnectionThread(this, preferences.port)
        thread!!.start()
    }
    else{
        thread!!.setMessage(request)
    }


}

fun Context.closeThread(){
    thread?.let { it.kill() }
    thread = null

}

