package edu.us.ischool.janeq97.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

class QuizActivity : AppCompatActivity(), TopicOverviewFragment.TopicOverviewFragmentListener,
    QuizFragment.QuizFragmentListener, AnswerFragment.AnswerFragmentListener {

    var data = mutableListOf<Topic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val jsonFile = assets.open("questions.json")
        data = QuizApp.getRepositoryData(jsonFile)

        val topic = intent.getStringExtra("TOPIC")
        val overviewFragment = TopicOverviewFragment.newInstance(topic, data)
        replaceFragment(overviewFragment)

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
