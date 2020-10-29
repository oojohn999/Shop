package com.oojohn.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val fragmentTransaction:FragmentTransaction=supportFragmentManager.beginTransaction() //Transaction(交易):當畫面改變時，會有很多動作，如先移除舊的載入新的，為連續性，都要成功才換畫面
        fragmentTransaction.add(R.id.container1,NewsFragment.instance) // 加入activity_news中的 container1 元件，執行NewsFragment()物件，該物件再載入fragment_news
        //NewsFragment()由自己寫的類別產生物件
        fragmentTransaction.commit()

    }
}