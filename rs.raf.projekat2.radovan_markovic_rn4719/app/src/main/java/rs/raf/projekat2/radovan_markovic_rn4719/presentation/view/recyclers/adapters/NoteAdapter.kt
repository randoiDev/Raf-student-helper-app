package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.LayoutItemNoteBinding
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.diffs.NoteDiffCallback
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.viewHolders.NoteViewHolder


class NoteAdapter(
private val onDeleteClicked: (Note) -> Unit,
private val onEditClicked: (Note) -> Unit,
private val onArchivedClicked: (Note) -> Unit
    ) : ListAdapter<Note, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = LayoutItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding, {
            val note = getItem(it)
            onDeleteClicked.invoke(note)
        }, {
            val note = getItem(it)
            onEditClicked.invoke(note)
        }, {
            val note = getItem(it)
            onArchivedClicked.invoke(note)
        })


    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}