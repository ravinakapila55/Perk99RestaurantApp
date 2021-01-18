/*
package com.app.perk99restaurant.notification


import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCM : FirebaseMessagingService()
{
    private val TAG = "MyFirebaseMsgService"
    private var data: Map<String, String>? = null
    private var type: String? = null
    private val title: String? = null
    private var message:String? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage?)
    {
        if (remoteMessage!!.data != null)
        {
            data = remoteMessage!!.data
            Log.e(TAG, "onMessageReceived: " + remoteMessage!!.data)
            message = data!!["message"]
            type = data!!["type"]

//            Log.e(TAG, "message $message")
//            Log.e(TAG, "type $type")
        }
    }

}*/
