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
import com.example.raccoon_todolist.databinding.FragmentSecondBinding
import com.example.raccoon_todolist.service.constants.TaskConstants
import com.example.raccoon_todolist.view.adapter.TaskAdapter
import com.example.raccoon_todolist.view.listeners.TaskListener
import com.example.raccoon_todolist.viewmodel.ToDoTasksViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CompletedTasks : Fragment() {

    private lateinit var mViewModel: ToDoTasksViewModel
    private val mAdapter: TaskAdapter = TaskAdapter()
    private lateinit var mListener: TaskListener

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        mViewModel = ViewModelProvider(this).get(ToDoTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_second, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_completed_tasks)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        observe()

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
                mViewModel.load(TaskConstants.FILTER.COMPLETED)
            }
        }

        mAdapter.attachListener(mListener)

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    override fun onResume() {
        super.onResume()
        mViewModel.load(TaskConstants.FILTER.COMPLETED)
    }

    private fun observe() {
        mViewModel.taskList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTasks(it)
        })
    }


}