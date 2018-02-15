package pw.jcollado.segamecontroller.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by jcolladosp on 13/02/2018.
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}