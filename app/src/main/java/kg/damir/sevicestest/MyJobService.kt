package kg.damir.sevicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")

        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
            }
            jobFinished(params,true)
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }


//    START_STICKY Перезапуск сервиса когда приложение удалена с диспечера но интент придет 0
//    START_NOT_STICKY Не перезапускает сервис когда приложение удалена с диспечера  но интент придет 0
//    START_REDELIVER_INTENT перезапускает сервис с тем же интентом котрый пришел


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("Service_Tag", "MyJobService $message")
    }

    companion object {

        private const val EXTRA_START = "start"

        fun newIntent(context: Context, start: Int): Intent {
            return Intent(context, MyJobService::class.java).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }
}