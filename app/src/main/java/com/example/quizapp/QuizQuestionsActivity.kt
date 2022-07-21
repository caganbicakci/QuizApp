package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

const val QUESTION_LIST = "QuestionList";
const val EACH_QUESTIONS = "Questions";
const val CURRENT_QUESTION = "CurrentQuestion"

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition: Int = 0
    private var questionsList: ArrayList<Question>? = null
    private var selectedAnswer: Int = -1
    private var userName: String? = null
    private var correctAnswers: Int = 0

    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionsList = Constants.getQuestions()
        Log.i(QUESTION_LIST, "QuestionsList size is ${questionsList?.size}")

//        for (q in questionsList!!) {
//            Log.e(EACH_QUESTIONS, q.question)
//        }

        setQuestion()

        binding.optionA.setOnClickListener(this)
        binding.optionB.setOnClickListener(this)
        binding.optionC.setOnClickListener(this)
        binding.optionD.setOnClickListener(this)
        binding.submitBtn.setOnClickListener(this)

        userName = intent.getStringExtra(Constants.USER_NAME)

    }

    private fun setQuestion() {

        defaultOptionViews()

        if (currentPosition == questionsList!!.size) {
            binding.submitBtn.text = getString(R.string.finish_quiz)
        } else {
            binding.submitBtn.text = getString(R.string.submit)
        }

        val question: Question = questionsList!![currentPosition]

        binding.progress.progress = currentPosition + 1
        binding.progress.max = questionsList!!.size

        binding.progressTextView.text =
            getString(R.string.progress_ratio, currentPosition + 1, questionsList!!.size)

        //binding.progressTextView.text = "$currentPosition/${questionsList.size}"

        binding.questionImageView.setImageResource(question.image)
        binding.questionTextView.text = question.question
        binding.optionA.text = question.optionA
        binding.optionB.text = question.optionB
        binding.optionC.text = question.optionC
        binding.optionD.text = question.optionD

    }

    private fun defaultOptionViews() {

        val options = ArrayList<TextView>()
        binding.optionA.let {
            options.add(0, it)
        }
        binding.optionB.let {
            options.add(1, it)
        }
        binding.optionC.let {
            options.add(2, it)
        }
        binding.optionD.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_default_option
            )
        }

    }

    private fun selectedOptionView(tw: TextView, selectedOptionNum: Int) {

        defaultOptionViews()

        selectedAnswer = selectedOptionNum

        tw.setTextColor(Color.parseColor("#363643"))
        tw.setTypeface(tw.typeface, Typeface.BOLD)

        tw.background = ContextCompat.getDrawable(
            this,
            R.drawable.bg_selected_option
        )

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.optionA -> {
                selectedOptionView(binding.optionA, 0)
            }
            R.id.optionB -> {
                selectedOptionView(binding.optionB, 1)
            }
            R.id.optionC -> {
                selectedOptionView(binding.optionC, 2)
            }
            R.id.optionD -> {
                selectedOptionView(binding.optionD, 3)
            }
            R.id.submitBtn -> {

                if (selectedAnswer == -1) {
                    currentPosition++

                    when {
                        currentPosition < questionsList?.size!! -> {
                            setQuestion()
                        }
                        else -> {
                            val resultIntent = Intent(this, QuizResultActivity::class.java)
                            resultIntent.putExtra(Constants.USER_NAME, userName)
                            resultIntent.putExtra(Constants.CORRECT_ANSWERS_COUNT, correctAnswers)
                            resultIntent.putExtra(Constants.TOTAL_QUESTIONS_COUNT, questionsList?.size)
                            startActivity(resultIntent)
                        }
                    }
                } else {
                    val question = questionsList?.get(currentPosition)
                    if (selectedAnswer != question?.correctAnswer) {
                        answerView(selectedAnswer, R.drawable.bg_wrong_answer)
                    }else{
                        correctAnswers++
                    }
                    answerView(question!!.correctAnswer, R.drawable.bg_correct_answer)
                }

                if (currentPosition + 1 == questionsList?.size) {
                    binding.submitBtn.text = getString(R.string.finish_quiz)
                } else if (selectedAnswer != -1) {
                    binding.submitBtn.text = getString(R.string.next_question)
                } else {
                    binding.submitBtn.text = getString(R.string.submit)
                }

//                Log.i(CURRENT_QUESTION, currentPosition.toString())
//                Log.i(CURRENT_QUESTION, "*******************")
//                Log.i(CURRENT_QUESTION, questionsList!![currentPosition].optionA)

                selectedAnswer = -1
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            0 -> {
                binding.optionA.background = ContextCompat.getDrawable(this, drawableView)
            }
            1 -> {
                binding.optionB.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                binding.optionC.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                binding.optionD.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}