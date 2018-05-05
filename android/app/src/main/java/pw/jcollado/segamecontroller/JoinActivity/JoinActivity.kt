package pw.jcollado.segamecontroller.joinActivity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pw.jcollado.segamecontroller.R
import pw.jcollado.segamecontroller.connections.AsyncResponse
import pw.jcollado.segamecontroller.connections.ServerConnectionThread
import pw.jcollado.segamecontroller.mainActivity.MainActivity
import pw.jcollado.segamecontroller.model.*
import pw.jcollado.segamecontroller.utils.closeLoadingDialog
import pw.jcollado.segamecontroller.utils.loadDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.provider.Settings.Secure;
import com.afollestad.materialdialogs.MaterialDialog
import android.text.InputType


open class JoinActivity : App(), AsyncResponse {
    open lateinit var gamethread: ServerConnectionThread
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setupUI()
        savePort(8080)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        resumeTX.bringToFront()
        monopolyIV.bringToFront()
        setUpVideoView()
        modeTX.bringToFront()

    }

    private fun setupUI(){
        if(preferences.port != 8080){
            resumeTX.text = "${getString(R.string.character_name_resume)} ${preferences.character}"
            joinButton.text = getString(R.string.resume_game)

            if (preferences.gamemode == "remote"){
                modeTX.text = getString(R.string.playing_remote)
            }
            else{
                modeTX.text = "${getString(R.string.playing_local)} ${preferences.ip}"

            }
        }

            joinButton.onClick { joinServerNewGame() }
            changeModeButton.onClick { onChangeServerMode() }

        if (preferences.gamemode == "remote"){
            changeModeButton.text = getString(R.string.play_local)

        }
        else{
            changeModeButton.text = getString(R.string.play_online)

        }
    }

    private fun joinServerNewGame(){
        runOnUiThread {
            loadDialog(getString(R.string.connecting))
        }
        ServerConnectionThread.IP = preferences.ip
       var androidId = Secure.getString(applicationContext.getContentResolver(), Secure.ANDROID_ID)
        Log.i("id",androidId)
        val joinGameRequest = Request(-1,"connect",androidId)
        val jsonStringRequest = joinGameRequest.toJSONString()
        try {
            gamethread = ServerConnectionThread(this, preferences.port)
            gamethread.start()
            gamethread.setMessage(jsonStringRequest)
        }catch (e: Exception){
            Log.e("error",e.getStackTraceString())
            runOnUiThread {
                closeLoadingDialog()
                toast(getString(R.string.error_server))
            }
        }

        handler.postDelayed({
            runOnUiThread {
                closeLoadingDialog()
                toast(getString(R.string.error_server))
            }
        }, 10000)

    }
    private fun changeModeToRemote(){
        changeModeButton.text = getString(R.string.play_local)
        preferences.gamemode = "remote"
        preferences.ip = "52.48.249.220"
        modeTX.text = ""
    }
    private fun changeModeToLocal(){
        changeModeButton.text = getString(R.string.play_online)
        preferences.gamemode = "local"
    }
    private fun onChangeServerMode(){
    if (preferences.gamemode == "remote"){
        MaterialDialog.Builder(this)
                .content(R.string.title_ip)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("192.168.1.20", "", { _, input ->
                   preferences.ip = input.toString()
                    modeTX.text = "IP: $input"
                    changeModeToLocal()
                }).show()
        }
        else{
            changeModeToRemote()
        }
    }



    private fun getResponseID(response: String){

        Log.i("response",response)
        if(response.contains("port")){
            val responseRequest = RequestFunctions().portFromJSONString(response)
            responseRequest?.id?.let { saveUserID(it) }
            responseRequest?.port?.let { savePort(it) }
            runOnUiThread {
                closeLoadingDialog()
            }
            gamethread.kill()
            startActivity<MainActivity>()


        }

    }


    private fun saveUserID(id: Int){
        preferences.playerID = id
    }
    private fun savePort(port: Int){
        preferences.port = port
    }


    override fun handleResponse(response: String?) {

        when (response) {
            null -> response?.let { toast(it) }
            "" -> toast(response)
            else -> {
                getResponseID(response)
                handler.removeCallbacksAndMessages(null)
            }

        }
    }

    private fun setUpVideoView() {
        val uriPath = ("android.resource://" + packageName + "/raw/galaxy" )
        val uri = Uri.parse(uriPath)

        try {
            background_vw.setVideoURI(uri)
            background_vw.requestFocus()
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        background_vw.setOnPreparedListener(videoViewListener)
    }

    private val videoViewListener = MediaPlayer.OnPreparedListener { mediaPlayer ->

        mediaPlayer.isLooping = true
        background_vw.start()

    }

}
