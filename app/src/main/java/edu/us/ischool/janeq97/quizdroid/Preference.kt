package edu.us.ischool.janeq97.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class Preference: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preference)

//        val btnPreference = findViewById<Button>(R.id.btnPreference)
//        btnPreference.setOnClickListener() {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}