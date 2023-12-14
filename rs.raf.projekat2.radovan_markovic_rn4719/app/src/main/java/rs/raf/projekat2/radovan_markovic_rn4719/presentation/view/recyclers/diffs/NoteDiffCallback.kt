package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.diffs

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title
                && oldItem.content == newItem.content
                && oldItem.archived == newItem.archived
    }

}