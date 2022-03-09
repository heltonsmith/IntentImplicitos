package com.heltonbustos.ejemplointentimplicito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnWeb, btnContacto, btnMapa, btnCamara, btnMarcador, btnMensaje, btnLlamar;

    private static final int TEL_COD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWeb = findViewById(R.id.btnWeb);
        btnContacto = findViewById(R.id.btnContacto);
        btnMapa = findViewById(R.id.btnMapa);
        btnCamara = findViewById(R.id.btnCamara);
        btnMarcador = findViewById(R.id.btnMarcador);
        btnMensaje = findViewById(R.id.btnMensaje);
        btnLlamar = findViewById(R.id.btnLlamar);

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.cl"));
                startActivity(intent);
            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                startActivity(intent);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:-31.417, -64.183"));
                startActivity(intent);
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        btnMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+5698565245"));
                startActivity(intent);
            }
        });

        btnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:+5698899745"));
                startActivity(intent);
            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = "+56985625654";

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.CALL_PHONE
                            },TEL_COD);
                }
                else{
                    versionesAntiguas(numero);
                }
            }

            private void versionesAntiguas(String numero){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));

                int result = checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
                if(result == PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Acceso a la llamada no autorizado", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case TEL_COD:
                String permisos = permissions[0];
                int result = grantResults[0];
                if(permisos.equals(Manifest.permission.CALL_PHONE)){
                    if (result == PackageManager.PERMISSION_GRANTED){
                        String numero = "+56985625654";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));
                        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED )
                            return;
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, "Acceso a la llamada no autorizada", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}