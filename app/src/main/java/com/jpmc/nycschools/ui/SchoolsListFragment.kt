package com.jpmc.nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jpmc.nycschools.R
import com.jpmc.nycschools.data.NetworkResult
import com.jpmc.nycschools.data.SchoolModel
import com.jpmc.nycschools.databinding.FragmentSchoolsListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SchoolsListFragment : Fragment(R.layout.fragment_schools_list) {

    private lateinit var binding: FragmentSchoolsListBinding

    private val viewModel: SchoolsViewModel by viewModels()

    @Inject
    lateinit var schoolsAdapter: SchoolsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSchoolsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerListener()
        initViews()
    }

    private fun registerListener() {
        viewModel.nYCHighSchoolsResponse.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                    binding.includeLayoutEmptyList.layoutEmptyList.isVisible = false
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                    binding.includeLayoutEmptyList.layoutEmptyList.isVisible = true
                    binding.rvSchools.isVisible = false
                }

                is NetworkResult.Success -> {
                    if (it.data.isEmpty()) {
                        binding.includeLayoutEmptyList.layoutEmptyList.isVisible = true
                        binding.rvSchools.isVisible = false
                    } else {
                        binding.includeLayoutEmptyList.layoutEmptyList.isVisible = false
                        binding.rvSchools.isVisible = true
                        schoolsAdapter.updateSchools(it.data)
                    }
                    binding.progressbar.isVisible = false
                }
            }
        }
    }

    private fun initViews() {
        binding.rvSchools.adapter = schoolsAdapter
        schoolsAdapter.setItemClick(object : ClickInterface<SchoolModel> {
            override fun onClick(schoolModel: SchoolModel) {
                //findNavController().navigate(R.id.action_SchoolsListFragment_to_schoolDetailsFragment)
                val action =
                    SchoolsListFragmentDirections.actionSchoolsListFragmentToSchoolDetailsFragment(schoolModel)
                findNavController().navigate(action)
            }
        })
        viewModel.fetchNYCHighSchools()
    }

}
