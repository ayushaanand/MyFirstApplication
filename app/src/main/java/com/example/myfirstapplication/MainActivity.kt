package com.example.myfirstapplication

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.materialswitch.MaterialSwitch

class MainActivity : AppCompatActivity() {
    private lateinit var loginName: EditText
    private lateinit var keyInput: EditText
    private lateinit var compResult: TextView
    private lateinit var themeToggle: MaterialSwitch
    private lateinit var darkOverlay: View
    private lateinit var loginNameLabel: TextView
    private lateinit var passKeyLabel: TextView

    // Mock Database
    private val userDatabase = mapOf(
        "admin" to "1234",
        "ayush" to "password",
        "android" to "studio",
        "user" to "key123"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        loginName = findViewById(R.id.loginName)
        keyInput = findViewById(R.id.keyInput)
        compResult = findViewById(R.id.compResult)
        themeToggle = findViewById(R.id.themeToggle)
        darkOverlay = findViewById(R.id.darkBackgroundOverlay)
        loginNameLabel = findViewById(R.id.loginNameLabel)
        passKeyLabel = findViewById(R.id.passKey)

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                performAuthentication()
            }
        }

        loginName.addTextChangedListener(watcher)
        keyInput.addTextChangedListener(watcher)

        themeToggle.setOnCheckedChangeListener { _, isChecked ->
            animateThemeChange(isChecked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainUI)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun animateThemeChange(isDark: Boolean) {
        val duration = 500L
        val targetAlpha = if (isDark) 1f else 0f
        
        // Text colors for Light/Dark modes
        val startColor = if (isDark) Color.parseColor("#333333") else Color.parseColor("#EEEEEE")
        val endColor = if (isDark) Color.parseColor("#EEEEEE") else Color.parseColor("#333333")

        // 1. Smoothly fade the dark background overlay
        darkOverlay.animate().alpha(targetAlpha).setDuration(duration).start()

        // 2. Smoothly transition text colors
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        colorAnimation.duration = duration
        colorAnimation.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            loginNameLabel.setTextColor(color)
            passKeyLabel.setTextColor(color)
            loginName.setTextColor(color)
            keyInput.setTextColor(color)
        }
        colorAnimation.start()
        
        // 3. Smoothly transition result text color
        val resultEndColor = if (isDark) Color.parseColor("#BDC3C7") else Color.parseColor("#2C3E50")
        val resultStartColor = if (isDark) Color.parseColor("#2C3E50") else Color.parseColor("#BDC3C7")
        
        val resultColorAnim = ValueAnimator.ofObject(ArgbEvaluator(), resultStartColor, resultEndColor)
        resultColorAnim.duration = duration
        resultColorAnim.addUpdateListener { animator ->
            compResult.setTextColor(animator.animatedValue as Int)
        }
        resultColorAnim.start()
    }

    private fun performAuthentication() {
        val name = loginName.text.toString().trim()
        val key = keyInput.text.toString()

        if (name.isEmpty()) {
            compResult.text = "no name"
            return
        }

        val correctKey = userDatabase.entries.find { 
            it.key.equals(name, ignoreCase = true) 
        }?.value

        if (correctKey == null) {
            compResult.text = "name not found"
        } else {
            if (correctKey == key) {
                compResult.text = "key matched"
            } else {
                compResult.text = "wrong key"
            }
        }
    }
}