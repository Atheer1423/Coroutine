package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var b1: Button
    private lateinit var b2: Button
    var click=true

    val adviceUrl = "https://api.adviceslip.com/advice"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.tv)
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)

        b1.setOnClickListener {

            requestApi()

        }

    }
    private fun requestApi() {
        CoroutineScope(Dispatchers.IO).launch {
           while (click) {

                val data = async {

                    URL(adviceUrl).readText(Charsets.UTF_8)

                }.await()

                withContext(Dispatchers.Main)
                {


                    val jsonObject = JSONObject(data)
                    val slip = jsonObject.getJSONObject("slip")
                    val id = slip.getInt("id")

                    tv.text = slip.getString("advice")




                }
               b2.setOnClickListener { click=false }

            }


        }
    }


}