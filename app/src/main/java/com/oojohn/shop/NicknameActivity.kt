package com.oojohn.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nickname.*


//================第五章 單元2=================================
class NicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        //設定輸入暱稱後  按下Finish鈕的方法
        done.setOnClickListener {
            //TODO:nickname TODO的標籤碼可以看到還有哪些還沒處理，處理完再刪除，在左下方有TODO按鈕
//            nick.text.toString() //設定書籤在左方(Ctrl+F11按鍵)，如書籤設定1，未來按Ctrl+1就會自動跳回
//            getSharedPreferences("shop", MODE_PRIVATE)
//                .edit()
//                .putString("NICKNAME",nick.text.toString())
//                .apply()

            //================第五章 單元3=================================
            setNickname(nick.text.toString())  //在Extension中已寫了父類別Activity的setNickname方法
            //================第五章 單元4=================================

            FirebaseDatabase.getInstance()
                .getReference("users")  //建立根結點 users
                .child(FirebaseAuth.getInstance().currentUser!!.uid)//currentUser!! 因為絕對不會是null值，所以用!!而不用?(問號)
                .child("nickname")
                .setValue(nick.text.toString())
            setResult(RESULT_OK)
            finish()

        }
    }
}