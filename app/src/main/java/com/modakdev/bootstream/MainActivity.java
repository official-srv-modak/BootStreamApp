package com.modakdev.bootstream;
// Author - Sourav Modak

import static com.modakdev.bootstream.Profiles.ipInfoFilePath;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.modakdev.bootstream.main.SectionsPagerAdapter;

import com.modakdev.bootstream.R;


public class MainActivity extends AppCompatActivity{

    public static String username = "admin";
    public static String ip = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ImageView menu = findViewById(R.id.menu);
        username = getIntent().getStringExtra("username");
        ip = getIntent().getStringExtra("ip");

        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        ImageButton searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.putExtra("username", username);
                startActivity(searchIntent);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId(); // Get the selected menu item's ID.

                if (id == R.id.profiles) {
                    Intent intent = new Intent(MainActivity.this, Profiles.class);
                    intent.putExtra("startFlag", "1");
                    startActivity(intent);
                    finish();
                } else if (id == R.id.trailer) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent);
                } else if (id == R.id.resetProfile) {
                    resetProfile("Do you really want to reset all your watching history?");
                } else if (id == R.id.contactUs) {
                    showContactUs("Developer - Sourav Modak\nContact Number - +1 437 602 9840\nE-Mail - official.srv.modak@gmail.com.");
                } else if (id == R.id.resetIp) {
                    showServerDialogNoExit(getString(R.string.reset_ip_message));
                }

                DrawerLayout drawer = findViewById(R.id.drawerlayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.profileName);
        navUsername.setText(username);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.souravmodak.arcinema", "com.souravmodak.arcinema.SplashActivity"));
                startActivity(intent);

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void showContactUs(String Message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }
    public void resetProfile(String Message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResetProfile resetProfile = new ResetProfile();
                resetProfile.execute(Profiles.reset_profile);

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }

    private class ResetProfile extends AsyncTask<String, Void, Integer> {

        @SuppressLint("ResourceType")
        @Override
        protected Integer doInBackground(String... url) {
            Movies.pingDataServer(Description.handleUrl(url[0]));
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return null;
        }

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();


        }
    }

    public void showServerDialog(String Message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Movies.writeIpData("192.168.0.4");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Enter Server's Local IP Address");
                final EditText input = new EditText(MainActivity.this);
                input.setHint("IP Address");
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Profiles.writeIpData(ipInfoFilePath, input.getText().toString().trim());
                        ip = input.getText().toString().trim();
                        Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                        finish();
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogBuilder.show();
    }
    public void showServerDialogNoExit(String Message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Movies.writeIpData("192.168.0.4");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Enter Server's Local IP Address");
                final EditText input = new EditText(MainActivity.this);
                input.setHint("IP Address");
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Profiles.writeIpData(ipInfoFilePath, input.getText().toString().trim());
                        ip = input.getText().toString().trim();
                        Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                        finish();
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }
}