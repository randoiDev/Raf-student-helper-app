package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.fragments.NoteFragment
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.fragments.SchoolScheduleFragment

class ContentPagerAdapter(
    fragmentManager: FragmentManager,
    val name1:String,
    val name2:String
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> SchoolScheduleFragment()
            else -> NoteFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> name1
            else -> name2
        }
    }
}