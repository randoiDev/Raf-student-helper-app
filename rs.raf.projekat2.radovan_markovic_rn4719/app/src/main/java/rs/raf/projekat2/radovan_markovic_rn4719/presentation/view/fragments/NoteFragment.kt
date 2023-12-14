package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.radovan_markovic_rn4719.R
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.FragmentNoteBinding
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts.NoteContract
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities.AddNoteActivity
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities.EditNoteActivity
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.adapters.NoteAdapter
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.ActionsNoteState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.NoteState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels.NoteViewModel
import timber.log.Timber

class NoteFragment: Fragment(R.layout.fragment_note) {

    private var _binding: FragmentNoteBinding? = null
    private val noteViewModel: NoteContract.ViewModel by viewModel<NoteViewModel>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    private val addNoteActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            val title = data?.getStringExtra("TITLE_OUTPUT")
            val content = data?.getStringExtra("CONTENT_OUTPUT")
            if(title != null && content != null) {
                noteViewModel.addNote(title,content)
            }
        }
    }

    private val editNoteActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            val id = data?.getIntExtra("ID_OUTPUT",-1)
            val title = data?.getStringExtra("TITLE_OUTPUT")
            val content = data?.getStringExtra("CONTENT_OUTPUT")
            val value = data?.extras?.getBoolean("ARCHIVED_OUTPUT")
            var value2:Boolean
                value.let {
                    value2 = it==true
            }
            if(title != null && content != null && id!=-1) {
                noteViewModel.updateNote(Note(id,title,content,value2))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
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

    private fun initObservers() {
        noteViewModel.noteState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        noteViewModel.getAllNotes(true)

        noteViewModel.addDone.observe(viewLifecycleOwner, Observer {
            renderActions(it)
        })

        noteViewModel.deleteDone.observe(viewLifecycleOwner, Observer {
            renderActions(it)
        })

        noteViewModel.updateDone.observe(viewLifecycleOwner, Observer {
            renderActions(it)
        })
    }

    private fun initRecycler() {
        binding.notesList.layoutManager = LinearLayoutManager(context)
        adapter = NoteAdapter({
                              noteViewModel.deleteNote(it)
        }, {
            editNoteActivity.launch(Intent(requireContext(), EditNoteActivity::class.java)
                .putExtra("ID_INPUT",it.id).putExtra("TITLE_INPUT",it.title)
                .putExtra("CONTENT_INPUT",it.content)
                .putExtra("ARCHIVED_INPUT",it.archived))
        }, {
            val value=!it.archived
            noteViewModel.updateNote(Note(it.id,it.title,it.content,value))
        })
        binding.notesList.adapter = adapter
    }

    private fun initListeners() {
        binding.switch2.setOnCheckedChangeListener { _, b ->
            if(b) {
                noteViewModel.getAllNotes(false)
            } else {
                noteViewModel.getAllNotes(true)
            }
        }

        binding.addButton.setOnClickListener {
            addNoteActivity.launch(Intent(context,AddNoteActivity::class.java))
        }
        binding.contentTitleInput.doAfterTextChanged {
            val filter = it.toString()
            noteViewModel.getFilteredNotes(filter)
        }
    }

    private fun renderActions(state: ActionsNoteState) {
        when(state) {
            is ActionsNoteState.Success -> {
                Toast.makeText(context,state.message, Toast.LENGTH_SHORT).show()
            }
            is ActionsNoteState.Error -> {
                Toast.makeText(context,state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderState(state: NoteState) {
        when (state) {
            is NoteState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.notes)
            }
            is NoteState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NoteState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.notesList.isVisible = !loading
        binding.addButton.isVisible = !loading
        binding.contentTitleInput.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}