package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.viewHolders

import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.radovan_markovic_rn4719.R
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.LayoutItemNoteBinding

class NoteViewHolder(
    private val itemBinding: LayoutItemNoteBinding,
    private val onDeleteClicked: (Int) -> Unit,
    private val onEditClicked: (Int) -> Unit,
    private val onArchiveClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.deleteNoteButton.setOnClickListener {
            onDeleteClicked.invoke(adapterPosition)
        }

        itemBinding.editNoteButton.setOnClickListener {
            onEditClicked.invoke(adapterPosition)
        }

        itemBinding.archiveNoteButton.setOnClickListener {
            onArchiveClicked.invoke(adapterPosition)
        }
    }

    fun bind(note: Note) {
        itemBinding.hiddenId.text = note.id.toString()
        itemBinding.title.text = note.title
        itemBinding.content.text = note.content
        if(note.archived) {
            itemBinding.archiveNoteButton.setBackgroundResource(R.drawable.unarchive)
        } else {
            itemBinding.archiveNoteButton.setBackgroundResource(R.drawable.archive_icon)
        }
    }

}