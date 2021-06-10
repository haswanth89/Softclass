package com.example.softclass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class lowe extends AppCompatActivity {
    private StorageReference mst,mw;
    Button Bt;
    ScrollView a;
    ImageView im;
    private ScaleGestureDetector fing;
    private float zoomSize = 1.0f;
    int i =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowe);
        im = ((ImageView)findViewById(R.id.img1));

        Timer t = new Timer();
        t.schedule(new TimerTask() {
                         String k="";
                                  @Override
                                  public void run() {
                                          if(k=="1"){System.out.println("entra idhi");}
                                          imgAd("boy" + k);
                                          k = k+"1";
                                  }
                              },
                0,
                30000);
    }
    public void imgAd(String s)
    {
        try {

            mst = FirebaseStorage.getInstance().getReference().child(s+".jpg");

            final File localfile = File.createTempFile(s,"jpg");
            mst.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(lowe.this,"Pictured Retirieved",Toast.LENGTH_SHORT);
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            im.setImageBitmap(bitmap);
                            fing = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    startActivity(new Intent(lowe.this,downloadpdf.class));
                    Toast.makeText(lowe.this,"Error Occured",Toast.LENGTH_SHORT);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean onTouchEvent(MotionEvent e) {
        fing.onTouchEvent(e);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            zoomSize *= scaleGestureDetector.getScaleFactor();
            zoomSize = Math.max(0.01f, Math.min(zoomSize, 10.0f));
            im.setScaleX(zoomSize);
            im.setScaleY(zoomSize);
            return true;
        }
    }
}