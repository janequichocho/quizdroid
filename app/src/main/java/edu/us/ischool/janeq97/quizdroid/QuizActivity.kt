package edu.us.ischool.janeq97.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class QuizActivity : AppCompatActivity(), TopicOverviewFragment.TopicOverviewFragmentListener,
    QuizFragment.QuizFragmentListener, AnswerFragment.AnswerFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val topic = intent.getStringExtra("TOPIC")
        val overviewFragment = TopicOverviewFragment.newInstance(topic)
        replaceFragment(overviewFragment)
    }


    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }


    override fun beginQuiz(topic: String) {
        val quizFragment = QuizFragment.newInstance(topic, 0, 0)
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
                numCorrect)
            replaceFragment(quizFragment)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
