package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

// La classe hérite aussi de View.OnClickListener pour permettre
// de passer des OnClickListeners sur les TextViews
class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition : Int = 1
    private var mQuestionsList : ArrayList<Question>? = Constants.getQuestions()
    private var mSelectedOptionPosition : Int = 0
    //
    private var mSelectedTextView : TextView? = null
    private var mCorrectAnswerTextView : TextView? = null
    //
    private var mCanAnswer : Boolean = true
    //
    private var optionOne : TextView? = null
    private var optionTwo : TextView? = null
    private var optionThree : TextView? = null
    private var optionFour : TextView? = null
    private var submitBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        mQuestionsList = Constants.getQuestions()

        optionOne = findViewById<TextView>(R.id.tv_option_one)
        optionTwo = findViewById<TextView>(R.id.tv_option_two)
        optionThree = findViewById<TextView>(R.id.tv_option_three)
        optionFour = findViewById<TextView>(R.id.tv_option_four)
        submitBtn = findViewById<Button>(R.id.btn_submit)

        // Bien mettre les setOnClickListener(this) dans la fonction create directement
        // quand on fait un héritage de View.OnClickListener sur la classe
        optionOne!!.setOnClickListener(this)
        optionTwo!!.setOnClickListener(this)
        optionThree!!.setOnClickListener(this)
        optionFour!!.setOnClickListener(this)
        submitBtn!!.setOnClickListener(this)

        loadQuestion()
    }

    private fun loadQuestion(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = mCurrentPosition
        mCanAnswer = true

        var question: Question? = mQuestionsList!![mCurrentPosition -1]

        // Reset l'apparence de tous les boutons
        setDefaultOptionsView()

        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        tvProgress.text = "$mCurrentPosition/${progressBar.max}"

        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        if (question != null) {
            tvQuestion.text = question.question
        }

        val questionImg = findViewById<ImageView>(R.id.iv_image)
        if (question != null) {
            questionImg.setImageResource(question.image)
        }

        submitBtn!!.setText("SUBMIT")

        // 1
        if (question != null) {
            optionOne!!.text = question.optionOne
        }

        // 2
        if (question != null) {
            optionTwo!!.text = question.optionTwo
        }
        // 3
        if (question != null) {
            optionThree!!.text = question.optionThree
        }
        // 4
        if (question != null) {
            optionFour!!.text = question.optionFour
        }
    }

    private fun setDefaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, optionOne!!)
        options.add(1, optionTwo!!)
        options.add(2, optionThree!!)
        options.add(3, optionFour!!)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            // Sets style of text (italic, bold...)
            option.typeface = Typeface.DEFAULT
            // Pour set un background custom, on passe par ContextCompat.getDrawable
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    // Ne pas oublier le paramètre v: View? de onClick où v = l'objet sélectionné (icin un TextView)
    override fun onClick(v: View?) {
        // Quand l'ID de v
        when(v?.id){
            // Correspond à tv_option_one
                // -> exécuter {}
            R.id.tv_option_one -> {
                if (mCanAnswer){
                    selectedOptionView(findViewById(R.id.tv_option_one), 1)
                }
            }
            R.id.tv_option_two -> {
                if (mCanAnswer){
                    selectedOptionView(findViewById(R.id.tv_option_two), 2)
                }
            }
            R.id.tv_option_three -> {
                if (mCanAnswer){
                    selectedOptionView(findViewById(R.id.tv_option_three), 3)
                }
            }
            R.id.tv_option_four -> {
                if (mCanAnswer){
                    selectedOptionView(findViewById(R.id.tv_option_four), 4)
                }
            }
            R.id.btn_submit -> {
                submitAnswer()
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectionOptionNum: Int){
        // 1. Reset all the buttons
        setDefaultOptionsView()
        //

        // *. Define TextViews for future
        // modifications
        mSelectedTextView = tv
        defineCorrectAnswerTextView()

        // 2. Use the selected button
        mSelectedOptionPosition = selectionOptionNum
        //

        // 3. Set new style for TextView that has been passed
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
        //
    }

    private fun defineCorrectAnswerTextView(){
        var question: Question? = mQuestionsList!![mCurrentPosition -1]
        var mCorrectAnswer : Int = question!!.correctAnswer


        if (mCorrectAnswer == 1){
            mCorrectAnswerTextView = findViewById(R.id.tv_option_one)
        }
        if (mCorrectAnswer == 2){
            mCorrectAnswerTextView = findViewById(R.id.tv_option_two)
        }
        if (mCorrectAnswer == 3){
            mCorrectAnswerTextView = findViewById(R.id.tv_option_three)
        }
        if (mCorrectAnswer == 4){
            mCorrectAnswerTextView = findViewById(R.id.tv_option_four)
        }
    }

    private fun submitAnswer(){
        var question: Question? = mQuestionsList!![mCurrentPosition -1]
        var mCorrectAnswer : Int = question!!.correctAnswer

        if (mSelectedOptionPosition == mCorrectAnswer){
            mCanAnswer = false
            mSelectedTextView!!.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            loadNextQuestion()
        }else if(mSelectedOptionPosition != mCorrectAnswer){
            mCanAnswer = false
            mSelectedTextView?.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
            mCorrectAnswerTextView?.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            mCorrectAnswerTextView?.typeface = Typeface.DEFAULT_BOLD
            mCorrectAnswerTextView?.setTextColor(Color.parseColor("#363A43"))
            loadNextQuestion()
        }
    }

    private fun loadNextQuestion(){
        // Si on a sélectionné aucune option
        // et qu'on appuie sur Submit...
        if (mSelectedOptionPosition == 0){
            // ... on augmente la position dans le quiz de 1
            mCurrentPosition += 1
            // Ensuite...
            when{
                //... si la position actuelle est entre 1 et 9 ...
                mCurrentPosition <= mQuestionsList!!.size -> {
                    //... on charge la prochaine question
                    loadQuestion()
                }
            }
        }
        if(mCurrentPosition >= mQuestionsList!!.size){
            submitBtn!!.text = "FINISH QUIZ"
            mSelectedOptionPosition = 9
        }else{
            submitBtn!!.text = "GO TO NEXT QUESTION"
            // Si on a pas finit le quiz et qu'on clic sur submit
            // on reset la position sélectionnée pour 0, ce qui unlock
            // la condition plus haut
            mSelectedOptionPosition = 0
        }
    }
}