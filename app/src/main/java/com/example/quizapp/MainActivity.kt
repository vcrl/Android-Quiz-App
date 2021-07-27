package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val btn_start = findViewById<Button>(R.id.btn_start)
        val et_name = findViewById<EditText>(R.id.et_name)

        btn_start.setOnClickListener {
            if (et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter a name to continue", Toast.LENGTH_SHORT).show()
            }else{
                // Charge nouvelle Activity (nouvelle fenêtre)
                val intent = Intent(this, QuizQuestionActivity::class.java)
                // On lance la nouvelle activity
                startActivity(intent)
                // Finish ferme l'activity pour qu'elle run pas en background
                finish()
            }
        }
    }
}