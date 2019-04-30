package edu.us.ischool.janeq97.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONObject
import android.content.Intent
import android.widget.TextView
import org.json.JSONArray

class QuizActivity : AppCompatActivity() {
    val quizData: JSONObject = JSONObject("""{
        |"Math" : {
        |   "NumOfQuestions": "3",
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
        |MarvelSuperHeroes: {
        |   "NumberOfQuestions": "3",
        |   "Questions": [
        |       {
        |           "Question": "Which of these is NOT a Marvel hero?",
        |           "Answer": "Wonder Woman"
        |           "Options": [
        |               "Wonder Woman",
        |               "Ant Man",
        |               "Spiderman",
        |               "Thor"
        |           ]
        |       },
        |       {
        |           "Question": "What is Captain Marvel's real name?"
        |           "Answer": "Carol Danvers",
        |           "Options": {
        |               "Mary Jane",
        |               "Katniss Everdeen",
        |               "Susan Johnson",
        |               "Carol Danvers"
        |           }
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
    var totalQuestions: Int = 0
    var numberCorrect: Int = 0
    var questionIndex: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val topic = getIntent().getStringExtra("TOPIC")

        val topicNameView: TextView = findViewById(R.id.topic)
        topicNameView.setText(topic)

        /*val topicCodeName = topic.replace("\\s".toRegex(), "")
        totalQuestions = quizData.getJSONObject(topicCodeName).getInt("NumberOfQuestions")
        val questions: JSONArray = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")

        val questionView: TextView = findViewById(R.id.question)
        var firstQuestion: JSONObject = questions[0] as JSONObject
        questionView.setText(firstQuestion.get("Question") as String)*/

    }
}
