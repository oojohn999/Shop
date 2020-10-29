package com.oojohn.shop

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_parking.*
import kotlinx.android.synthetic.main.row_movie.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.net.URL
//================第7章 單元4==========RecyclerView 顯示Json=======================
class MovieActivity : AppCompatActivity() ,AnkoLogger{
    var movies:List<Movie>?=null

    //================第7章 單元5==========Retrofit2=======================
    val retrofit=Retrofit.Builder()
        .baseUrl("https://pastebin.com/raw/")
        .addConverterFactory(GsonConverterFactory.create()).build()
   // ============================================================================1.基本網址呼叫 2.產出介面格式物件 3.呼叫介面方法,excute產生集合

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        //get json
        doAsync {
            //================第7章 單元5==========Retrofit2=======================
//            val json=URL("https://pastebin.com/raw/UvJRsdhA").readText()
//            movies=Gson().fromJson<List<Movie>>(json,
//                object :TypeToken<List<Movie>>(){}.type)  //因為前面是List集合，後面不能只放Movie類別，在此為集合，故須使用TypeToken<>
            val movieService=retrofit.create(MovieService::class.java) //放入最下方寫的MovieService介面
            movies=movieService.listMovies()
                .execute().body()

            movies?.forEach {
                info("${it.Title} ${it.imdbRating} ")
            }
            uiThread {
                recycler.layoutManager=LinearLayoutManager(this@MovieActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter=MovieAdapter()

            }
        }


        }

    inner class MovieAdapter():RecyclerView.Adapter<MovieHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.row_movie,parent,false)
            return MovieHolder(view)
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movie:Movie?=movies?.get(position)
            holder.bindMovie(movie!!)
        }

        override fun getItemCount(): Int {
            val size=movies?.size?:0
            return size
        }

    }

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view){
        //設定元件
        val titleText: TextView=view.movie_tiltle
        val imdbText: TextView=view.movie_imdb
        val directorText:TextView=view.movie_directior
        val posterImage:ImageView=view.movie_poster
        //輸入資料
        fun bindMovie(movie:Movie){
            titleText.text=movie.Title
            imdbText.text=movie.imdbRating
            directorText.text=movie.Director


            Glide.with(this@MovieActivity)
                .load(movie.Poster)
                .override(100)
                .into(posterImage)

        }

    }


data class Movie(
    val Actors: String,
    val Awards: String,
    val ComingSoon: Boolean,
    val Country: String,
    val Director: String,
    val Genre: String,
    val Images: List<String>,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Rated: String,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    val totalSeasons: String
)
    //================第7章 單元5==========Retrofit2=======================
    //先有基本網址，針對不同網址設計不同的介面方法(目前只有一個)
    interface MovieService{  //定義介面，裡面定義listMovies()的集合存取方法
        @GET("UvJRsdhA")  //GET後面的值，代表呼叫該方法時，請在那個網址加入辨識Path路徑
        fun listMovies(): retrofit2.Call<List<Movie>>  //定義存取的方法，call目的是告訴未來呼叫會得到 Movie集合的資訊
        //此處也可另外設計刪除，更動或新增的方法
    }



}

