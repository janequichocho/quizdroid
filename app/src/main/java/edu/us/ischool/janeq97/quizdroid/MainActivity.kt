package edu.us.ischool.janeq97.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity(), QuizMenuFragment.QuizMenuFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun goToTopicOverview(topic: String) {
        Log.i("TEST", topic)
    }

    /*fun goToTopicOverview(view: View) {
        val button: Button = view as Button
        val intent = Intent(this, TopicOverviewFragment::class.java)
        var topic: String = button.text.toString()
        intent.putExtra("TOPIC", topic)
        startActivity(intent)
    }*/
}
