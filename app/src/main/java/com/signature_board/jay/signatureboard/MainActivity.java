package com.signature_board.jay.signatureboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Log";
    RelativeLayout relativeLayout;

    View view;

    Button buttonClear, buttonSave;
    private SignatureBoardView signatureBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativelayout1);

        buttonClear = findViewById(R.id.buttonClear);
        buttonSave = findViewById(R.id.buttonSave);


        signatureBoardView = new SignatureBoardView(MainActivity.this);

        view = signatureBoardView;

        relativeLayout.addView(view, new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));


        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signatureBoardView.getPath().reset();


            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isStoragePermissionGranted()) {

                    //save bitmap
                    File file = savebitmap(view.getDrawingCache());
                    Uri uri = Uri.fromFile(file);

                } else
                    Toast.makeText(getApplicationContext(), "Please give storage write permission", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private File savebitmap(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String filename;
        Date date = new Date(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        filename = sdf.format(date) + ".jpg";
        File file = null;
        try {
            File folder = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "signature");
            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();
            String filePath = folder.toString() + File.separator + filename;

            file = new File(filePath);
            file.deleteOnExit();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.write(bytes.toByteArray());
            fileOutputStream.close();
            Toast.makeText(this,"File Saved",Toast.LENGTH_SHORT).show();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission

            //save bitmap
            File file = savebitmap(view.getDrawingCache());
            Uri uri = Uri.fromFile(file);

        }
    }

}
