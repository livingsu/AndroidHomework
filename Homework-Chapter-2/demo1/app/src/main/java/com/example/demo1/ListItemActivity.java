package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListItemActivity extends AppCompatActivity {
    private TextView textView;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        string=getIntent().getStringExtra("textValue");

        textView=findViewById(R.id.listItem_text);
        textView.setText(string);
    }
}
