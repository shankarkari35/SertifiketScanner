package com.example.sertifiketscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    String bhai_value;
    TextView barcoderesult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcoderesult = findViewById(R.id.txtContent);
        isCameraPermissionGranted();
    }

    public void scanBarcode(View view) {
        Intent in = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(in, 0);

    }

    private boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                // Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Toast.makeText(this, "PERMISSION RE_GRANTED", Toast.LENGTH_SHORT).show();
                //Log.v(TAG,"Permission IS revoked");
                // Log.v("permission i")
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else {
            //permisssion automatically granted onsdk<23 upon installation
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                Barcode barcode = data.getParcelableExtra("barcode");
                bhai_value = barcode.displayValue;
                barcoderesult.setText("barcode value" + bhai_value);
                Toast.makeText(this, bhai_value + "", Toast.LENGTH_SHORT).show();
            } else {
                barcoderesult.setText("no barcode Capture");
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}
