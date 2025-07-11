package com.example.callrandomsms;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    EditText numberInput, messageInput;
    Button addNumberBtn, saveMessageBtn;
    CheckBox randomModeCheckbox;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        messageInput = findViewById(R.id.messageInput);
        addNumberBtn = findViewById(R.id.addNumberBtn);
        saveMessageBtn = findViewById(R.id.saveMessageBtn);
        randomModeCheckbox = findViewById(R.id.randomModeCheckbox);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);

        checkPermissions();

        addNumberBtn.setOnClickListener(v -> {
            String newNumber = numberInput.getText().toString();
            if (!newNumber.isEmpty()) {
                Set<String> currentSet = prefs.getStringSet("number_list", new HashSet<>());
                currentSet.add(newNumber);
                prefs.edit().putStringSet("number_list", currentSet).apply();
                Toast.makeText(this, "شماره ذخیره شد", Toast.LENGTH_SHORT).show();
            }
        });

        saveMessageBtn.setOnClickListener(v -> {
            String msg = messageInput.getText().toString();
            boolean useRandom = randomModeCheckbox.isChecked();
            prefs.edit().putString("message_text", msg).putBoolean("use_random", useRandom).apply();
            Toast.makeText(this, "متن پیام ذخیره شد", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_CALL_LOG
        };
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
                break;
            }
        }
    }
}
