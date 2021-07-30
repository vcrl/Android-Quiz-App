package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class EndScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(Constants.USERNAME)
        val tvName = findViewById<TextView>(R.id.tv_username)
        tvName.text = username.toString()

        val tvAnswers = findViewById<TextView>(R.id.tv_score)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val questionsNumber = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        tvAnswers.text = "Your score is $correctAnswers out of $questionsNumber"

        val btnFinish = findViewById<Button>(R.id.btn_finish)
        btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}