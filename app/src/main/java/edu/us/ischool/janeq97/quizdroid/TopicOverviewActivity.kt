package edu.us.ischool.janeq97.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.json.JSONObject
import android.content.Intent

class TopicOverviewActivity : AppCompatActivity() {
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

    var topicName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

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
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("TOPIC", topicName)
        startActivity(intent)
    }

}
