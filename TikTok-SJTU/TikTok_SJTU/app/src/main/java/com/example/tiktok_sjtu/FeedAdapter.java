package com.example.tiktok_sjtu;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.tiktok_sjtu.model.Message;
import java.util.List;

import com.bumptech.glide.Glide;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.VideoViewHolder>{
    private List<Message> data;
    public void setData(List<Message> messageList){
        data = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed,parent,false);
        return new VideoViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        private ImageView coverSD;
        private TextView userNameTV;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.tv_username);
            coverSD = itemView.findViewById(R.id.sd_cover);
        }
        public void bind(Message message){
            Glide.with(coverSD)
                    .load(message.getImageUrl())
                    .error(R.drawable.error)
                    .into(coverSD);

            userNameTV.setText("作者: "+message.getUserName());

            //实现点击进入视频界面
            coverSD.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), VideoActivity.class);
                    //传递视频url信息
                    intent.putExtra("videoUrl",message.getVideoUrl());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}
