package com.modakdev.bootstream;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class Description extends AppCompatActivity {

    static  long durFromMx=0, posFromMx=0;
    static JSONObject card;
    static String resumeFlag = null, name = null;
    static String activityResume = "0";
    private static String username = "";
    private static String id = "";
    private static String descriptionStr = "";
    private static String imageUrl = "";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        this.overridePendingTransition(R.anim.nothing,R.anim.activity_slide_down);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)  // -1 RESULT_OK : Playback was completed or stopped by user request.
            //Activity.RESULT_CANCELED: User canceled before starting any playback.
            //RESULT_ERROR (=Activity.RESULT_FIRST_USER): Last playback was ended with an error.

            if (data.getAction().equals("com.mxtech.intent.result.VIEW")) {
                //data.getData()
                PostProcess p = new PostProcess();
                p.execute(data);
            }
//            else if (data.getAction().equals(modakflixPlayerAction)) {
//                //data.getData()
//                PostProcess p = new PostProcess();
//                p.execute(data);
//            }

    }

    private void doPostProcess(Intent data)
    {
////        if(data.getAction().equals(modakflixPlayerAction))
////        {
////            long pos = data.getLongExtra("position", -1); // Last playback position in milliseconds. This extra will not exist if playback is completed.
////            long dur = data.getLongExtra("duration", -1); // Duration of last played video in milliseconds. This extra will not exist if playback is completed.
////            String cause = data.getStringExtra("end_by"); //  Indicates reason of activity closure.
////            Uri uri = data.getData();
////            String name = "";
////            durFromMx = dur;
////            posFromMx = pos;
////            if(pos != -1 && dur != -1)
////            {
////                try {
////                    name = URLDecoder.decode(uri.toString().split("/")[uri.toString().split("/").length - 2], "UTF-8");
////
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
////                try {
////                    double rem = 0;
////                    rem = dur - pos;
////                    rem = (rem/dur)*100;
////                    if (rem >= 5)
////                    {
////                        Movies.pingDataServer(Profiles.record_position_path+"?username="+username+"&show="+ URLDecoder.decode(uri.toString(), "UTF-8")+"&pos="+pos+"&duration="+dur+"&cause="+cause+"&name="+name);
////                    }
////
////                    else
////                        Movies.pingDataServer(Profiles.delete_position_path+"?username="+username+"&show="+name);
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
////                Button openWith = findViewById(R.id.playWithBtn);
////                long rem = dur - pos;
////                rem /= 1000;
////                final long[] mins = {rem / 60};
////                long hrs = mins[0] /60;
////                if(hrs > 0)
////                {
////                    mins[0] = mins[0] %60;
////                    if(hrs > 0)
////                    {
////                        mins[0] = mins[0] %60;
////                        openWith.setText("Resume "+hrs+" hour "+ mins[0] +" min(s) left");
////                    }
////                    else
////                    {
////                        openWith.setText("Resume "+ mins[0] +" min(s) left");
////                    }
////                    //openWith.setText("Resume "+(int)hrs+" hour "+(int)mins+" min(s) left");
////                    long finalMins = mins[0];
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Button playBtn = findViewById(R.id.playBtn);
////                            if(hrs > 0)
////                            {
////                                mins[0] = mins[0] %60;
////                                playBtn.setText("Resume with internal player : "+hrs+" hour "+ mins[0] +" min(s) left");
////                            }
////                            else
////                            {
////                                playBtn.setText("Resume with internal player : "+ mins[0] +" min(s) left");
////                            }
////                           // playBtn.setText("Resume with internal player : "+hrs+" hour "+ finalMins +" min(s) left");
////                        }
////                    });
////                }
////                else
////                {
////                    if(hrs > 0)
////                    {
////                        mins[0] = mins[0] %60;
////                        openWith.setText("Resume "+hrs+" hour "+ mins[0] +" min(s) left");
////                    }
////                    else
////                    {
////                        openWith.setText("Resume "+ mins[0] +" min(s) left");
////                    }
////                    //openWith.setText("Resume "+(int) mins[0] +" min(s) left");
////                    long finalMins1 = mins[0];
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Button playBtn = findViewById(R.id.playBtn);
////                            if(hrs > 0)
////                            {
////                                mins[0] = mins[0] %60;
////                                playBtn.setText("Resume with internal player : "+hrs+" hour "+ mins[0] +" min(s) left");
////                            }
////                            else
////                            {
////                                playBtn.setText("Resume with internal player : "+ mins[0] +" min(s) left");
////                            }
////                            //playBtn.setText("Resume with internal player : "+hrs+" hour "+ finalMins1 +" min(s) left");
////                        }
////                    });
////                }
////            }
////        }
//        else
//        {
            int pos = data.getIntExtra("position", -1); // Last playback position in milliseconds. This extra will not exist if playback is completed.
            int dur = data.getIntExtra("duration", -1); // Duration of last played video in milliseconds. This extra will not exist if playback is completed.
            String cause = data.getStringExtra("end_by"); //  Indicates reason of activity closure.
            Uri uri = data.getData();
            String name = "";
            durFromMx = dur;
            posFromMx = pos;
            if(pos != -1 && dur != -1)
            {
                try {
                    name = URLDecoder.decode(uri.toString().split("/")[uri.toString().split("/").length - 2], "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    double rem = 0;
                    rem = dur - pos;
                    rem = (rem/dur)*100;
                    if (rem >= 5)
                    {
                        Movies.pingDataServer(Profiles.record_position_path+"?username="+username+"&show="+ URLDecoder.decode(uri.toString(), "UTF-8")+"&pos="+pos+"&duration="+dur+"&cause="+cause+"&name="+name+"&id="+id);
                    }

                    else
                        Movies.pingDataServer(Profiles.delete_position_path+"?username="+username+"&show="+name);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Button openWith = findViewById(R.id.playWithBtn);
                int rem = dur - pos;
                rem /= 1000;
                final int[] mins = {rem / 60};
                int hrs = mins[0] /60;
                if(hrs > 0)
                {
                    mins[0] = mins[0] %60;
                    if(hrs > 0)
                    {
                        mins[0] = mins[0] %60;
                        openWith.setText("Resume "+hrs+" hour "+ mins[0] +" min(s) left");
                    }
                    else
                    {
                        openWith.setText("Resume "+ mins[0] +" min(s) left");
                    }
                    //openWith.setText("Resume "+hrs+" hour "+mins+" min(s) left");
                    int finalMins = mins[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Button playBtn = findViewById(R.id.playBtn);
//                            if(hrs > 0)
//                            {
//                                mins[0] = mins[0] %60;
//                                playBtn.setText("Resume with internal player : "+hrs+" hour "+ mins[0] +" min(s) left");
//                            }
//                            else
//                            {
//                                playBtn.setText("Resume with internal player : "+ mins[0] +" min(s) left");
//                            }
                            //playBtn.setText("Resume with internal player : "+hrs+" hour "+ finalMins +" min(s) left");
                        }
                    });
                }
                else
                {
                    if(hrs > 0)
                    {
                        mins[0] = mins[0] %60;
                        openWith.setText("Resume "+hrs+" hour "+ mins[0] +" min(s) left");
                    }
                    else
                    {
                        openWith.setText("Resume "+ mins[0] +" min(s) left");
                    }
                    //openWith.setText("Resume "+ mins[0] +" min(s) left");
                    int finalMins1 = mins[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Button playBtn = findViewById(R.id.playBtn);
//                            if(hrs > 0)
//                            {
//                                mins[0] = mins[0] %60;
//                                playBtn.setText("Resume with internal player : "+hrs+" hour "+ mins[0] +" min(s) left");
//                            }
//                            else
//                            {
//                                playBtn.setText("Resume with internal player : "+ mins[0] +" min(s) left");
//                            }
                            //playBtn.setText("Resume with internal player : "+hrs+" hour "+ finalMins1 +" min(s) left");
                        }
                    });
                }
            }
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshDesc);
        if(getIntent().hasExtra("username"))
        {
            username = getIntent().getStringExtra("username");
            id = getIntent().getStringExtra("id");
        }


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData("");
                pullToRefresh.setRefreshing(false);
            }
        });
        activityResume = "0";
        try {
            card = new JSONObject(getIntent().getStringExtra("description"));
            resumeFlag = getIntent().getStringExtra("resumeFlag");
            name = card.getString("name");
           // activityResume = resumeFlag;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            if(activityResume.equals("0"))
            {
                BackgroundProcess bp = new BackgroundProcess();
                bp.execute(name, Profiles.get_description, resumeFlag);
            }
            else
            {
                BackgroundProcessResume bp = new BackgroundProcessResume();
                bp.execute(name, Profiles.get_description, resumeFlag);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String handleUrl(String URL)
    {
        if(!URL.isEmpty())
        {
            String output = "http:";
            //Log.e("YY", URL);
            String [] splitList = URL.split(output);
            if(splitList.length>1)
            {
                String temp = splitList[1];
                temp = temp.replace("/", "forwardslash");
                temp = temp.replace(" ", "spacebarspace");
                temp = temp.replace("?", "questionmarkquestion");
                temp = temp.replace("&", "emparsandemparsand");
                temp = temp.replace("=", "equaltoequal");
                try {
                    temp= URLEncoder.encode(temp, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                temp = temp.replace("forwardslash", "/");
                temp = temp.replace("spacebarspace", "%20");
                temp = temp.replace("questionmarkquestion", "?");
                temp = temp.replace("emparsandemparsand", "&");
                temp = temp.replace("equaltoequal", "=");

                output += temp;
                return output;
            }
            else
                return URL;
        }
        else
            return URL;

    }

    @SuppressLint("WrongConstant")
    private void processCards(JSONObject card, String username) throws JSONException {

        ImageView imageView = (ImageView) findViewById(R.id.image);

        String album_art_path = card.getString("album_art_path");
        if(!album_art_path.isEmpty())
        {
            Glide.with(getApplicationContext()).load(album_art_path).into(imageView);
            imageUrl = album_art_path;
        }
        TextView showName = findViewById(R.id.showName);
        String name = card.getString("name");
        showName.setText(name);
        String desc = card.getString("des");
        if(!desc.isEmpty())
        {
            TextView description = findViewById(R.id.summary);
            String summary = desc.split("IMDB")[0].trim();
            String rest = "IMDB "+desc.split("IMDB")[1].trim();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }

            description.setText(summary);
            TextView restOfThings = findViewById(R.id.rest);
            restOfThings.setText(rest);

            descriptionStr = description.getText().toString()+"\n\n"+restOfThings.getText().toString();
        }


        ImageButton backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                overridePendingTransition(R.anim.nothing,R.anim.activity_slide_down);
            }
        });

        if(card.has("position") && card.has("duration")) // Coming to resume
        {
            String videoUrl = handleUrl(card.getString("url"));
            Button openWith = findViewById(R.id.playWithBtn);
            ImageView resetShowBtn = findViewById(R.id.resetShowBtn);

            resetShowBtn.setVisibility(View.VISIBLE);

            int dur = 0, pos = 0;

            dur = Integer.parseInt(card.getString("duration"));
            pos = Integer.parseInt(card.getString("position"));

            int rem = dur - pos;
            rem /= 1000;
            int mins = rem/60;
            int hrs = mins/60;
            if(hrs > 0)
            {
                mins = mins%60;
                openWith.setText("Resume "+hrs+" hour "+mins+" min(s) left");
            }
            else
            {
                openWith.setText("Resume "+mins+" min(s) left");
            }
            final int pos1 = pos;
            openWith.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String appPackageName = "com.mxtech.videoplayer.ad";
                    try {
                        resumeFlag = "1";
                        activityResume = "1";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.mxtech.videoplayer.ad");
                        intent.setClassName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen");
                        Uri videoUri = Uri.parse(videoUrl);
                        intent.setDataAndType(videoUri, "application/x-mpegURL");
                        intent.setPackage("com.mxtech.videoplayer.ad"); // com.mxtech.videoplayer.pro
                        intent.putExtra("position", pos1);
                        byte decoder = 1;
                        //intent.putExtra("decode_mode", decoder);
                        //intent.putExtra("fast_mode", true);
                        intent.putExtra("return_result", true);
                        startActivityForResult(intent, 1);

                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(Description.this, "MX Player not installed. Install MX Player" , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });
//            Button playBtn = findViewById(R.id.playBtn);
//
//            if(hrs > 0)
//            {
//                mins = mins%60;
//                playBtn.setText("Resume with internal player : "+hrs+" hour "+mins+" min(s) left");
//            }
//            else
//            {
//                playBtn.setText("Resume with internal player : "+mins+" min(s) left");
//            }
//            //playBtn.setText("Resume with internal player : "+hrs+" hour "+mins+" min(s) left");
//            int finalDur = dur;
//            int finalPos = pos;
//            playBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    /*Intent modakFlixPlayer = ModakFlixPlayer.makeIntent(Description.this);
//                    modakFlixPlayer.putExtra("url", videoUrl);
//                    modakFlixPlayer.putExtra("resume_pos", pos1);
//                    startActivityForResult(modakFlixPlayer, 1);*/
////                    startModakFlixPlayer("0", name, videoUrl, descriptionStr, imageUrl, String.valueOf(finalPos));
//                }
//            });

            resetShowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = Profiles.reset_show+"?username="+username+"&showname="+name;

                    PingUrl pu = new PingUrl();
                    pu.execute(Description.handleUrl(url));
                    Toast.makeText(Description.this, "Show : "+name+" marked as completed", Toast.LENGTH_LONG).show();
                    finish();
                    overridePendingTransition(R.anim.nothing,R.anim.activity_slide_down);
                }
            });
        }
        else    // Not coming to resume
        {
            String videoUrl = handleUrl(card.getString("url"));
//            Button playBtn = findViewById(R.id.playBtn);
//            playBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    /*Intent modakFlixPlayer = ModakFlixPlayer.makeIntent(Description.this);
//                    modakFlixPlayer.putExtra("url", videoUrl);
//                    startActivityForResult(modakFlixPlayer, 1);*/
////                    startModakFlixPlayer("0", name, videoUrl, descriptionStr, imageUrl, "0");
//                }
//            });

            Button openWith = findViewById(R.id.playWithBtn);
            openWith.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String appPackageName = "com.mxtech.videoplayer.ad";
                    try {
                        resumeFlag = "1";
                        activityResume = "1";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.mxtech.videoplayer.ad");
                        intent.setClassName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen");
                        Uri videoUri = Uri.parse(videoUrl);
                        intent.setDataAndType(videoUri, "application/x-mpegURL");
                        int pos = 1000;

                        //intent.putExtra("position", 0000);
                        intent.setPackage("com.mxtech.videoplayer.ad"); // com.mxtech.videoplayer.pro
                        intent.putExtra("position", pos);
                        byte decoder = 1;
                        //intent.putExtra("decode_mode", decoder);
                        //intent.putExtra("fast_mode", true);
                        intent.putExtra("return_result", true);
                        startActivityForResult(intent, 1);

                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(Description.this, "MX Player not installed. Install MX Player" , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });
        }

    }

