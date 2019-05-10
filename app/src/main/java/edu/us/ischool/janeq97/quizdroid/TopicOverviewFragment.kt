package edu.us.ischool.janeq97.quizdroid

import android.content.Context
import android.os.Bundle
import android.view.View
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button

class TopicOverviewFragment : Fragment() {

    companion object {
        var topicMap = mapOf("Math" to 0, "Physics" to 1, "Marvel Super Heroes" to 2)

        fun newInstance(topic: String, quizData: MutableList<Topic>): TopicOverviewFragment {
            val fragment = TopicOverviewFragment()

            val bundle = Bundle().apply {
                putString("TOPIC", topic)

                var description = quizData[topicMap.get(topic) as Int].longDescr
                var numOfQuestions = quizData[topicMap.get(topic) as Int].quiz.size
                putString("DESCRIPTION", description)
                putInt("NUMBER_OF_QUESTIONS", numOfQuestions)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

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
        val descr = arguments?.getString("DESCRIPTION")
        val numOfQuestions = arguments?.getInt("NUMBER_OF_QUESTIONS")
        setTopicInfo(view, topic as String, descr as String, numOfQuestions as Int)

        view.findViewById<Button>(R.id.begin_btn).setOnClickListener { _ -> handleBeginBtnClick(topic) }

        return view
    }

    fun handleBeginBtnClick(topic: String) {
        activityCommander?.beginQuiz(topic)
    }

    fun setTopicInfo(view: View, topicName: String, descr: String, numOfQuestions: Int) {
        val topicNameView: TextView = view.findViewById(R.id.topic)
        val descrTextView: TextView = view.findViewById(R.id.topic_descr)
        val numQuestionsTextView: TextView = view.findViewById(R.id.question_count)

        topicNameView.setText(topicName)

        descrTextView.setText(descr)

        numQuestionsTextView.setText("Number of questions: $numOfQuestions")
    }
}
