package com.cis.kotlinjsonclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.text = ""

        btn.setOnClickListener {
            val thread = NetworkThread()
            thread.start()
        }
    }

    inner class NetworkThread : Thread() {
        override fun run() {
            try {
                val site = "http://10.211.55.4:8080/JsonUse/json.jsp"
                val url = URL(site)
                val conn = url.openConnection()
                val input = conn.getInputStream()
                val isr = InputStreamReader(input)
                val br = BufferedReader(isr)

                var str: String? = null
                val buf = StringBuffer()

                do {
                    str = br.readLine()
                    if (str != null) {
                        buf.append(str)
                    }
                } while (str != null)

                var root = JSONArray(buf.toString())

                for (i in 0 until root.length()) {
                    var obj = root.getJSONObject(i)

                    var data1 = obj.getString("data1")
                    var data2 = obj.getInt("data2")
                    var data3 = obj.getDouble("data3")

                    runOnUiThread {
                        tv.append("data1 :${data1}\n")
                        tv.append("data2 :${data2}\n")
                        tv.append("data3 :${data3}\n\n")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
