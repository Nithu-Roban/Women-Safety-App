package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button blogin, bregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blogin = (Button) findViewById(R.id.blogin);
        bregister = (Button) findViewById(R.id.bregister);
        Intent intent = getIntent();
        ImageView mimageView = (ImageView) findViewById(R.id.img5);
        ImageView mimageView1 = (ImageView) findViewById(R.id.img4);



//Google icon border radius

        Bitmap mbitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.gicon)).getBitmap();
        Bitmap imageRounded1 = Bitmap.createBitmap(mbitmap1.getWidth(), mbitmap1.getHeight(), mbitmap1.getConfig());
        Canvas canvas1 = new Canvas(imageRounded1);
        Paint mpaint1 = new Paint();
        mpaint1.setAntiAlias(true);
        mpaint1.setShader(new BitmapShader(mbitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas1.drawRoundRect((new RectF(0, 0, mbitmap1.getWidth(), mbitmap1.getHeight())), 100, 100, mpaint1);// Round Image Corner 100 100 100 100
        mimageView1.setImageBitmap(imageRounded1);

//facebook icon border radius

        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ficon)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100
        mimageView.setImageBitmap(imageRounded);
    }

    public  void OnRegister(View view){
        Intent intent= new Intent(MainActivity.this, registerActivity.class);

        startActivity(intent);
    }

    public void OnLogin(View view){
        Intent intent = new Intent(MainActivity.this, loginActivity.class);
        startActivity(intent);

    }
//
}