package pl.edu.pg.cloudlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pg.cloudlib.databinding.ActivityLibElementBinding
import pl.edu.pg.cloudlib.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}