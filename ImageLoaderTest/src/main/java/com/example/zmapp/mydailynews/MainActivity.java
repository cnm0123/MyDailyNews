package com.example.zmapp.mydailynews;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private ImageLoader imageLoader;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/" +
                "superman/img/logo/bd_logo1_31bdc765.png";

        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageLoader = new ImageLoader(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = imageLoader.loadImage(url,l);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
    private ImageLoader.OnLoadImageListener l = new ImageLoader.OnLoadImageListener() {
        @Override
        public void onImageLoadOK(String url, Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoadError(String url) {
            Toast.makeText(MainActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
        }
    };
}