//    private void startModakFlixPlayer(String... videoInfo)
//    {
//        Bundle bundle = new Bundle();
//        bundle.putString("video_id", videoInfo[0]);
//        bundle.putString("video_name", videoInfo[1]);
//        bundle.putString("video_url", videoInfo[2]);
//        bundle.putString("description", videoInfo[3]);
//        bundle.putString("image_url", videoInfo[4]);
//        bundle.putString("position", videoInfo[5]);
//        bundle.putLong("video_duration", Long.parseLong(videoInfo[0]));
//
//
//        Intent intent = new Intent(Description.this, OnlinePlayerActivity.class);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, 1);
//    }

    private class BackgroundProcess extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... params) {

            String showname = params[0];
            String url = params[1];
            String resumeFlag = params[2];
            JSONObject cards = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cards = Movies.getDataFromServer(handleUrl(url+"?username="+username+"&show="+showname+"&resumeflag="+resumeFlag));

            }

            if(cards != null)
            {
                JSONArray cardArr = null;
                try {
                    cardArr = cards.getJSONArray("cards");
                    JSONObject card = cardArr.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                processCards(card, username);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
        ProgressDialog progressDialog = new ProgressDialog(Description.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
        }
    }

    private class BackgroundProcessResume extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... params) {

            String showname = params[0];
            String url = params[1];
            String resumeFlag = params[2];
            JSONObject cards = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cards = Movies.getDataFromServer(handleUrl(url+"?username="+username+"&show="+showname+"&resumeflag="+resumeFlag));

            }

            if(cards != null)
            {
                JSONArray cardArr = null;
                try {
                    cardArr = cards.getJSONArray("cards");
                    JSONObject card = cardArr.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                processCards(card, username);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityResume = "1";
    }

    private class PostProcess extends AsyncTask<Intent, Void, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Description.this);;

        protected Integer doInBackground(Intent... data) {
            android.os.Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);

            doPostProcess(data[0]);

            return 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            /*progressDialog.dismiss();*/
        }
    }

    private class PingUrl extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... data) {
            Movies.pingDataServer(data[0]);
            return 0;
        }
    }
}




