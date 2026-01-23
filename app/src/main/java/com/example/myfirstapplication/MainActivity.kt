package com.example.myfirstapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var loginName : EditText
    private lateinit var keyInput : EditText
    private lateinit var compResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        loginName = findViewById(R.id.loginName)
        keyInput = findViewById(R.id.keyInput)
        compResult = findViewById(R.id.compResult)

        loginName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "Received Text : $s")
                if (isStrMatch()) compResult.text = "The string are matching."
                else compResult.text = "The string are not matching."
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}

        })

        keyInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "Received Key : $s")
                if(isStrMatch()) compResult.text = "The string are matching."
                else compResult.text = "The string are not matching."
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}

        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginName)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun isStrMatch() : Boolean {
        var str1 : String? = loginName.text.toString()
        var str2 : String? = keyInput.text.toString()

        if(str1 == str2) return true
        else return false
    }

}