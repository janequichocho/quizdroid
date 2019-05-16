
package edu.us.ischool.janeq97.quizdroid

import android.app.Activity
import android.app.Application

import android.content.Context

import android.util.Log
import android.widget.Toast

import org.json.JSONArray
import java.io.*

import kotlin.concurrent.thread
import android.content.Context.DOWNLOAD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.DownloadManager
import android.app.Service
import android.net.Uri


class QuizApp: Application() {

    companion object {
        var data = mutableListOf<Topic>()

        fun getRepositoryData(jsonFile: InputStream, context: Context, url: String, downloadFreq: Int): MutableList<Topic> {
            data = QuizDatabase().initializeData(jsonFile, context, url, downloadFreq)

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
    fun initializeData(jsonFile: InputStream, context: Context, url: String, downloadFreq: Int): MutableList<Topic>
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
    var url: String? = ""
    var downloadID: Long = 0

    override fun initializeData(
        jsonFile: InputStream,
        context: Context,
        dataUrl: String,
        downloadFreq: Int
    ): MutableList<Topic> {

        var quizData = JSONArray("${readJSONFromAsset(jsonFile)}")
        url = dataUrl
        thread(start = true) {
            Log.i("TEST", "Inside new thread")
            (context as Activity).runOnUiThread(object : Runnable {
                override fun run() {
                    // var result = doInBackground("http://tednewardsandbox.site44.com/questions.json")
                    beginDownload(context, "https://raw.github.com/square/okhttp/master/README.md")
                    // Log.i("TEST", result)
                    Toast.makeText(context, "$url", Toast.LENGTH_LONG).show()
                }
            })
        }


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


    private fun beginDownload(context: Context, url: String) {
        val file = File("../../assets/questions.json")
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Dummy File")// Title of the Download Notification
            .setDescription("Downloading")// Description of the Download Notification
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
            .setAllowedOverRoaming(true)// Set if download is allowed on roaming network
        val downloadManager = getSystemService(context, DOWNLOAD_SERVICE as Class<Service>) as DownloadManager?
        downloadID = downloadManager!!.enqueue(request)// enqueue puts the download request in the queue.
    }
}