package com.example.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnWrite, btnRead;
    TextView tv;
    String folderLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tv = findViewById(R.id.tv);


        if (checkPermission()) {
            String folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
            File folder = new File(folderLocation);
            if (folder.exists() == false) {
                boolean result = folder.mkdir();
                if (result == true) {
                    Log.d("File Read/ Write", "Folder Created");

                }
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }


        //btnWrite
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String folderLocation_I = getFilesDir().getAbsolutePath() + "/Test";
                    File targetFile_I = new File(folderLocation_I, "test.txt");
                    FileWriter writer_I = new FileWriter(targetFile_I, true);
                    writer_I.write("test data" + "\n");
                    writer_I.flush();
                    writer_I.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        //btnRead
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
                File targetFile = new File(folderLocation, "test.txt");

                if (targetFile.exists() == true) {
                    String data = "";
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);

                        String line = br.readLine();
                        while (line != null) {
                            data += line + "\n";
                            line = br.readLine();
                        }
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    tv.setText(data);
                }
            }
        });

    }


        //check for permission
        private boolean checkPermission() {
            int permissionCheck_Write = ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionCheck_Read = ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED
                    && permissionCheck_Read == PermissionChecker.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                return false;

            }
        }
    }
