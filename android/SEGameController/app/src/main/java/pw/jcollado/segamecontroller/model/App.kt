package pw.jcollado.segamecontroller.model

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pw.jcollado.segamecontroller.utils.Prefs

/**
 * Created by jcolladosp on 07/03/2018.
 */
val preferences: Prefs by lazy {
    App.prefs!!
}

abstract class App : AppCompatActivity() {


    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = Prefs(applicationContext)
        super.onCreate(savedInstanceState)
    }

}