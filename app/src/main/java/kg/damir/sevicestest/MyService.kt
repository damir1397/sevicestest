package kg.damir.sevicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        val start = intent?.getIntExtra(EXTRA_START, 0) ?:0

        coroutineScope.launch {
            for (i in start until 100) {
                delay(1000)
                log("Timer $i")
            }
        }

        return START_REDELIVER_INTENT

    }
//    START_STICKY Перезапуск сервиса когда приложение удалена с диспечера но интент придет 0
//    START_NOT_STICKY Не перезапускает сервис когда приложение удалена с диспечера  но интент придет 0
//    START_REDELIVER_INTENT перезапускает сервис с тем же интентом котрый пришел


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("Service_Tag", "MyService $message")
    }

    companion object {

        private const val EXTRA_START = "start"

        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyService::class.java).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }
}