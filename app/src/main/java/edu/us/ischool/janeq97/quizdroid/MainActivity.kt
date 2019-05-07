package edu.us.ischool.janeq97.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.support.v4.app.Fragment

class MainActivity : AppCompatActivity(), QuizMenuFragment.QuizMenuFragmentListener,
    TopicOverviewFragment.TopicOverviewFragmentListener, QuizFragment.QuizFragmentListener,
    AnswerFragment.AnswerFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(QuizMenuFragment())
    }

    override fun beginQuiz(topic: String) {
        val quizFragment = QuizFragment.newInstance(topic, 0, 0)
        replaceFragment(quizFragment)
    }

    override fun goToTopicOverview(topic: String) {
        var overviewFragment = TopicOverviewFragment.newInstance(topic)
        replaceFragment(overviewFragment)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
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
            val quizSelectionFragment = QuizMenuFragment()
            replaceFragment(quizSelectionFragment)
        }
    }
}
