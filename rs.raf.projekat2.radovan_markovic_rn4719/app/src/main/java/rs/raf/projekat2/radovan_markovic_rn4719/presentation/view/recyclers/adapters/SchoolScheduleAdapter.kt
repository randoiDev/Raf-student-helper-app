package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolSchedule
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.LayoutItemSchoolScheduleBinding
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.diffs.SchoolScheduleDiffCallback
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.recyclers.viewHolders.SchoolScheduleViewHolder

class SchoolScheduleAdapter : ListAdapter<SchoolSchedule, SchoolScheduleViewHolder>(SchoolScheduleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolScheduleViewHolder {
        val itemBinding = LayoutItemSchoolScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolScheduleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SchoolScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}