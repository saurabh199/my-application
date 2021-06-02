package com.page.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class MyPhoneStateListener extends PhoneStateListener {
    public static Boolean phoneRinging = false;
    ProgressDialog progressDialog;
  Context mContext;
  CountDownLatch countDownLatch;
    MyPhoneStateListener(Context context){
        mContext = context;
    }

    public MyPhoneStateListener(int i) {

    }

   /* public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }*/

    public void onCallStateChanged(int state, String incomingNumber) {
      //  mContext = this;
    progressDialog = new ProgressDialog(mContext);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                System.out.println("-----IDLE--------------");

                phoneRinging = false;
               progressDialog.dismiss();

               if(countDownLatch !=null){
                   countDownLatch.countDown();
               }

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                System.out.println("-----OFFHOOK--------------");
                phoneRinging = false;
                //progressDialog.setCancelable(false);

                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                System.out.println("-----RINGING--------------");

                phoneRinging = true;
                //progressDialog.setCancelable(false);

                break;
        }
    }
}
