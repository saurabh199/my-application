package com.page.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;

import static android.content.Context.TELECOM_SERVICE;

public class IncomingCallReceiver extends BroadcastReceiver {
    private static final String TAG = "";
    String number = "9599525679";
    static int i = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

        ITelephony telephonyService;
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    @SuppressLint("SoonBlockedPrivateApi") Method m = tm.getClass().getDeclaredMethod("getITelephony");

                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(tm);

                    if ((number != null)) {
                        telephonyService.endCall();
                        Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                breakCallPieAndHigher(context);
                Toast.makeText(context, "Ring " + number, Toast.LENGTH_SHORT).show();

            }
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                breakCallPieAndHigher(context);
                Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();
                TelecomManager telecomManager = (TelecomManager) context.getSystemService(TELECOM_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (telecomManager.isInCall()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        telecomManager.endCall();
                    }
                }

            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context, "Idle "+ number, Toast.LENGTH_SHORT).show();
                breakCallPieAndHigher(context);
                i++;
                if(i < 3) {
                    Intent i = new Intent();
                    i.setClass(context, CallActivity.class);
                    i.putExtra("number", "9599525679");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);


                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    private void breakCallPieAndHigher(Context context) {
        Log.d(TAG, "Trying to break call for Pie and higher with TelecomManager.");
        TelecomManager telecomManager = (TelecomManager)
                context.getSystemService(Context.TELECOM_SERVICE);
        try {
            telecomManager.getClass().getMethod("endCall").invoke(telecomManager);
            Log.d(TAG, "Invoked 'endCall' on TelecomManager.");
        } catch (Exception e) {
            Log.e(TAG, "Could not end call. Check stdout for more info.");
            e.printStackTrace();
        }
    }
    public void callPhoneNumber(Context context) {
        try {
          //  if (Build.VERSION.SDK_INT > 22) {
            System.out.println("hsdhbjvshfhbvsfhdbsfhb");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "9599525679"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              /*  if (ActivityCompat.checkSelfPermission(get, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
*/

                context.startActivity(callIntent);
                // phoneState();
                //getLastCallDuration();

           /* } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "9599525679"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(callIntent);
                //phoneState();
                //getLastCallDuration();

            }*/
        } catch (Exception ex) {
          // ex.getMessage(ex);
            System.out.println("------"+ex.getMessage());
        }
    }

}