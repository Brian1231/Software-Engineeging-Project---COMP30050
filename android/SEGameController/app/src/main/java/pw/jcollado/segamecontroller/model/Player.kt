package pw.jcollado.segamecontroller.model

/**
 * Created by jcolladosp on 15/02/2018.
 */
class Player private constructor(){
    private var id: Int = -1
    private var balance: Int = 0
    private var position: Int = 0

    init {
        /*
        *  every time init is called increment instance count
        *  just in case somehow we break singleton rule, this will be
        *  called more than once and myInstancesCount > 1 == true
        */
        ++myInstancesCount
    }

    companion object {
        //Debuggable field to check instance count
        var myInstancesCount = 0
        private val mInstance: Player = Player()

        @Synchronized
        fun getInstance(): Player {
            return mInstance
        }
    }


}