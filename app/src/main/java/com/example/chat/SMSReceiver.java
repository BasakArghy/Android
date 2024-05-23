package com.example.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    String msg,mobNo;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=  intent.getExtras();
        Object[] smsObj= (Object[]) bundle.get("pdus");

        for(Object obj:smsObj){
            SmsMessage message= SmsMessage.createFromPdu((byte[]) obj );

            mobNo=  message.getDisplayOriginatingAddress();
            msg = message.getDisplayMessageBody();



          //  Toast.makeText(context.getApplicationContext(), "MobNo: "+mobNo+",msg: "+msg, Toast.LENGTH_SHORT).show();


        }

    }

}
