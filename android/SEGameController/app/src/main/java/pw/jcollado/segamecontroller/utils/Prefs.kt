package pw.jcollado.segamecontroller.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by jcolladosp on 07/03/2018.
 */

class Prefs (context: Context) {
    val PLAYER_ID = "PlayerID"
    val prefs: SharedPreferences = context.defaultSharedPreferences

    var playerID: String
        get() = prefs.getString(PLAYER_ID, "-1")
        set(value) = prefs.edit().putString(PLAYER_ID, value).apply()
}
