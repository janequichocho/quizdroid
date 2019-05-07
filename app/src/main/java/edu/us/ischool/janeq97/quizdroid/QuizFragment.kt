package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import org.json.JSONObject
import android.widget.RadioButton
import android.widget.TextView
import android.widget.RadioGroup
import android.widget.Button
import android.content.Context

class QuizFragment : Fragment() {
    val quizData: JSONObject = JSONObject("""{
        |"Math": {
        |   "NumberOfQuestions": "3",
        |   "Questions": [
        |       {
        |           "Question": "What is 2 + 2?",
        |           "Answer": "4",
        |           "Options": [
        |               "2",
        |               "8",
        |               "4",
        |               "13"
        |           ]
        |       },
        |       {
        |           "Question": "What is 5 * 4?",
        |           "Answer": "20",
        |           "Options": [
        |               "9",
        |               "25",
        |               "1",
        |               "20"
        |           ]
        |       },
        |       {
        |           "Question": "What is 14 - 7?",
        |           "Answer": "7",
        |           "Options": [
        |               "7",
        |               "21",
        |               "100",
        |               "4"
        |           ]
        |       }
        |   ]
        |},
        |"Physics": {
        |   "NumberOfQuestions": "3",
        |   "Questions": [
        |       {
        |           "Question": "What is light?",
        |           "Answer": "a wave",
        |           "Options": [
        |               "a particle",
        |               "a wave",
        |               "an energy",
        |               "fire"
        |           ]
        |       },
        |       {
        |           "Question": "How fast does light travel?",
        |           "Answer": "299,792,458 m/s",
        |           "Options": [
        |               "299,792,458 m/s",
        |               "466,467,938 m/s",
        |               "~1 million mph",
        |               "552,375 mph"
        |           ]
        |       },
        |       {
        |           "Question": "What is the unit of measurement for force?",
        |           "Answer": "Newtons",
        |           "Options": [
        |               "grams",
        |               "Newtons",
        |               "Moles",
        |               "Kelvin"
        |           ]
        |       }
        |   ]
        |},
        |"MarvelSuperHeroes": {
        |   "NumberOfQuestions": "3",
        |   "Questions": [
        |       {
        |           "Question": "Which of these is NOT a Marvel hero?",
        |           "Answer": "Wonder Woman",
        |           "Options": [
        |               "Wonder Woman",
        |               "Ant Man",
        |               "Spiderman",
        |               "Thor"
        |           ]
        |       },
        |       {
        |           "Question": "What is Captain Marvel's real name?",
        |           "Answer": "Carol Danvers",
        |           "Options": [
        |               "Mary Jane",
        |               "Katniss Everdeen",
        |               "Susan Johnson",
        |               "Carol Danvers"
        |           ]
        |       },
        |       {
        |           "Question": "What is Thor's weapon?",
        |           "Answer": "a hammer",
        |           "Options": [
        |               "a hammer",
        |               "a sword",
        |               "a whip",
        |               "nunchucks"
        |           ]
        |       }
        |   ]
        |}
    }""".trimMargin())

    companion object {

        fun newInstance(topic: String, index: Int, numCorrect: Int): QuizFragment {
            val fragment = QuizFragment()

            val bundle = Bundle().apply {
                putString("TOPIC", topic)
                putInt("QUESTION_INDEX", index)
                putInt("NUM_CORRECT", numCorrect)
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
        val topicCodeName = topic.replace("\\s".toRegex(), "")
        var currentAnswer = ""

        displayQuestion(topicCodeName, view, questionIndex)
        populateOptions(topicCodeName, view, questionIndex)

        val options: RadioGroup = view.findViewById(R.id.radio_group)
        options.setOnCheckedChangeListener {_, checkedId ->
            val checked: RadioButton = view.findViewById(checkedId)
            currentAnswer = checked.text.toString()
        }

        view.findViewById<Button>(R.id.submit).setOnClickListener {
                _ -> handleSubmit(currentAnswer, topicCodeName, questionIndex, topic)
        }

        return view
    }

    fun populateOptions(topicCodeName: String, view: View, questionIndex: Int) {
        val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
        val options = questions.getJSONObject(questionIndex).getJSONArray("Options")

        val option1: RadioButton = view.findViewById(R.id.option1)
        val option2: RadioButton = view.findViewById(R.id.option2)
        val option3: RadioButton = view.findViewById(R.id.option3)
        val option4: RadioButton = view.findViewById(R.id.option4)

        option1.setText(options[0].toString())
        option2.setText(options[1].toString())
        option3.setText(options[2].toString())
        option4.setText(options[3].toString())
    }

    fun displayQuestion(topicCodeName: String, view: View, questionIndex: Int) {
        val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
        val questionView: TextView = view.findViewById(R.id.question)
        val question = questions.getJSONObject(questionIndex).get("Question")
        questionView.setText("$question")
    }

    fun handleSubmit(currentAnswer: String, topicCodeName: String, questionIndex: Int, topic: String) {
        if (currentAnswer != "") {
            val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
            val answer = questions.getJSONObject(questionIndex).get("Answer") as String
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
