package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.RadioGroup
import android.widget.Button
import android.content.Context

class QuizFragment : Fragment() {

    companion object {
        var topicMap = mapOf("Math" to 0, "Physics" to 1, "Marvel Super Heroes" to 2)

        fun newInstance(topic: String, index: Int, numCorrect: Int, quizData: MutableList<Topic>): QuizFragment {
            val fragment = QuizFragment()

            val bundle = Bundle().apply {
                putString("TOPIC", topic)
                putInt("QUESTION_INDEX", index)
                putInt("NUM_CORRECT", numCorrect)
                putStringArrayList("OPTIONS", quizData[(topicMap.get(topic) as Int)].quiz[index].options)
                putString("QUESTION", quizData[(topicMap.get(topic) as Int)].quiz[index].question)
                putInt("CORRECT_INDEX", quizData[(topicMap.get(topic) as Int)].quiz[index].correctIndex)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    var activityCommander: QuizFragmentListener? = null

    interface QuizFragmentListener {
        fun submitAnswer(result: String, yourAnswer: String, answer: String,
                         questionIndex: Int, numCorrect: Int, totalQuestions: Int,
                         topic: String)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as QuizFragmentListener
        } catch(e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }


    var numCorrect = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_quiz, container, false)
        var topic = arguments!!.getString("TOPIC")

        val questionIndex = arguments!!.getInt("QUESTION_INDEX", 0)
        numCorrect = arguments!!.getInt("NUM_CORRECT", 0)

        var currentAnswer = ""

        val question = arguments!!.getString("QUESTION")
        displayQuestion(view, question)

        val optionsList = arguments!!.getStringArrayList("OPTIONS")
        populateOptions(view, optionsList)

        val options: RadioGroup = view.findViewById(R.id.radio_group)
        options.setOnCheckedChangeListener {_, checkedId ->
            val checked: RadioButton = view.findViewById(checkedId)
            currentAnswer = checked.text.toString()
        }

        val correctIndex = arguments!!.getInt("CORRECT_INDEX")

        view.findViewById<Button>(R.id.submit).setOnClickListener {
                _ -> handleSubmit(currentAnswer, questionIndex, topic, optionsList[correctIndex])
        }

        return view
    }

    fun populateOptions(view: View, options: ArrayList<String>) {


        val option1: RadioButton = view.findViewById(R.id.option1)
        val option2: RadioButton = view.findViewById(R.id.option2)
        val option3: RadioButton = view.findViewById(R.id.option3)
        val option4: RadioButton = view.findViewById(R.id.option4)

        option1.setText(options[0])
        option2.setText(options[1])
        option3.setText(options[2])
        option4.setText(options[3])
    }

    fun displayQuestion(view: View, question: String) {
        val questionView: TextView = view.findViewById(R.id.question)
        questionView.setText("$question")
    }

    fun handleSubmit(currentAnswer: String, questionIndex: Int, topic: String, answer: String) {
        if (currentAnswer != "") {

            var result = ""
            if (answer == currentAnswer) {
                numCorrect = numCorrect + 1
                result = "CORRECT"
            } else {
                result = "INCORRECT"
            }

            activityCommander?.submitAnswer(result, currentAnswer, answer, questionIndex,
                numCorrect, 3, topic)
        }
    }
}
