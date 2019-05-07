package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AnswerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_answer, container, false)
        return view
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_answer)

        val numberCorrect = getIntent().getIntExtra("NUMBER_CORRECT", 0)
        val numberOfQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0)
        val questionIndex = getIntent().getIntExtra("QUESTION_INDEX", 0)
        val result = getIntent().getStringExtra("RESULT")
        val correctAnswer = getIntent().getStringExtra("CORRECT_ANSWER")
        val yourAnswer = getIntent().getStringExtra("YOUR_ANSWER")

        val resultView: TextView = findViewById(R.id.result)
        resultView.setText(result)

        val correctAnswerView: TextView = findViewById(R.id.correct_answer)
        correctAnswerView.setText("Correct Answer: $correctAnswer")

        val yourAnswerView: TextView = findViewById(R.id.your_answer)
        yourAnswerView.setText("Your answer: $yourAnswer")

        val numberCorrectView: TextView = findViewById(R.id.number_correct)
        numberCorrectView.setText("You have $numberCorrect out of $numberOfQuestions correct.")

        if (questionIndex == numberOfQuestions - 1) {
            val nextBtn: Button = findViewById(R.id.next)
            nextBtn.setText("Finish")
        }
    }

    fun handleNext(view: View) {
        val topic = getIntent().getStringExtra("TOPIC")
        val numberCorrect = getIntent().getIntExtra("NUMBER_CORRECT", 0)
        val questionIndex = getIntent().getIntExtra("QUESTION_INDEX", 0) + 1
        val numberOfQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0)

        if (questionIndex < numberOfQuestions) {
            val intent = Intent(this, QuizFragment::class.java)
            intent.putExtra("TOPIC", topic)
            intent.putExtra("NUMBER_CORRECT", numberCorrect)
            intent.putExtra("QUESTION_INDEX", questionIndex)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }*/
}
