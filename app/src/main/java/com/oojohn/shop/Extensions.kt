package com.oojohn.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nickname.*

//================第五章 單元3================================= Activity父類別，寫setNickname方法

fun Activity.setNickname(nickname:String){   //Activity類別要有一個新功能 setNickname為fun名稱
    getSharedPreferences("shop", Context.MODE_PRIVATE)
        .edit()
        .putString("NICKNAME",nickname) //建構子
        .apply()
}
fun Activity.getNickname(): String? {
    return getSharedPreferences("shop", Context.MODE_PRIVATE).getString("NICKNAME","") //getString("NICKNAME","")讀取不到的時候給空字串
}