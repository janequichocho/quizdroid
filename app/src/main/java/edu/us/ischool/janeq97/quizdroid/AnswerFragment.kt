package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.content.Context

class AnswerFragment : Fragment() {

    // (result: String, yourAnswer: String, answer: String,
    //                     questionIndex: Int, numCorrect: Int, totalQuestions: Int,
    //                     topic: String)

    companion object {

        fun newInstance(result: String, yourAnswer: String, answer: String,
                        questionIndex: Int, numCorrect: Int, totalQuestions: Int,
                        topic: String): AnswerFragment {
            val fragment = AnswerFragment()

            val bundle = Bundle().apply {
                putString("TOPIC", topic)
                putString("YOUR_ANSWER", yourAnswer)
                putString("ANSWER", answer)
                putInt("QUESTION_INDEX", questionIndex)
                putInt("NUM_CORRECT", numCorrect)
                putInt("TOTAL_QUESTIONS", totalQuestions)
                putString("RESULT", result)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    interface AnswerFragmentListener{
        fun continueQuiz(topic: String, numCorrect: Int, questionIndex: Int, totalQuestions: Int)
    }

    var activityCommander: AnswerFragmentListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as AnswerFragmentListener
        } catch(e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_answer, container, false)
        var topic = arguments!!.getString("TOPIC")
        val yourAnswer = arguments!!.getString("YOUR_ANSWER")
        val answer = arguments!!.getString("ANSWER")
        var questionIndex = arguments!!.getInt("QUESTION_INDEX")
        var numCorrect = arguments!!.getInt("NUM_CORRECT")
        var totalQuestions = arguments!!.getInt("TOTAL_QUESTIONS")
        var result = arguments!!.getString("RESULT")

        val resultView: TextView = view.findViewById(R.id.result)
        resultView.setText(result)

        val correctAnswerView: TextView = view.findViewById(R.id.correct_answer)
        correctAnswerView.setText("Correct Answer: $answer")

        val yourAnswerView: TextView = view.findViewById(R.id.your_answer)
        yourAnswerView.setText("Your answer: $yourAnswer")

        val numberCorrectView: TextView = view.findViewById(R.id.number_correct)
        numberCorrectView.setText("You have $numCorrect out of $totalQuestions correct.")

        val nextBtn: Button = view.findViewById(R.id.next)
        if (questionIndex == totalQuestions - 1) {
            nextBtn.setText("Finish")
        }
        nextBtn.setOnClickListener { _ -> handleNext(topic, numCorrect, questionIndex, totalQuestions) }

        return view
    }

    fun handleNext(topic: String, numberCorrect: Int, questionIndex: Int,
        numberOfQuestions: Int) {
        activityCommander?.continueQuiz(topic, numberCorrect, questionIndex + 1, numberOfQuestions)

    }
}
