package edu.us.ischool.janeq97.quizdroid

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast

class QuizActivity : AppCompatActivity(), TopicOverviewFragment.TopicOverviewFragmentListener,
    QuizFragment.QuizFragmentListener, AnswerFragment.AnswerFragmentListener {

    var data = mutableListOf<Topic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        val jsonFile = assets.open("questions.json")

        val url = intent.getStringExtra("URL")
        val downloadFreq = intent.getIntExtra("DOWNLOAD_FREQUENCY", 0)

        if (isAirplanceMode(this)) {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Airplane Mode")
            builder.setMessage("Looks like airplane mode is on, some features may not work properly. Do you want to turn airplane mode off?")
            builder.setPositiveButton("YES"){dialog, which ->
                val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                startActivity(intent)
            }
            builder.setNegativeButton("No"){dialog,which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else if (isNetworkAvailable() == false) {
            Toast.makeText(this, "You are not connected to the internet", Toast.LENGTH_LONG).show()
            Log.i("TEST", "Network is not available")
        } else {
            data = QuizApp.getRepositoryData(jsonFile, this, url, downloadFreq)
            Log.i("TEST", "Network is available")
            val topic = intent.getStringExtra("TOPIC")
            val overviewFragment = TopicOverviewFragment.newInstance(topic, data)
            replaceFragment(overviewFragment)
        }

    }

    private fun isAirplanceMode(context: Context): Boolean {
        return Settings.System.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    private fun isNetworkAvailable(): Boolean {
        Log.i("TEST", "is network available is running")
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
        return false
    }


    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }


    override fun beginQuiz(topic: String) {
        val quizFragment = QuizFragment.newInstance(topic, 0, 0, data)
        replaceFragment(quizFragment)
    }

    override fun submitAnswer(result: String, yourAnswer: String, answer: String,
                              questionIndex: Int, numCorrect: Int, totalQuestions: Int,
                              topic: String) {
        var answerFragment = AnswerFragment.newInstance(result, yourAnswer,
            answer, questionIndex, numCorrect, totalQuestions, topic)
        replaceFragment(answerFragment)
    }

    override fun continueQuiz(topic: String, numCorrect: Int, questionIndex: Int,
                              totalQuestions: Int) {
        if (questionIndex < totalQuestions) {
            val quizFragment = QuizFragment.newInstance(topic, questionIndex,
                numCorrect, data)
            replaceFragment(quizFragment)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
