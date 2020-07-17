package com.example.stubbornnotes

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var notificationManager:NotificationManager?=null
    var channel=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        addNote.setOnClickListener{
        val alertDialog=AlertDialog.Builder(this)
            alertDialog.setTitle("Add Task")
            alertDialog.setMessage("Enter Task Name : ")
            val task: EditText= EditText(this)
            alertDialog.setView(task)
            alertDialog.create()
            alertDialog.setPositiveButton("Add Note",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    createNotification(task?.text.toString())
                }

            })
            alertDialog.show()
        }
    }

    private fun createNotification(text: String?) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channel.toString(), channel.toString(), IMPORTANCE_LOW)
            notificationChannel.enableVibration(false)
            notificationManager?.createNotificationChannel(notificationChannel)
            val notification = NotificationCompat.Builder(applicationContext, channel.toString())
            notification.setContentTitle(text)
            notification.setOngoing(true)
            notification.setChannelId(channel.toString())
            notification.setSmallIcon(R.drawable.ic_note_black_24dp)
            notification.setContentText("Tap To Remove")
            notification.setAutoCancel(true)
            notification.setContentIntent(PendingIntent.getActivity(this,0,Intent(),0))
            notificationManager?.notify(++channel,notification.build())
            channel++
        }
    }
}
