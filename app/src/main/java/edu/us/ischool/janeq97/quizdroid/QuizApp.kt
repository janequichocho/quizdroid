package edu.us.ischool.janeq97.quizdroid

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.security.AccessController.getContext
import kotlin.concurrent.thread


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
    fun initializeData(
        jsonFile: InputStream,
        context: Context,
        dataUrl: String,
        downloadFreq: Int
    ): MutableList<Topic>
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

class QuizDatabase: TopicRepository, AppCompatActivity() {
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

                    setUpVolleyFetching("https://raw.github.com/square/okhttp/master/README.md", context)
                    // Log.i("TEST", result)

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

    private fun setUpVolleyFetching(url: String, context: Context) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val dataUrl = url

        // Request a string response from the provided URL.
        Log.i("TEST", "Download is starting")
        val stringRequest = StringRequest(
            Request.Method.GET, dataUrl,
            Response.Listener<String> { response ->
                Log.i("TEST", "Finished downloading")
                // Display the first 500 characters of the response string.
                val jsonData = response
                File("../../assets/questions.json").writeText(jsonData)
            },
            Response.ErrorListener {
                Log.i("ERROR", "there was an error")
            }
        )
        queue.add(stringRequest)

    }

    /*private fun beginDownload(context: Context, url: String) {
//        val file = File("../../assets/questions.json")
        val sdcard : File = Environment.getExternalStorageDirectory()

        val jsonFile : File = File(sdcard, "questions.json")

        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Dummy File")// Title of the Download Notification
            .setDescription("Downloading")// Description of the Download Notification
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(jsonFile))// Uri of the destination file
            .setAllowedOverRoaming(true)// Set if download is allowed on roaming network
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager!!.enqueue(request)// enqueue puts the download request in the queue.
    }*/

}