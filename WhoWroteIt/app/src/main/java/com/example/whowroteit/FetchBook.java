package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText) {
        this.mTitleText = new WeakReference<>(mTitleText);
        this.mAuthorText = new WeakReference<>(mAuthorText);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String title = null, authors = null;
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;

            while (i<itemsArray.length() && (authors == null && title == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                }catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        if(title != null && authors != null) {
            mTitleText.get().setText(title);
            mAuthorText.get().setText(authors);
        } else {
            mTitleText.get().setText("No results");
            mAuthorText.get().setText("");
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

}
