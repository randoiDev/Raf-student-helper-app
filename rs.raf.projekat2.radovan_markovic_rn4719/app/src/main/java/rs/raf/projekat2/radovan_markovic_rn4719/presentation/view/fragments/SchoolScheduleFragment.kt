package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.radovan_markovic_rn4719.R
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.FragmentSchoolScheduleBinding
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts.SchoolScheduleContract
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.adapters.SchoolScheduleAdapter
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.SchoolScheduleState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner1State
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner2State
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels.SchoolScheduleViewModel
import timber.log.Timber

class SchoolScheduleFragment: Fragment(R.layout.fragment_school_schedule) {

    private val schoolScheduleViewModel: SchoolScheduleContract.ViewModel by viewModel<SchoolScheduleViewModel>()
    private lateinit var adapter: SchoolScheduleAdapter

    private var _binding: FragmentSchoolScheduleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSchoolScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.schoolScheduleList.layoutManager = LinearLayoutManager(context)
        adapter = SchoolScheduleAdapter()
        binding.schoolScheduleList.adapter = adapter
    }

    private fun initListeners() {
        binding.filterButton.setOnClickListener {
            val group:String = binding.spinner1.selectedItem as String
            val day:String = binding.spinner2.selectedItem as String
            val mixed:String = binding.professorAndSubjectInput.text.toString()
            schoolScheduleViewModel.getFilteredSchoolSchedules(group,day,mixed)
        }
    }

    private fun initObservers() {

        schoolScheduleViewModel.spinner1State.observe(viewLifecycleOwner, Observer {
            val spinner = binding.spinner1
            val list1: MutableList<String> = mutableListOf()
            when (it) {
                is Spinner1State.Success -> {
                    list1.add("Group")
                    it.groups.forEach {
                        list1.add(it.group)
                    }
                }
                is Spinner1State.Loading -> {
                    Timber.e("Spinner1 Loading")
                }
                is Spinner1State.Error -> {
                    Timber.e("Spinner1 Error")
                }
            }
            val s1: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                list1
            )
            s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = s1

        })
        schoolScheduleViewModel.spinner2State.observe(viewLifecycleOwner, Observer {
            val spinner2 = binding.spinner2
            val list2: MutableList<String> = mutableListOf()
            when (it) {
                is Spinner2State.Success -> {
                    list2.add("Day")
                    it.days.forEach {
                        list2.add(it.day)
                    }
                }
                is Spinner2State.Loading -> {
                    Timber.e("Spinner2 Loading")
                }
                is Spinner2State.Error -> {
                    Timber.e("Spinner2 Error")
                }
            }
            val s2: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                list2
            )
            s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = s2

        })

        schoolScheduleViewModel.getAllGroups()

        schoolScheduleViewModel.getAllDays()

        schoolScheduleViewModel.schoolScheduleState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        schoolScheduleViewModel.getAllSchoolSchedules()

        schoolScheduleViewModel.fetchAllSchoolSchedules()
    }

    private fun renderState(state: SchoolScheduleState) {
        when (state) {
            is SchoolScheduleState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.schoolSchedules)
            }
            is SchoolScheduleState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SchoolScheduleState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_SHORT).show()
            }
            is SchoolScheduleState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.spinner1.isVisible = !loading
        binding.spinner2.isVisible = !loading
        binding.professorAndSubjectInput.isVisible = !loading
        binding.schoolScheduleList.isVisible = !loading
        binding.filterButton.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}