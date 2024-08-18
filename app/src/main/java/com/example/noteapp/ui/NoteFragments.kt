package com.example.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteFragmentsBinding
import com.example.noteapp.models.NoteResponse
import com.google.gson.Gson


class NoteFragments : Fragment() {

    private var _binding : FragmentNoteFragmentsBinding?=null
    private val binding get() = _binding
    private  var  note : NoteResponse?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding= FragmentNoteFragmentsBinding.inflate(inflater,container,true)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitalvalue()
    }

    private fun setInitalvalue() {
        val json = arguments?.getString("note")
        if (json!=null){
          note = Gson().fromJson(json,NoteResponse :: class.java)
            note?.let {
                binding?.txtTitle?.setText(it.title)
                binding?.txtDescription?.setText(it.discription)
            }
        }
        else{
            binding?.addEditText?.text="Add Note"
        }
    }

}