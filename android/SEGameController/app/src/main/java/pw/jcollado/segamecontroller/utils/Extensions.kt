package pw.jcollado.segamecontroller.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pw.jcollado.segamecontroller.connections.ClientThread
import pw.jcollado.segamecontroller.model.Connections
import pw.jcollado.segamecontroller.model.preferences

/**
 * Created by jcolladosp on 13/02/2018.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Context.requestToServer(request: String){
    Log.i("lol",request)
    ClientThread(this).execute(request, Connections.IP.value, preferences.port.toString())
}

