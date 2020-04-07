package com.example.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button open_website_button;
    private Button open_location_button;
    private Button share_text_button;

    private EditText website_edittext;
    private EditText location_edittext;
    private EditText share_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        website_edittext = findViewById(R.id.website_edittext);
        location_edittext = findViewById(R.id.location_edittext);
        share_edittext = findViewById(R.id.share_edittext);

        open_website_button = findViewById(R.id.open_website_button);
        open_location_button = findViewById(R.id.open_location_button);
        share_text_button = findViewById(R.id.share_text_button);
    }

    public void openWebsite(View view) {
        String url = website_edittext.getText().toString();
        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Log.d("implicit intents", "Can't handle this!");
        }
    }

    public void openLocation(View view) {
        String location = location_edittext.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + location);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void shareText(View view) {
        String txt = share_edittext.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.edittext_share)
                .setText(txt)
                .startChooser();

}
}
