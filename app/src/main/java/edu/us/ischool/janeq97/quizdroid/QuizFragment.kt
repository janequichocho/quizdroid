package edu.us.ischool.janeq97.quizdroid

import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

class QuizFragment : Fragment() {
    /*val quizData: JSONObject = JSONObject("""{
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

    var totalQuestions: Int = 3
    var numberCorrect: Int = 0
    var questionIndex: Int = 0
    var currentAnswer: String = ""
    var topicCodeName: String = ""*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_quiz, container, false)
        return view
    }

    /*constructor(parcel: Parcel) : this() {
        totalQuestions = parcel.readInt()
        numberCorrect = parcel.readInt()
        questionIndex = parcel.readInt()
        currentAnswer = parcel.readString()
        topicCodeName = parcel.readString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_quiz)

        val topic = getIntent().getStringExtra("TOPIC")
        val topicNameView: TextView = findViewById(R.id.topic)
        topicNameView.setText(topic)

        topicCodeName = topic.replace("\\s".toRegex(), "")

        numberCorrect = getIntent().getIntExtra("NUMBER_CORRECT", 0)
        questionIndex = getIntent().getIntExtra("QUESTION_INDEX", 0)

        displayQuestion()
        populateOptions()

        Log.i("INDEX", "$questionIndex")

        val options: RadioGroup = findViewById(R.id.radio_group)
        options.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener({group, checkedId ->
            val checked: RadioButton = findViewById(checkedId)
            currentAnswer = checked.text.toString()
        }))
    }

    fun populateOptions() {
        val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
        val options = questions.getJSONObject(questionIndex).getJSONArray("Options")

        val option1: RadioButton = findViewById(R.id.option1)
        val option2: RadioButton = findViewById(R.id.option2)
        val option3: RadioButton = findViewById(R.id.option3)
        val option4: RadioButton = findViewById(R.id.option4)

        option1.setText(options[0].toString())
        option2.setText(options[1].toString())
        option3.setText(options[2].toString())
        option4.setText(options[3].toString())
    }

    fun displayQuestion() {
        val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
        val questionView: TextView = findViewById(R.id.question)
        val question = questions.getJSONObject(questionIndex).get("Question")
        questionView.setText("$question")
    }

    fun handleSubmit(view: View) {
        if (currentAnswer != "") {
            val topic = getIntent().getStringExtra("TOPIC")
            val questions = quizData.getJSONObject(topicCodeName).getJSONArray("Questions")
            val answer = questions.getJSONObject(questionIndex).get("Answer")
            var isCorrect = false
            if (answer == currentAnswer) {
                numberCorrect = numberCorrect + 1
                isCorrect = true
            }
            val intent = Intent(this, AnswerFragment::class.java)
            intent.putExtra("QUESTION_INDEX", questionIndex)
            intent.putExtra("NUMBER_CORRECT", numberCorrect)
            if (isCorrect) {
                intent.putExtra("RESULT", "Correct!")
            } else {
                intent.putExtra("RESULT", "Incorrect!")
            }
            intent.putExtra("TOTAL_QUESTIONS", totalQuestions)
            intent.putExtra("TOPIC", topic)

            intent.putExtra("CORRECT_ANSWER", answer.toString())
            intent.putExtra("YOUR_ANSWER", currentAnswer)
            startActivity(intent)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(totalQuestions)
        parcel.writeInt(numberCorrect)
        parcel.writeInt(questionIndex)
        parcel.writeString(currentAnswer)
        parcel.writeString(topicCodeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizFragment> {
        override fun createFromParcel(parcel: Parcel): QuizFragment {
            return QuizFragment(parcel)
        }

        override fun newArray(size: Int): Array<QuizFragment?> {
            return arrayOfNulls(size)
        }
    }*/
}
