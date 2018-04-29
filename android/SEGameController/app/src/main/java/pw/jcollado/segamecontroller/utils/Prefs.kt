package pw.jcollado.segamecontroller.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by jcolladosp on 07/03/2018.
 */

class Prefs (context: Context) {
    private val PLAYER_ID = "PlayerID"
    private val PORT = "PORT"
    private val CHARACTER = "CHARACTER"

    val prefs: SharedPreferences = context.defaultSharedPreferences

    var playerID: Int
        get() = prefs.getInt(PLAYER_ID, -1)
        set(value) = prefs.edit().putInt(PLAYER_ID, value).apply()
    var port: Int
        get() = prefs.getInt(PORT, 8080)
        set(value) = prefs.edit().putInt(PORT, value).apply()
    var character: String
        get() = prefs.getString(CHARACTER, "character")
        set(value) = prefs.edit().putString(CHARACTER, value).apply()
}
