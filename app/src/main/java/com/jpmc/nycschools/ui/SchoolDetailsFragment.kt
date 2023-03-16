package com.jpmc.nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jpmc.nycschools.R
import com.jpmc.nycschools.data.NetworkResult
import com.jpmc.nycschools.data.SchoolModel
import com.jpmc.nycschools.databinding.FragmentSchoolDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDetailsFragment : DialogFragment(R.layout.fragment_school_details) {

    private lateinit var binding: FragmentSchoolDetailsBinding

    private val viewModel: SchoolsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSchoolDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_FRAME,
            R.style.FullScreenDialogStyle
        );
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerListener()
        with(binding){
            toolbar.setNavigationOnClickListener{
                dismiss()
            }
        }
        val safeArgs: SchoolDetailsFragmentArgs by navArgs()
        safeArgs.model.let {
            binding.tvTitle.text = it.school_name
            initViews(it)
        }
    }

    private fun registerListener() {
        viewModel.schoolsDetailsResponse.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    binding.progressbar.isVisible = false
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success -> {
                    if (it.data.isNotEmpty()) {
                        it.data[0].let {
                            with(binding){
                                tvNumOfSatTestTakers.text = getString(R.string.num_of_sat_test_takers,it.num_of_sat_test_takers)
                                tvMathAvgScore.text = getString(R.string.math_avg_score,it.sat_math_avg_score)
                                tvReadingAvgScore.text = getString(R.string.reading_avg_score,it.sat_critical_reading_avg_score)
                                tvWritingAvgScore.text = getString(R.string.writing_avg_score,it.sat_writing_avg_score)
                            }
                        }
                    }
                    binding.progressbar.isVisible = false
                }
            }
        }
    }

    private fun initViews(schoolModel: SchoolModel) {
        with(binding){
            tvTitle.text = schoolModel.school_name
            tvDescription.text = schoolModel.overview_paragraph
            tvPhone.text = schoolModel.phone_number
            tvEmail.text = schoolModel.school_email
            tvWebsite.text = schoolModel.website
            tvNumOfSatTestTakers.text = getString(R.string.num_of_sat_test_takers,"NA")
            tvMathAvgScore.text = getString(R.string.math_avg_score,"NA")
            tvReadingAvgScore.text = getString(R.string.reading_avg_score,"NA")
            tvWritingAvgScore.text = getString(R.string.writing_avg_score,"NA")
        }
        schoolModel.dbn?.let {
           viewModel.fetchSchoolDetails(it)
        }
    }

}
