package git.myapplication.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import git.myapplication.notification.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private companion object{
//        there can be multiple notifications, so it can be used as notification identity
        private const val CHANNEL_ID = "channel01"
    }

//    UI Views
    private lateinit var showNotificationBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showNotificationBtn = findViewById(R.id.showNotificationBtn)
        //handle click, show notification
        showNotificationBtn.setOnClickListener {
            showNotification()
        }


    }

    private fun showNotification() {
        createNotificationChannel()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.JAPAN).format(date).toInt()

        //handle notification click, start SecondActivity by Tapping notification
        val mainIntent = Intent(this,SecondActivity::class.java)
        // if you want to pass data in notification and get in required activity
        mainIntent.putExtra("KEY_NAME","JUNG KIHOON")
        mainIntent.putExtra("KEY_EMAIL","rlgns927@gmail.com")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivity(this,1,mainIntent, PendingIntent.FLAG_ONE_SHOT)

        //creating notification builder
        val notificationBuilder = NotificationCompat.Builder(this,"$CHANNEL_ID")
        //notification icon
        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
        //notification title
        notificationBuilder.setContentTitle("Notification Title")
        //notification description
        notificationBuilder.setContentText("This is the description of the notification, can be of multiple lines")
        //notification priority
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        //cancle notification on click
        notificationBuilder.setAutoCancel(true)
        //add click intent
        notificationBuilder.setContentIntent(mainPendingIntent)

        //notification manager
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name :CharSequence= "Mynotification"
            val description = "My notification channel description"
            //importance of your notification
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name , importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}