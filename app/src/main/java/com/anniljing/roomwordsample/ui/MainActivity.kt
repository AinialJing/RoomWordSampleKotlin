package com.anniljing.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anniljing.roomwordsample.R
import com.anniljing.roomwordsample.WordsApplication
import com.anniljing.roomwordsample.adapter.WordListAdapter
import com.anniljing.roomwordsample.entity.Word
import com.anniljing.roomwordsample.viewModel.WordViewModel
import com.anniljing.roomwordsample.viewModel.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode=1
    private val wordViewModel:WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv: RecyclerView = findViewById(R.id.recyclerview)
        val adapter = WordListAdapter()
        rv.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager

        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val startActivityForResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                    val word = Word(reply)
                    wordViewModel.insert(word)
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult.launch(intent)
        }
    }
}