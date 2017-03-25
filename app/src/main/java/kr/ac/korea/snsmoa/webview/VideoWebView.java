package kr.ac.korea.snsmoa.webview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.ac.korea.intelligentgallery.R;


/**
 * Created by Noverish on 2016-10-15.
 */

public class VideoWebView extends FrameLayout implements View.OnClickListener{
    private Context context;
    private String imageUrl;
    private String videoUrl;

    public VideoWebView(Context context, String imageUrl, String videoUrl) {
        super(context);
        this.context = context;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;

        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_web_video, this);

        ImageView imageView = (ImageView) findViewById(R.id.view_web_video_src);
        Glide.with(context).load(imageUrl).into(imageView);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url",videoUrl);
        context.startActivity(intent);
    }
}
