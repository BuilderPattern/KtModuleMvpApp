package kt.module.module_video.views

open interface IjkPlayerListener {
    fun onPrepared()
    fun onBuffer(percent: Int)
    fun onCompletion()
    fun onError(what: Int, extra: Int): Boolean
    fun onInfo(what: Int, extra: Int): Boolean
    fun onNetworkSpeedUpdate(speed: Int)
}