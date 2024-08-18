package com.example.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.NoteAdapter
import com.example.noteapp.R
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.databinding.FragmentMainBinding
import com.example.noteapp.models.NoteResponse
import com.example.noteapp.repository.NoteRepository
import com.example.noteapp.viewModel.NoteViewModel
import com.google.gson.Gson


class MainFragment : Fragment() {

     private  var _binding : FragmentMainBinding?= null
    private val binding get()=_binding
     private val noteviewModel by viewModels<NoteViewModel>()
    private lateinit var adaptrer : NoteAdapter(::onNoteClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        adaptrer = NoteAdapter()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindObserver()
        noteviewModel.getNotes()
        binding?.noteList?.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding?.noteList?.adapter =adaptrer
    }

    private fun bindObserver() {
        noteviewModel.notesLivedata.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success->{
                   adaptrer.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {}
            }
        })
    }

    fun onNoteClicked(noteResponse: NoteResponse){

        val bundle = Bundle()
        bundle.putString("note",Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragments,bundle)
    }

}