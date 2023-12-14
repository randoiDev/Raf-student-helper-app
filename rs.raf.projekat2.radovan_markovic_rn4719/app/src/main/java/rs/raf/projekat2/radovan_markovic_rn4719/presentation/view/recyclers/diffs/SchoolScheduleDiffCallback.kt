package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.diffs

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolSchedule

class SchoolScheduleDiffCallback : DiffUtil.ItemCallback<SchoolSchedule>() {

    override fun areItemsTheSame(oldItem: SchoolSchedule, newItem: SchoolSchedule): Boolean {
        return oldItem.subject == newItem.subject
                && oldItem.type == newItem.type
                && oldItem.group == newItem.group
    }

    override fun areContentsTheSame(oldItem: SchoolSchedule, newItem: SchoolSchedule): Boolean {
        return oldItem.subject == newItem.subject
                && oldItem.type == newItem.type
                && oldItem.group == newItem.group
                && oldItem.classroom == newItem.classroom
                && oldItem.day == newItem.day
                && oldItem.time == newItem.time
                && oldItem.professor == newItem.professor
    }

}