package pw.jcollado.segamecontroller.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pw.jcollado.segamecontroller.model.Player


/**
 * Created by jcolladosp on 13/02/2018.
 */
var progressBar: ProgressDialog? = null


fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)


}

fun Context.loadDialog(con: Context,message:String){
        progressBar = ProgressDialog(this)
        progressBar?.setMessage(message)
        progressBar?.setCancelable(false)
        progressBar?.show()

}

fun Context.getPropertyImageURL(propertyName:String): String{
  return "http://52.48.249.220/worlds/$propertyName.jpg"

}

fun Context.getPlayerColor(): ColorDrawable{
    val intColor = Player.colour
    val hexColor = "#" + Integer.toHexString(intColor).substring(2)
    val color = Color.parseColor(hexColor)
    return ColorDrawable(color)
}
fun Context.closeLoadingDialog(){

    progressBar?.dismiss()

}



