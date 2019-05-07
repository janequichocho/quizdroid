package edu.us.ischool.janeq97.quizdroid

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.widget.Button
import java.lang.ClassCastException

class QuizMenuFragment : Fragment() {
    interface QuizMenuFragmentListener {
        fun goToTopicOverview(topic: String)
    }

    var activityCommander: QuizMenuFragmentListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as QuizMenuFragmentListener
        } catch(e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_quiz_menu, container, false)

        view.findViewById<Button>(R.id.topic1_btn).setOnClickListener { btnView -> selectTopic(btnView) }
        view.findViewById<Button>(R.id.topic2_btn).setOnClickListener { btnView -> selectTopic(btnView) }
        view.findViewById<Button>(R.id.topic3_btn).setOnClickListener { btnView -> selectTopic(btnView) }

        return view
    }

    fun selectTopic(view: View) {
        val button: Button = view as Button
        activityCommander?.goToTopicOverview(button.text.toString())
    }


}