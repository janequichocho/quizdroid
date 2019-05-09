package edu.us.ischool.janeq97.quizdroid

import android.app.Application
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import com.google.gson.Gson


class QuizApp: Application() {

    companion object {
        var data = mutableListOf<Topic>()
        val quizData: JSONArray = JSONArray("""[
        |{
        |   "Topic": "Math",
        |   "LongDescription": "This quiz covers advanced topics such as addition, subtraction, multiplication, and division.",
        |   "ShortDescription": "Various math topics",
        |   "NumberOfQuestions": "3",
        |   "Questions": [
        |       {
        |           "Question": "What is 2 + 2?",
        |           "Answer": "2",
        |           "Options": [
        |               "2",
        |               "8",
        |               "4",
        |               "13"
        |           ]
        |       },
        |       {
        |           "Question": "What is 5 * 4?",
        |           "Answer": "3",
        |           "Options": [
        |               "9",
        |               "25",
        |               "1",
        |               "20"
        |           ]
        |       },
        |       {
        |           "Question": "What is 14 - 7?",
        |           "Answer": "0",
        |           "Options": [
        |               "7",
        |               "21",
        |               "100",
        |               "4"
        |           ]
        |       }
        |   ]
        |},
        |{
        |   "Topic": "Physics",
        |   "NumberOfQuestions": "3",
        |   "LongDescription": "Physics is the branch of science concerned with the nature and properties of matter and energy",
        |   "ShortDescription": "Various physics topics",
        |   "Questions": [
        |       {
        |           "Question": "What is light?",
        |           "Answer": "1",
        |           "Options": [
        |               "a particle",
        |               "a wave",
        |               "an energy",
        |               "fire"
        |           ]
        |       },
        |       {
        |           "Question": "How fast does light travel?",
        |           "Answer": "0",
        |           "Options": [
        |               "299,792,458 m/s",
        |               "466,467,938 m/s",
        |               "~1 million mph",
        |               "552,375 mph"
        |           ]
        |       },
        |       {
        |           "Question": "What is the unit of measurement for force?",
        |           "Answer": "1",
        |           "Options": [
        |               "grams",
        |               "Newtons",
        |               "Moles",
        |               "Kelvin"
        |           ]
        |       }
        |   ]
        |},
        |{  "Topic": "Marvel Super Heroes",
        |   "NumberOfQuestions": "3",
        |   "LongDescription" : "This section covers a range of heroes from the Marvel franchise, an American media featuring various super hero films.",
        |   "ShortDescription": "Various marvel topics",
        |   "Questions": [
        |       {
        |           "Question": "Which of these is NOT a Marvel hero?",
        |           "Answer": "0",
        |           "Options": [
        |               "Wonder Woman",
        |               "Ant Man",
        |               "Spiderman",
        |               "Thor"
        |           ]
        |       },
        |       {
        |           "Question": "What is Captain Marvel's real name?",
        |           "Answer": "3",
        |           "Options": [
        |               "Mary Jane",
        |               "Katniss Everdeen",
        |               "Susan Johnson",
        |               "Carol Danvers"
        |           ]
        |       },
        |       {
        |           "Question": "What is Thor's weapon?",
        |           "Answer": "0",
        |           "Options": [
        |               "a hammer",
        |               "a sword",
        |               "a whip",
        |               "nunchucks"
        |           ]
        |       }
        |   ]
        |}
    ]""".trimMargin())

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
class Quiz(val question: String, val options: Any, val correctIndex: Int) {}

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

        // val listOfStrings = Gson().fromJson(readJSONFromAsset(jsonFile), mutableListOf<String>().javaClass)
        // Log.i("TEST", listOfStrings[0])
        var quizData = JSONArray("${readJSONFromAsset(jsonFile)}")

        var topics = mutableListOf<Topic>()
        // Create topics
        for (i in 0..(quizData.length() - 1)) {
            val topicName = quizData.getJSONObject(i).get("Topic") as String
            val shortDescription = quizData.getJSONObject(i).get("ShortDescription") as String
            val longDescription = quizData.getJSONObject(i).get("LongDescription") as String
            val quizQuestions = quizData.getJSONObject(i).getJSONArray("Questions")
            val formattedQuestions = mutableListOf<Quiz>()
            for (i in 0..(quizQuestions.length() - 1)) {
                val question = quizQuestions.getJSONObject(i).get("Question") as String
                val answer = quizQuestions.getJSONObject(i).getInt("Answer")
                val options = quizQuestions.getJSONObject(i).get("Options")

                formattedQuestions.add(Quiz(question, options, answer))
            }
            topics.add(Topic(topicName, shortDescription, longDescription, formattedQuestions))
        }
        return topics
    }
}