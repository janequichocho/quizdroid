package edu.us.ischool.janeq97.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity(), QuizMenuFragment.QuizMenuFragmentListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(QuizMenuFragment())
    }

    override fun goToTopicOverview(topic: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("TOPIC", topic)
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val i = Intent(this, Preference::class.java)
        startActivity(i)
        return true
    }
}
