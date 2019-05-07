package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

class TopicOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_topic_overview, container, false)
        return view
    }

    /*val quizData: JSONObject = JSONObject("""{
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
