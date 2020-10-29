package com.oojohn.shop

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//================第六章 單元3=================================
class ContactActivity : AppCompatActivity() {
    private val TAG=ContactActivity::class.java.simpleName
    private val RC_CONTACTS=110
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        //ContextCompat用來得到使用者有沒有讀取聯絡人權限，以下該行為詢問視窗，得到的結果變成permission物件
       val permission=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) //使用Manifest(Android)類別，READ_CONTACTS為僅讀取聯絡人
        if (permission!=PackageManager.PERMISSION_GRANTED) {//PackageManager類別中的PERMISSION_GRANTED代表同意權取得了
            //requestPermissions為蹦出對話框詢問權限，用arrayof陣列方法，可以放置多種詢問權限，本處只有一個
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),RC_CONTACTS)
        }else {
            readContacts()
        }


    }

    override fun onRequestPermissionsResult(  //不論權限拒絕會存取都會跑到onRequestPermissions方法
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==RC_CONTACTS){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){  //grantResults是一個Int的陣列，所以訪問grantResults[0]放置的同意權限
                readContacts()

            }
        }

    }


    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )//不挑欄位,不挑whrere,不選參數,不排序都null，共四個
        //用query得到Cursor物件，為一個讀取資料庫的指標，可以一行一行讀出來
        //一開始cursor的指標停在第一筆資料之前，moveToNext倘無資料即回傳False
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name =
                    //getString有點像map集合，當游標指到那一行，cursor.getString(key),就可以得到value
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) //DISPLAY_NAME為顯示聯絡人名稱專用 ，ContactsContract.Contacts儲存每個欄位名稱，getColumnIndex找到該名稱欄位的索引值
                Log.d(TAG, "onCreate: $name ")

            }
        }
    }
}