package git.myapplication.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import git.myapplication.notification.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //get data from notification using intent
        val bundle:Bundle? = intent!!.extras

        if (bundle != null)
        {
            val name = bundle.getString("KEY_NAME")
            val email = bundle.getString("KEY_EMAIL")

            Toast.makeText(this,"$name \n$email",Toast.LENGTH_LONG).show()
        }
    }
}