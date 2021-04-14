package com.domker.study.androidstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class GlideActivity extends AppCompatActivity {
    ViewPager pager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        layoutInflater = getLayoutInflater();
        pager = (ViewPager) findViewById(R.id.view_pager);
//        addImage(R.drawable.drawableimage);  //在res/drawable中
//        addImage(R.drawable.ic_markunread);
//        addImage("/sdcard/fileimage.jpg");
//        addImage("file:///android_asset/assetsimage.jpg");  //获取assets下的assetsimage.jpg
//        addImage(R.raw.rawimage);
//        addImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg");
        //添加网络图片：Super Mario
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.shopify.com%2Fs%2Ffiles%2F1%2F0732%2F4767%2Fproducts%2FMario_New_Package_Version_Super_Mario_Brothers_SHFiguarts_Bandai_Tamashii_Nations_Woozy_Moo_1_grande.jpg%3Fv%3D1511983569&refer=http%3A%2F%2Fcdn.shopify.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620978541&t=8bff4954d72289f54333d72492bd5648");
        //动图
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180618%2F1e41747b604b431ca182df0f392872ef.gif&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620978817&t=48f93b585a54ae4286184ecc1ab36849");
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        pager.setAdapter(adapter);
    }

    private void addImage(int resId) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
            .load(resId)
            .error(R.drawable.error)
            .into(imageView);
        pages.add(imageView);
    }

    private void addImage(String path) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
            .load(path)
            .apply(new RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            //.transition(withCrossFade(4000))
            //.override(100, 100)
            .into(imageView);
        pages.add(imageView);
    }
}
