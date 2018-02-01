package com.magicrecipe.abhilashdesai.magicrecipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.joooonho.SelectableRoundedImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Abhilash on 01-02-2018.
 */

public class detail extends Activity {

    TextView title,ing,link;
    RoundedImageView dis;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        dis=(RoundedImageView) findViewById(R.id.dis);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ti = extras.getString("title");
        ti=ti.replaceAll("\n","");
        ti=ti.replaceAll("\t","");
        ti=ti.replaceAll("\r","");
        ti=ti+"\n";
        String in = extras.getString("ing");
        in=in+"\n";
        String he = extras.getString("href");
        String th = extras.getString("thumbnail");
        ing=(TextView)findViewById(R.id.ing);

        try {
            new DownLoadImageTask(dis).execute(th);
        }
        catch (Exception x)
        {
            Toast.makeText(getApplicationContext(),x.getMessage(),Toast.LENGTH_LONG).show();
        }
        link=(TextView)findViewById(R.id.link);
        title=(TextView)findViewById(R.id.dissh);
        title.setText(ti);
        ing.setText(in);
        link.setText(he);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(link.getText().toString()));

    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }


        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }


        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}

