package com.oojohn.shop

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import org.jetbrains.anko.*
import java.net.URL

class MainActivity : AppCompatActivity(),AnkoLogger {
    private val TAG=MainActivity::class.java.simpleName
    private val RC_SIGNUP: Int=200
    private val RC_NICKNAME: Int=210
    val functions = listOf<String>("Camera","Invite freind","Parking","Movies","Download coupons","News","Maps")
    var signup =false
    val auth=FirebaseAuth.getInstance() //Firebase認證狀態的傾聽器，FirebaseAuth為類別，.getInstance變為物件
    var cacheService:Intent?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //================第五章 單元2=================================
        /*    if (!signup){  //if(true)就會執行，故用 (!sign)，預設登入，如果sign=true,就不執行登入了
            val intent =Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP)
        }

     */
        //===============================監聽 auth物件變動=================
        auth.addAuthStateListener { auth ->
            authChanged(auth)
        }




        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //spinner //================第六章 單元1=================================
        val colors = arrayOf("Red", "Green", "Blue")
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors)//設定adapter
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line) //layout管理
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) { //P1 為點選的TextView, P2為 Position ,P3為ID,目前不用
                Log.d("MainActivity", "onItemSelected:${colors[position]} ")

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //RecycleView //================第六章 單元2=================================

        recycler.layoutManager =
            LinearLayoutManager(this) //右邊需要一個RecycleView.LayoutManager物件，清單式編排頁面
        recycler.setHasFixedSize(true) //固定大小
        recycler.adapter = FunctionAdapter()
    }

        inner class FunctionAdapter() :RecyclerView.Adapter<FunctionHolder>(){  //FunctionAdapter繼承RecyclerView.Adapter<ViewHolder>，ViewHolder為必須宣告
            // 故FunctionHolder另外寫再放入RecyclerView.Adapter
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder { //viewHolder要產生時，此處介面規定要產生一個FunctionHolder
                val view = LayoutInflater.from(parent.context)  //LayoutInflater這個class主要功能是將user-defined layout的xml實體化為對應的View物件
                    .inflate(R.layout.row_function, parent, false)
                val holder = FunctionHolder(view)
                return holder
            }


            override fun onBindViewHolder(holder: FunctionHolder, position: Int) {  //當有資料時會呼叫onBindViewHolder
                holder.nameText.text=functions.get(position)  //nameText.text方法為setText
                holder.itemView.setOnClickListener {view->
                    functionClciked(holder,position) //另寫方法
                }
            }

            override fun getItemCount(): Int {
                return functions.size
            }


        }   //繼承Adapter類別

    private fun functionClciked(holder: FunctionHolder, position: Int) {
        Log.d(TAG, "functionClciked: $position")
        when (position) {
            1 -> startActivity(
                Intent(
                    this,
                    ContactActivity::class.java
                )
            ) // ===========直接new出一個 intent物件================，雙冒號 ::表示調用自class.java
            2 -> startActivity(
                Intent(
                    this,
                    ParkingActivity::class.java
                )
            )//================第七章 單元1====================AsyncTask=============
            3 -> startActivity(
                Intent(
                    this,
                    MovieActivity::class.java
                )
            )//================第七章 單元3==================Movies==Recycle=============
            5 -> startActivity(
                Intent(
                    this,
                    NewsActivity::class.java
                )            ) //================第8章 單元3==================Fragment=============
            6-> startActivity(
                Intent(
                    this,
                    MapsActivity::class.java
                )
            )
        }
    }

    class FunctionHolder(view: View):RecyclerView.ViewHolder(view){ //FunctionHolder繼承 RecyclerView.ViewHolder(暫存器)，主要目的為設定layout上
        // 各個元件及其資源，其中建構子View創建在FunctionHolder中，上傳至父類別後，再於子類別寫入時使用
        var nameText: TextView =view.name

    }






    //================第五章 單元3=================================
    override fun onResume() {  //畫面上去後，就可以準備把Nickname設定上去
        super.onResume()
//        nickname.text=getNickname()  //設定開始畫面的暱稱
        //================第五章 單元 4=================================  Activity Resume時就會自動讀到 Firebase資料，顯示在文字方格
//        FirebaseDatabase.getInstance()
//            .getReference("users")
//            .child(auth.currentUser!!.uid)
//            .child("nickname")
//            .addListenerForSingleValueEvent(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                   nickname.text= snapshot.value as String //snapshot取字串值用法
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })
    }

    private fun authChanged(auth: FirebaseAuth) {
        if (auth.currentUser==null){
            val intent =Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP)
        }
        else {
            Log.d("MainActivity", "authChanged:${auth.currentUser?.uid} ")
        }
    }

    //================第五章 單元2=================================
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==RC_SIGNUP){
            if (resultCode== RESULT_OK){  //當註冊成功setResult就會回傳
                val intent=Intent(this,NicknameActivity::class.java)
                startActivityForResult(intent,RC_NICKNAME)
            }
            if (requestCode==RC_NICKNAME){

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //================第8章 單元2=================================
    val broadcastReceiver= object  : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent?.action.equals(CacheService.ACTION_CACHE_DONE)) { //如果收到廣播
//                toast("MainActivity cache informed")
                info("MainActivity cache informed")
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_cache->{
                //================第8章 單元2=================================
                doAsync {
                    val json= URL("https://pastebin.com/raw/UvJRsdhA").readText()
                    val  movies= Gson().fromJson<List<MovieActivity.Movie>>(json,
                        object : TypeToken<List<MovieActivity.Movie>>(){}.type)
                    movies.forEach {
                        startService( intentFor<CacheService>( //將Title,URL傳送給CacheServie類別
                            "TITLE" to it.Title,
                            "URL" to it.Poster))
                    }
//                val intent=Intent(this,CacheService::class.java)
//                intent.putExtra("Title",movie.Title)
//                intent.putExtra("URL",movie.Poster)
//                startService(intent)

                }
                true //return when中必須回傳true才不會有錯誤
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //================第8章 單元2=================================
    override fun onStart() {
        super.onStart()
        val filter=IntentFilter(CacheService.ACTION_CACHE_DONE)
        registerReceiver(broadcastReceiver,filter)
    }
        override fun onStop() {
        super.onStop()
////        stopService(cacheService)
        unregisterReceiver(broadcastReceiver)
    }
}