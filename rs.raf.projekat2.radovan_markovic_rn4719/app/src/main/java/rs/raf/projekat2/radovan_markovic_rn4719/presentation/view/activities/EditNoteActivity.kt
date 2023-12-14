package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import rs.raf.projekat2.radovan_markovic_rn4719.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.inputTitle.setText(intent.getStringExtra("TITLE_INPUT").toString())
        binding.inputContent.setText(intent.getStringExtra("CONTENT_INPUT").toString())
        val id:Int = intent.getIntExtra("ID_INPUT",-1)
        val value:Boolean = intent.getBooleanExtra("ARCHIVED_INPUT",false)
        binding.buttonEdit.setOnClickListener {
            if(binding.inputTitle.text.toString() == "" || binding.inputContent.text.toString() == "") {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra("ID_OUTPUT",id)
                    .putExtra("TITLE_OUTPUT",binding.inputTitle.text.toString())
                    .putExtra("CONTENT_OUTPUT",binding.inputContent.text.toString())
                    .putExtra("ARCHIVED_OUTPUT",value)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
        binding.buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent());
            finish();
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED, Intent());
        finish();
        super.onBackPressed()
    }
}