package com.example.raccoon_todolist.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.raccoon_todolist.R
import com.example.raccoon_todolist.viewmodel.TaskFormViewModel

class TaskFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tViewModel: TaskFormViewModel
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        tViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        setListeners()
        observe()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id== R.id.button_save){
            val task = findViewById<EditText>(R.id.edit_create_task).text.toString()
            val completion = findViewById<RadioButton>(R.id.radio_pendent).isChecked

            tViewModel.save(mTaskId, task, completion)
        }
    }

    private fun observe() {
        tViewModel.saveTask.observe(this, Observer {
            if (it) {
            Toast.makeText(applicationContext, "Tarefa Salva", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })
    }

    private fun setListeners(){
        val saveButton  = findViewById<Button>(R.id.button_save)
        saveButton.setOnClickListener(this)
    }


}