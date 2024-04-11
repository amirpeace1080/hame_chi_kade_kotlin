package com.example.hame_chi_kade

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class date : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "ساعت و تاریخ آنلاین"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        apiCall()
    }


//     call api
private fun apiCall() {
    val show_time = findViewById<TextView>(R.id.show_time)
    val show_date = findViewById<TextView>(R.id.show_date)
    val show_day = findViewById<TextView>(R.id.show_day)
    val show_fasl = findViewById<TextView>(R.id.show_fasl)
    val show_mah = findViewById<TextView>(R.id.show_mah)

    val url = "https://api.codebazan.ir/time-date/?json=fa"
    val queue = Volley.newRequestQueue(this)

    // Create a StringRequest for making a GET request to the URL
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            try {
                val jsonObject = JSONObject(response)
                val resultObject = jsonObject.getJSONObject("result")
                val time = resultObject.getString("time")
                val date = resultObject.getString("date")
                val day = resultObject.getString("faweekname")
                val fasl = resultObject.getString("fasl")
                val mah = resultObject.getString("mahname")
                show_time.text = time
                show_date.text = date
                show_day.text = day
                show_fasl.text = fasl
                show_mah.text = mah

            } catch (e: JSONException) {
                // Handle JSON parsing errors
                e.printStackTrace()
                // Show error message to the user
                Toast.makeText(this, "خطای ناشناخته", Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            // Handle errors
            error.printStackTrace()
            // Show error message to the user
            Toast.makeText(this, "خطا در ارتباط با سرور", Toast.LENGTH_SHORT).show()
        })

    // Add the request to the request queue
    queue.add(stringRequest)
}



}