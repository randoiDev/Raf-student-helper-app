package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen: SplashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            checkLogin()
            false
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun checkLogin() {
        val sharedPreference: SharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val user: String? = sharedPreference.getString(LoginActivity.USER_KEY,"")
        if(!user.equals("")) {
            startActivity(Intent(this,ContentActivity::class.java))
        } else {
            startActivity(Intent(this,LoginActivity::class.java).putExtra("Mode",Context.MODE_PRIVATE))
        }
    }
}