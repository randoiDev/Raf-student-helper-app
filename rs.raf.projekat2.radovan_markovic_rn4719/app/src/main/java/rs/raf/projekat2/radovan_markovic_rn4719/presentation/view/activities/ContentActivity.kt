package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.projekat2.radovan_markovic_rn4719.R
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.ActivityContentBinding
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.adapter.ContentPagerAdapter

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.viewPager.adapter =
            ContentPagerAdapter(
                supportFragmentManager,
                resources.getString(R.string.ss),
                resources.getString(R.string.n)
            )
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}