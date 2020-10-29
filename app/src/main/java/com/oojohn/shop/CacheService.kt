package com.oojohn.shop

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.bumptech.glide.Glide
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class CacheService() : IntentService("acheService"),AnkoLogger{  //CacheService建構子中沒有參數，所以父類別中建構子要填入字串
//IntentService會自己destroy

    companion object{  //Java中 static概念 經常陪伴在身邊的物件
        val ACTION_CACHE_DONE="action_cache_done"
    }

    override fun onHandleIntent(intent: Intent?) {
        info("onHandleIntent")
    val title:String?=intent?.getStringExtra("TITLE")
    val url : String?=intent?.getStringExtra("URL")
    info("Downloading... $title $url")
    Glide.with(this)
        .download(url)  //先download到系統不顯示，之後存取會變快
        sendBroadcast(Intent(ACTION_CACHE_DONE))  //建構子中可以放Action的字串


    }

    override fun onCreate() {  //Service被系統產生後直接呼叫onCreate
        info("onCreate")
        super.onCreate()
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {  Service類別用，本處已改IntentService，避免不執行onHandleIntent，只執行onStartCommand
//        info("onStartCommand")
//        return START_STICKY
//    }

    override fun onDestroy() {  //用IntentService會在執行續完成後自動Destroy
        info("onDestroy")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null

    }



}