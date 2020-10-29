package com.oojohn.shop

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_parking.*
import org.jetbrains.anko.*
import java.net.URL
//================第7章 第1單元==============AsyncTask===================
class ParkingActivity : AppCompatActivity() ,AnkoLogger{  //實作AnkoLogger介面來debug,info,warnig等
    private val TAG =ParkingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        val parking="https://pastebin.com/raw/fdRydxy2"

        //================第7章 第2單元============== Anko ===================
        doAsync {
            val url =URL(parking)
            val json =url.readText()
            info(json)
            //不能在非UI執行緒存取資料，用uiThread可以存取 MainThread(UI執行緒)的元件
            uiThread {
             Toast.makeText(it,"Got it",Toast.LENGTH_LONG).show()
                toast("Got it") //Anko語法，短的Toast
                info.text=json
                alert("Got","ALERT"){
                    okButton {  }
                    //================第7章 第2單元============== Anko ===================
                    parseGson(json)

                }.show()
            }

        }

//        ParkingTask().execute(parking)           AsyncTask使用後續換 Anko資料庫支援

    }
    //================第7章 第3單元============== 資料庫Gson ===================
    private fun parseGson(json: String) {
        //將json資料變成集合，在放入Parking類別中的List<ParkingLot>集合，每個屬性如areaId對照放入，另json中集合名稱parkingLots在Parking類別中，使用同名稱，有對照寫入概念
        val parking=Gson().fromJson<Parking>(json, Parking::class.java) //定義parking物件=Gson物件.fromJson方法(下載的json,自訂的Parking)，json檔轉換為Parking類別
        info(parking.parkingLots.size)
        parking.parkingLots.forEach {
            info("${it.areaId} ${it.areaName} ${it.parkName} ${it.totalSpace}")
        }



    }

    //================第7章 第2單元============== Anko ===================
    inner class ParkingTask : AsyncTask<String,Void,String>() //AsyncTask需要訂定三個資料型態，1.參數、2.中間回報資料、3.回傳結果，
    //同步工作第一個要傳送網址，故型態為String,中間不用回報，最後要回傳json文字檔，故也是設定String型態
    {
        override fun doInBackground(vararg params: String?): String {
            val url =URL(params[0])//parking資料在傳送到另一個執行緒的內容會是params，取代parking，params[0]取得第一個字串
            val json =url.readText()
            Log.d(TAG, "doInBackground:$json ")

            return json
        }
        override fun onPostExecute(result: String?) { //doInBackground執行"後"使用，json為字串資料，doInBackground執行完成後在 MainThread傳入result
            super.onPostExecute(result)
            Toast.makeText(this@ParkingActivity,"Got it",Toast.LENGTH_LONG).show()
            info.text=result
        }
        override fun onPreExecute() {  //doInBackground執行前使用
            super.onPreExecute()
        }
        override fun onProgressUpdate(vararg values: Void?) {//doInBackground過程中使用回報資訊
            super.onProgressUpdate(*values)
        }
    }
}

//================第7章 第3單元============== 資料庫Gson ===================
/*
 {
      "parkingLots": [
  {
          "areaId": "1",
          "areaName": "桃園區",
          "parkName": "桃園縣公有府前地下停車場",
          "totalSpace": 334,
          "surplusSpace": "10",
          "payGuide": "停車費率:30 元/小時。停車時數未滿一小時者，以一小時計算。逾一小時者，其超過之不滿一小時部分，如不逾三十分鐘者，以半小時計算；如逾三十分鐘者，仍以一小時計算收費。",
          "introduction": "桃園市政府管轄之停車場",
          "address": "桃園區縣府路1號(出入口位於桃園市政府警察局前)",
          "wgsX": 121.3011,
          "wgsY": 24.9934,
          "parkId": "P-TY-001"
        }
        ]
        }

        Gson方法
將json資料變成集合，在放入Parking類別中的List<ParkingLot>集合，每個屬性如areaId對照放入，另json中集合名稱parkingLots在Parking類別中，使用同名稱，有對照寫入概念

 */

//class Parking(val parkingLots:List<ParkingLot>) // 該類別的建構子為集合，parkingLots建構子為jason檔中的集合名稱，<>中放data class的類別
//// Parking類別(val json集合: List<data class>)
//data class ParkingLot(  //data class用來儲存的類別，建立後建構子即完成
//    val areaId:String,
//    val areaName:String,
//    val parkName:String,
//    val totalSpace:Int
//
//)

 class Parking(
    val parkingLots: List<ParkingLot>
)

data class ParkingLot(
    val address: String,
    val areaId: String,
    val areaName: String,
    val introduction: String,
    val parkId: String,
    val parkName: String,
    val payGuide: String,
    val surplusSpace: String,
    val totalSpace: Int,
    val wgsX: Double,
    val wgsY: Double
)