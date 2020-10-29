package com.example.s01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button courseButton = (Button)findViewById(R.id.courseButton);
        final Button scheduleButton = (Button)findViewById(R.id.scheduleButton);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected  void onPreExecute(){
            target = "http://unixjang0.dothome.co.kr/CourseList.php";
        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private long lastTimeBackPressed;
    @Override
    public  void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
