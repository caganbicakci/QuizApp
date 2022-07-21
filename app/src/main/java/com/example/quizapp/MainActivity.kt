package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startQuizBtn : Button = binding.startQuizBtn
        val nameEditText : EditText = binding.nameEditText


        startQuizBtn.setOnClickListener(){
            if(nameEditText.text.isNullOrBlank()){
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }else{
                val questionsIntent = Intent(this, QuizQuestionsActivity::class.java)
                questionsIntent.putExtra(Constants.USER_NAME, binding.nameEditText.text.toString())
                startActivity(questionsIntent)
                finish()

            }
        }
    }
}