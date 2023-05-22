package pl.edu.pg.cloudlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pg.cloudlib.databinding.ActivityLibElementBinding

class LibElementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibElementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibElementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = intent.getStringExtra("message") ?: "Error: no message arrived"

    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finish()
    }
}