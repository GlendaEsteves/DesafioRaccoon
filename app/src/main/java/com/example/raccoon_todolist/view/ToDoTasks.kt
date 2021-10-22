package com.example.raccoon_todolist.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.raccoon_todolist.R
import com.example.raccoon_todolist.databinding.FragmentFirstBinding
import com.example.raccoon_todolist.service.constants.TaskConstants
import com.example.raccoon_todolist.view.adapter.TaskAdapter
import com.example.raccoon_todolist.view.listeners.TaskListener
import com.example.raccoon_todolist.viewmodel.ToDoTasksViewModel

class ToDoTasks : Fragment() {

    private lateinit var mViewModel: ToDoTasksViewModel
    private val mAdapter: TaskAdapter = TaskAdapter()
    private lateinit var mListener: TaskListener

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        mViewModel = ViewModelProvider(this).get(ToDoTasksViewModel::class.java)

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val root = inflater.inflate(R.layout.fragment_first, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_to_do_tasks)

        recycler.layoutManager = LinearLayoutManager(context)

        recycler.adapter = TaskAdapter()

        mListener = object : TaskListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, TaskFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(TaskConstants.TASKID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(TaskConstants.FILTER.PENDENT)
            }
        }

        observe()
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.load(TaskConstants.FILTER.PENDENT)
    }

    private fun observe() {
        mViewModel.taskList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTasks(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}