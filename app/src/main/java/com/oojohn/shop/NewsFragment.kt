package com.oojohn.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NewsFragment :Fragment(){
    companion object{
//        fun getInstance():NewsFragment{   kotlin有更好的方式表達如下
        val instance:NewsFragment by lazy { NewsFragment()   //by lazy{}用法為該物件不要一開始就生出來，有人要的時候再執行括號中new出來的類別
    //==================上述為Singleton單一物件設計

}


    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        //nCreateView方法，會再產生一個view的時候被執行，然後回傳一個View，inflater是用來把XML資料充氣成一個View物件，container是所在的容器
        return inflater.inflate(R.layout.fragment_news,container,false)  //第三個參數false，問說要不要一開始出現的時候就去連接container
//   return super.onCreateView(inflater, container, savedInstanceState)
    }
}