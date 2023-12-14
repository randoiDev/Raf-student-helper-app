package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.viewHolders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolSchedule
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.LayoutItemSchoolScheduleBinding

class SchoolScheduleViewHolder(private val itemBinding: LayoutItemSchoolScheduleBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(ss: SchoolSchedule) {
        itemBinding.subjectAndType.text = "${ss.subject} - ${ss.type}"
        itemBinding.professor.text = ss.professor
        itemBinding.classrom.text = ss.classroom
        itemBinding.day.text = ss.day
        itemBinding.time.text = ss.time
        itemBinding.group.text = ss.group
    }

}