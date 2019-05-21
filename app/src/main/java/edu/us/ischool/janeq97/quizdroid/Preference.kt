package edu.us.ischool.janeq97.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class Preference: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preference)

        val btnPreference = findViewById<Button>(R.id.btnPreference)
        val editURL = findViewById<EditText>(R.id.editURL)
        val editDownload = findViewById<EditText>(R.id.editDownload)
        btnPreference.setOnClickListener() {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("URL", editURL.text.toString())
            intent.putExtra("DOWNLOAD_FREQUENCY", editDownload.text.toString().toInt())
            startActivity(intent)
        }
    }
}