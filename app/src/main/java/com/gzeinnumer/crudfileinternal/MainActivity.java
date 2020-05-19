package com.gzeinnumer.crudfileinternal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText enterText = findViewById(R.id.enterText);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterText.getText().toString().isEmpty()) {
                    makeFile(enterText.getText().toString());
                } else {
                    enterText.requestFocus();
                    enterText.setError("Tidak Boleh Kosong");
                }
            }
        });

        final EditText readText = findViewById(R.id.readText);
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readText.setText(readFile());
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteFile()){
                    enterText.setText("");
                    readText.setText("");

                    Toast.makeText(MainActivity.this, "Success hapus file", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal hapus file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeFile(String text) {
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "sample.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(text);
            writer.flush();
            writer.close();
            Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Add text to file "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String readFile()
    {
        String myData = "";
        File myExternalFile = new File(MainActivity.this.getFilesDir()+"/text","sample.txt");
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine + "\n";
            }
            br.close();
            in.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Read text to file "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return myData;
    }

    private boolean deleteFile(){
        File file = new File(MainActivity.this.getFilesDir()+"/text","sample.txt");
        return file.delete();
    }
}
