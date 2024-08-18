package com.example.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.noteapp.viewModel.AuthViewModels
import com.example.noteapp.R
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.Utlils.TokenManager
import com.example.noteapp.databinding.FragmentLoginBinding
import com.example.noteapp.models.UserRequest
import javax.inject.Inject


class LoginFragment : Fragment() {
   private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModels by viewModels<AuthViewModels>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
         val validateResult = validateUserInput()
            if (validateResult.first){
                authViewModels.login(getUserRequest())
            }else{
               binding.txtError.text=validateResult.second
            }
        }
        binding.btnSignUp.setOnClickListener {
          findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun bindObserver() {
        authViewModels.userResponseLiveData.observe(viewLifecycleOwner, Observer {
        //    binding.progressbar.isVisible = false
            when(it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error->{
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading->{
              //      binding.progressbar.isVisible = true
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
         val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
         return UserRequest(emailAddress,password,"")
    }
    private fun validateUserInput(): Pair<Boolean,String> {
      val userRequest = getUserRequest()

        return  authViewModels.validatesCredentials(userRequest.userName,userRequest.email,userRequest.password,true)
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}