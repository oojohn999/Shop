package com.oojohn.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //================第五章 單元2=================================

        signup.setOnClickListener {
            val sEmail=email.text.toString()
            val sPassword=password.text.toString()
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful()){   //完成的時候(包括成功或失敗)，isSuccessful為 boolean值
                        AlertDialog.Builder(this)
                            .setTitle("Sing Up")
                            .setMessage("Account created")
                            .setPositiveButton("ok"){  //onClickListener最後的lambda語法的參數也是lambda,可以移出括號外寫
                                dialog,which->
                                setResult(RESULT_OK) // 如果有資料傳送用setResult(RESULT_OK,intent)

                                finish()
                            }.show()
                    }
                    else{
                        AlertDialog.Builder(this)
                            .setTitle("Sing Up")
                            .setMessage(it.exception?.message) //呼叫失敗會有一個exception物件,呼叫其中的message物件
                            .setPositiveButton("ok",null)
                            .show()

                    }
                }
        }
    }
}