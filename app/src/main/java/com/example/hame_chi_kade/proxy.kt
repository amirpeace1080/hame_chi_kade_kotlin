package com.example.hame_chi_kade

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class proxy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_proxy)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        apiCall()
    }

    // call api
    private fun apiCall() {
        val url = "https://api.codebazan.ir/mtproto/json/"
        val requestQueue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Handle the response data
                try {
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("Result")

                    val linearLayout = findViewById<LinearLayout>(R.id.linear_layout)
                    val btn_proxy = findViewById<Button>(R.id.btn_proxy)

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val server = item.getString("server") // استفاده از نام فیلد "server"
                        val port = item.getString("port") // استفاده از نام فیلد "port"
                        val secret = item.getString("secret") // استفاده از نام فیلد "secret"

                        // Create a string to hold the information of each item
                        val itemInfo = "$port \n $server \n $secret"

                        // Create a button for each item and set its text
                        val button = Button(this)
                        button.text = itemInfo

                        // Set an onClickListener to the button
                        button.setOnClickListener {


                            val packageName = "org.telegram.messenger"
                            val intent = packageManager.getLaunchIntentForPackage(packageName)
                            if (intent != null) {
                                val telegram = Intent(Intent.ACTION_VIEW)
                                telegram.setData(Uri.parse("http://telegram.me/proxy?server=$server&port=$port&secret=$secret"))
                                telegram.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                telegram.setPackage("org.telegram.messenger")
                                this.startActivity(telegram)
                            } else {
                                // Telegram app not installed, handle this case
                                Toast.makeText(this, "تلگرام نصب نیست", Toast.LENGTH_LONG).show()
                            }

                        }


                        // Add the button to the linear layout
                        linearLayout.addView(button)
                    }

                } catch (e: JSONException) {
                    // Handle JSON exception
                    e.printStackTrace()
                    // Show error message to the user
                    Toast.makeText(this, "خطای ناشناخته", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Handle errors
                // Show error message to the user
                Toast.makeText(this, "خطای اتصال به اینترنت " + error.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(stringRequest)
    }

}
