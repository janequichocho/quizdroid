package edu.us.ischool.janeq97.quizdroid

import android.content.Context
import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONObject
import android.util.Log
import android.widget.Button

class TopicOverviewFragment : Fragment() {
    var topicMap = mapOf("Math" to 0, "Physics" to 1, "Marvel Super Heroes" to 2)

    companion object {
        const val ARG_NAME = "TOPIC"

        fun newInstance(topic: String, quizData: MutableList<Topic>): TopicOverviewFragment {
            val fragment = TopicOverviewFragment()

            val bundle = Bundle().apply {
                putString(ARG_NAME, topic)
                
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    val quizData: JSONObject = JSONObject("""{
        "Math": {
            "Description": "This quiz covers advanced topics such as addition, subtraction, multiplication, and division.",
            "NumberOfQuestions": "3"
        },
        "Physics": {
            "Description": "Physics is the branch of science concerned with the nature and properties of matter and energy",
            "NumberOfQuestions": "3"
        },
        "MarvelSuperHeroes": {
            "Description" : "This section covers a range of heroes from the Marvel franchise, an American media featuring various super hero films.",
            "NumberOfQuestions": "3"
        }
    }""")

    var activityCommander: TopicOverviewFragmentListener? = null

    interface TopicOverviewFragmentListener {
        fun beginQuiz(topicCodeName: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as TopicOverviewFragmentListener
        } catch(e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_topic_overview, container, false)
        val topic = arguments?.getString("TOPIC")
        setTopicInfo(view, topic as String)

        view.findViewById<Button>(R.id.begin_btn).setOnClickListener { _ -> handleBeginBtnClick(topic) }

        return view
    }

    fun handleBeginBtnClick(topic: String) {
        activityCommander?.beginQuiz(topic)
    }

    fun setTopicInfo(view: View, topicName: String) {
        val topicNameView: TextView = view.findViewById(R.id.topic)
        val descrTextView: TextView = view.findViewById(R.id.topic_descr)
        val numQuestionsTextView: TextView = view.findViewById(R.id.question_count)

        topicNameView.setText(topicName)

        val topicCodeName = topicName.replace("\\s".toRegex(), "")
        val descr = quizData.getJSONObject(topicCodeName).get("Description") as String
        descrTextView.setText(descr)

        var numOfQuestions = quizData.getJSONObject(topicCodeName).get("NumberOfQuestions")
        numQuestionsTextView.setText("Number of questions: $numOfQuestions")
    }




    /*
    var topicName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_topic_overview)

        val topic = getIntent().getStringExtra("TOPIC")
        val topicTextView: TextView = findViewById(R.id.topic)
        topicTextView.setText("$topic Overview")
        topicName = topic

        val topicCodeName = topic.replace("\\s".toRegex(), "")

        val descr = quizData.getJSONObject(topicCodeName).get("Description") as String
        val descrTextView: TextView = findViewById(R.id.topic_descr)
        descrTextView.setText(descr)

        var numOfQuestions = quizData.getJSONObject(topicCodeName).get("NumberOfQuestions")
        val numQuestionsTextView: TextView = findViewById(R.id.question_count)
        numQuestionsTextView.setText("Number of questions: $numOfQuestions")
    }

    fun beginQuiz(view: View) {
        val intent = Intent(this, QuizFragment::class.java)
        intent.putExtra("TOPIC", topicName)
        startActivity(intent)
    }*/

}
