package com.example.hame_chi_kade

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class biography : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biography)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        apiCall()

        // imagebutton bio
        val button_next: Button = findViewById(R.id.button_next)
        button_next.setOnClickListener {
            startActivity(Intent(this, biography::class.java))
        }


        val showApiText = findViewById<TextView>(R.id.text_bio)
        val buttonCopy = findViewById<ImageButton>(R.id.button_copy)
        buttonCopy.setOnClickListener {
            // Get the text from the TextView
            val textToCopy = showApiText.text.toString()

            // Copy the text to the clipboard
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)

            // Provide feedback to the user
            Toast.makeText(this, "کپی شد", Toast.LENGTH_SHORT).show()
        }
    }


    // call api
    private fun apiCall() {
        val showApiText = findViewById<TextView>(R.id.text_bio)
        val url = "https://api.codebazan.ir/bio/"
        val queue = Volley.newRequestQueue(this)

        val requestQueue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                // Handle the response data
                showApiText.text = response
            },
            Response.ErrorListener { error ->
                // Handle errors
            })

        requestQueue.add(stringRequest)
    }


}