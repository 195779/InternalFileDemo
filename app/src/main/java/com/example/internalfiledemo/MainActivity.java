package com.example.internalfiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button button_write;
    Button button_read;
    CheckBox checkBox_isAppend;
    EditText editText_entry;
    TextView textView_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_write = (Button) findViewById(R.id.write);
        button_read = (Button) findViewById(R.id.read);
        checkBox_isAppend = (CheckBox) findViewById(R.id.append);
        editText_entry = (EditText) findViewById(R.id.entry);
        textView_display = (TextView) findViewById(R.id.display);


        /*写入文件*/
        button_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText_entry.getText().toString();
                int type = Context.MODE_PRIVATE;
                if(checkBox_isAppend.isChecked()){
                    type = Context.MODE_APPEND;
                }
                save(inputText,type);
            }
        });

        /*读取文件*/
        button_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outputText = load();
                textView_display.setText(outputText);
            }
        });
    }


    protected void save(String inputText,int type){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", type);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    protected String load(){
        StringBuilder content = new StringBuilder();
        FileInputStream in = null;
        BufferedReader reader = null;
        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}