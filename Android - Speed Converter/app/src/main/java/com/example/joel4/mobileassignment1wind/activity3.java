package com.example.joel4.mobileassignment1wind;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class activity3 extends ActionBarActivity {

    private TextView txt1;
    private TextView txt2;
    private NotificationManager mNManager;
    private static final int NOTIFY_ID = 1100;
    private String phoneNum = "021 1234567"; // the phone number of the notification to call
    private int buttonsClicked = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);

        Button button1 = (Button) findViewById(R.id.button1);
        txt1 = (TextView) findViewById(R.id.A3_txtView1);
        Button button2 = (Button) findViewById(R.id.button2);
        txt2 = (TextView) findViewById(R.id.A3_txtView2);
        Button button3 = (Button) findViewById(R.id.button3);
        final Context con = (Context) this;

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                updateInt();
                new AlertDialog.Builder(con)
                        .setTitle(getString(R.string.A3_alertTitle))
                        .setMessage(getString(R.string.A3_alertTxt))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                setAlert(1);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                setAlert(2);
                            }
                        })
                        .setNeutralButton(R.string.maybe, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                setAlert(3);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                updateInt();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(con)
                                .setSmallIcon(R.drawable.notification_icon)
                                .setContentTitle(getString(R.string.A3_notificationName))
                                .setContentText(getString(R.string.A3_notificationTxt))
                        .setAutoCancel(true);

                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(con, activity3.class);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);

                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(activity3.class);

                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                stackBuilder.addNextIntent(callIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("buttonsClicked",buttonsClicked);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });




    }


    private void setAlert(int n)
    {
        if(n == 1)
        {
            txt1.setText(getString(R.string.A3_alertYes));
        }
        else if(n == 2)
        {
            txt1.setText(getString(R.string.A3_alertNo));
        }
        else
        {
            txt1.setText(getString(R.string.A3_alertMaybe));
        }
    }

    private void updateInt()
    {
        buttonsClicked++;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
