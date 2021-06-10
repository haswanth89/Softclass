package com.example.softclass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivty extends AppCompatActivity{

    EditText secretcodeBox;
    Button joinBtn,shareBtn;
    JitsiMeetConferenceOptions defaultOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activty);

        secretcodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);


        URL serverURL;

        try{
             serverURL = new URL("https://meet.jit.si");
             defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(true)
                    .build();
        } catch(MalformedURLException e){
            e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options =  new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretcodeBox.getText().toString())
                        .setWelcomePageEnabled(true)
                        .build();
                JitsiMeetActivity.launch(DashboardActivty.this, options);
                new Screen(findViewById(R.id.content).getRootView()).start();

            }
        });

    }

    public void lowernet(View view) {
        startActivity(new Intent(DashboardActivty.this, lowe.class));
    }

    class Screen extends Thread
    {
        View V;
        Screen(View V)
        {
            this.V = V;
        }
        public void takeSS(View takeSS,String fileName)
        {

            try
            {

                String dirPath = Environment.getExternalStorageDirectory().toString()+"/screenshotser";
                File fileDir = new File(dirPath);
                if(!fileDir.exists())
                {
                    boolean mkdir=fileDir.mkdir();
                }
                String path = dirPath+"/"+fileName+".jpeg";
                takeSS.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(takeSS.getDrawingCache());
                takeSS.setDrawingCacheEnabled(false);

                File imageFile = new File(path);
                FileOutputStream outputFile = new FileOutputStream(imageFile);
                int quality=100;
                bitmap.compress(Bitmap.CompressFormat.JPEG,quality,outputFile);
                outputFile.flush();
                outputFile.close();
                return ;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        public void run()
        {
            String k="es";
            while (true){
                try {
                    currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k+="1";
                System.out.println("sarsarle");
                takeSS(V.findViewById(R.id.parent).getRootView(),("rse"+k));
            }
        }
    }
}

