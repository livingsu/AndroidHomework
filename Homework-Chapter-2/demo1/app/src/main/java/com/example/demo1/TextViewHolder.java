package com.example.demo1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView textView;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.textItem);
        textView.setOnClickListener(this);
    }

    public void setText(String text){textView.setText(text);}

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),ListItemActivity.class);
        intent.putExtra("textValue",textView.getText().toString());
        v.getContext().startActivity(intent);
    }
}
