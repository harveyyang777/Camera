package com.example.harvey.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置屏幕不随手机旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    public void takePhoto(View v){

       // Toast.makeText(this,"take photo",Toast.LENGTH_LONG).show();
        Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivity(it);
        startActivityForResult(it,100);

    }

    public void onPick(View v){
        Intent it=new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it,101);

    }

    public void send(View v){
        if (imguri!=null){
            Intent it=new Intent(Intent.ACTION_SEND);
            it.setType("image/*");
            it.putExtra(Intent.EXTRA_STREAM,imguri);
            startActivity(it);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

           // Toast.makeText(this,"log1",Toast.LENGTH_LONG).show();

            switch (requestCode){
                case 100:
                    //    Toast.makeText(this,"log2",Toast.LENGTH_LONG).show();
                    /*
                    Bundle extras=data.getExtras();
                    Bitmap bmp=(Bitmap)extras.get("data");

                    ImageView imv=(ImageView)findViewById(R.id.imageView);
                    imv.setImageBitmap(bmp);
                    */

                    break;
                case 101:
                    imguri=convertUri(data.getData());
                    String s=imguri.getPath().toString();
                    Toast.makeText(this,s,Toast.LENGTH_LONG).show();
                    break;

            }


            showImg();

        }
        else{
            Toast.makeText(this,"take no photo",Toast.LENGTH_LONG).show();
        }
    }

    private void showImg() {

       // Toast.makeText(this,"showImg",Toast.LENGTH_LONG).show();

      //  Toast.makeText(this,data.toString(),Toast.LENGTH_LONG).show();

       // Bundle extras=data.getExtras();

      //  Bitmap bmp=(Bitmap)extras.get("data");
        Bitmap bmp= BitmapFactory.decodeFile(imguri.getPath());



       //Toast.makeText(this,bmp.toString(),Toast.LENGTH_LONG).show();
        ImageView imv=(ImageView)findViewById(R.id.imageView);

        TextView txv=(TextView) findViewById(R.id.textView);
        txv.setText(imguri.getPath().toString());
       // imv.setImageBitmap(bmp);
    }

    //加入转换uri的convertUri方法，将content://开头的uri转换为file开头的uri
    Uri convertUri(Uri uri){
        if(uri.toString().substring(0,7).equalsIgnoreCase("content")){

            String[] colName={MediaStore.MediaColumns.DATA};
            Cursor cursor=getContentResolver().query(uri,colName,null,null,null);
            cursor.moveToFirst();
            uri=Uri.parse("file://"+cursor.getString(0));
            cursor.close();
        }
        return uri;
    }


}
