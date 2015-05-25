package edu.washington.ykim253.quizdroid;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    private DownloadManager dm;
    private ListView Topics;
    public int interval;
    public String url;
    public static final int SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        Intent starter = new Intent();
        starter.setAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);

        if(!checkConnection(this)) {
            if(checkAirplane(this)) {
                AlertDialog.Builder window = new AlertDialog.Builder(this);
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_LONG).show();
                window.setTitle("Airplane Mode On")
                      .setMessage("Do you want to turn off the Airplane Mode?")
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent airplane = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                airplane.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(airplane);
                            }
                      })
                      .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                      })
                      .show();
            }
        } else {
            Toast.makeText(this, "No internet, download cannot be completed", Toast.LENGTH_LONG).show();
        }

        QuizApp quizApp = (QuizApp) getApplication();
        List<Topic> subjects = quizApp.getTopics();
        ArrayList<String> newSubject = new ArrayList<String>();

        //get each topic
        for(int i =0; i < subjects.size(); i++) {
            newSubject.add(subjects.get(i).getTitle() + " - " + subjects.get(i).getShort());
        }

        Topics = (ListView) findViewById(R.id.Topics);
        //added custom adapter for the icon/images to show for the listview
        MyCustomAdapter<String> items = new MyCustomAdapter<>(this, newSubject);
        Topics.setAdapter(items);

        Topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String value = String.valueOf(parent.getItemAtPosition(i));
                Intent chosenOne = new Intent(MainActivity.this, SecondActivity.class);
                chosenOne.putExtra("topic", i);
                startActivity(chosenOne);
            }
        });
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            Log.i("Receiver", "onReceive of registered download receiver");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("Receiver", "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample", "Status Check: " + status);
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // The download-complete message said the download was "successful" then run this code
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());
                                    String parsed = QuizApp.getInstance().readJSONFile(fis);
                                    QuizApp.getInstance().writeToFile(parsed);
                                    Log.i("done", parsed);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                new AlertDialog.Builder(getApplicationContext())
                                        .setTitle("Failed Download")
                                        .setMessage("Do you want to retry?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent downloadServiceIntent = new Intent(getApplicationContext(), DownloadService.class);
                                                downloadServiceIntent.putExtra("url", QuizApp.getInstance().getUrl());
                                                startService(downloadServiceIntent);
                                            }
                                        })
                                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //nothing happens
                                            }
                                        })
                                        .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                System.exit(0);
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                }
            }
        }
    };

    public void updatePref() {
        QuizApp.getInstance().startAlarm(MainActivity.this, interval, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            url = sharedPrefs.getString("location",
                    "http://tednewardsandbox.site44.com/questions.json");
            interval = Integer.parseInt(sharedPrefs.getString("minutes", "5"));
            if (url != null) {
                if (url.equals("")) {
                    url = "http://tednewardsandbox.site44.com/questions.json";
                }
            }
            QuizApp.getInstance().setUrl(url);
            QuizApp.getInstance().setInterval(interval);
        }
        updatePref();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void openPreferences() {
        Intent prefs = new Intent(getApplicationContext(), Preferences.class);
        startActivityForResult(prefs, SETTINGS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                openPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean checkAirplane(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    public static boolean checkConnection(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

}
