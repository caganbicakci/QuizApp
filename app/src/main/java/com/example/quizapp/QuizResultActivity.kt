package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding : ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswerCount = intent.getIntExtra(Constants.CORRECT_ANSWERS_COUNT, 0)
        val totalQuestionCount = intent.getIntExtra(Constants.TOTAL_QUESTIONS_COUNT, 0)

        binding.userNameTextView.text = getString(R.string.username, userName)
        binding.scoreTextView.text = getString(R.string.score, correctAnswerCount, totalQuestionCount)

//        binding.userNameTextView.text = userName
//        binding.scoreTextView.text = "Your Score is $correctAnswerCount out of $totalQuestionCount."

        binding.finishBtn.setOnClickListener {
            startActivity(Intent(this@QuizResultActivity, MainActivity::class.java))
        }

    }
}