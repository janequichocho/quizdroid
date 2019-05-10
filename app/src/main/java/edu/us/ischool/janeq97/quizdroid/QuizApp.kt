package edu.us.ischool.janeq97.quizdroid

import android.app.Application
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream


class QuizApp: Application() {

    companion object {
        var data = mutableListOf<Topic>()

        fun getRepositoryData(jsonFile: InputStream): MutableList<Topic> {
            data = QuizDatabase().initializeData(jsonFile)

            return data
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("TEST", "QuizApp created")
    }

}

class Topic(val title: String, val shortDescr: String, val longDescr: String, val quiz: List<Quiz>) {}
class Quiz(val question: String, val options: ArrayList<String>, val correctIndex: Int) {}

interface TopicRepository {
    fun initializeData(jsonFile: InputStream): MutableList<Topic>
}

fun readJSONFromAsset(jsonFile: InputStream): String? {
    var json: String? = ""
    try {
        json = jsonFile.bufferedReader().use{it.readText()}

    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

class QuizDatabase: TopicRepository {
    override fun initializeData(jsonFile: InputStream): MutableList<Topic> {

        var quizData = JSONArray("${readJSONFromAsset(jsonFile)}")

        var topics = mutableListOf<Topic>()
        // Create topics
        for (i in 0..(quizData.length() - 1)) {
            val topicName = quizData.getJSONObject(i).get("topic") as String
            val shortDescription = ""
            val longDescription = quizData.getJSONObject(i).get("desc") as String
            val quizQuestions = quizData.getJSONObject(i).getJSONArray("questions")
            val formattedQuestions = mutableListOf<Quiz>()
            for (i in 0..(quizQuestions.length() - 1)) {
                val question = quizQuestions.getJSONObject(i).get("text") as String
                val answer = quizQuestions.getJSONObject(i).getInt("answer")
                val options = quizQuestions.getJSONObject(i).getJSONArray("answers")
                val optionsArray = arrayListOf<String>()
                for (i in 0..(options.length() - 1)) {
                    optionsArray.add(options.getString(i))
                }
                formattedQuestions.add(Quiz(question, optionsArray, answer))
            }
            topics.add(Topic(topicName, shortDescription, longDescription, formattedQuestions))
        }
        return topics
    }
}