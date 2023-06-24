package edu.poly.assigment_ph26023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ManHinhChaoActivity extends AppCompatActivity {
    private ImageView img_anhCho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        img_anhCho = findViewById(R.id.img_anhManHinhCho);

        // câu lệnh dùng thư viện glide để load ảnh
        Glide.with(this).load(R.drawable.anhhoctap).into(img_anhCho);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManHinhChaoActivity.this, DangNhapActivity.class);
                startActivity(intent);

                finish();
            }
        }, 1000);

    }
}