package com.example.admin.posts;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by admin on 7/8/2017.
 */

public class PostsAsyncTask extends AsyncTask<String,Void,ArrayList<Posts>> {

    OnDownloadCompleteListener mListener;

    void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        mListener = listener;
    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ArrayList<Posts> doInBackground(String... params) {     // String ... params means any number of argumnets can be there nd they will be stored in params[0],params[1] n so on

        String urlString = params[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // setting up conection
            urlConnection.setRequestMethod("GET");     // request method
            urlConnection.connect();               // connecting
            InputStream inputStream = urlConnection.getInputStream();

            Scanner s = new Scanner(inputStream);
            String str = "";
            while (s.hasNext()) {
                str += s.nextLine();
            }

            Log.i("FetchedString ", str);
            return parseCourses(str);

        } catch (MalformedURLException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Posts> parseCourses(String str) throws JSONException {

        try {
            //JSONObject coursesJson = new JSONObject(str);
            //JSONObject data = coursesJson.getJSONObject("data");  // here there were data courses etc...but since we do not have any of data etc nd we have directly array..therfre only arraylist corresponding to string
            JSONArray coursesJsonArray = new JSONArray(str);

            ArrayList<Posts> courseList = new ArrayList<>();

            for (int i = 0; i < coursesJsonArray.length(); i++) {
                JSONObject courseJson = (JSONObject) coursesJsonArray.get(i);
                int id = courseJson.getInt("id");
                String body = courseJson.getString("body");
                String title = courseJson.getString("title");
                int userId=courseJson.getInt("userId");
                Posts c = new Posts(id,userId,title,body);
                courseList.add(c);

            }

            return courseList;


        } catch (JSONException e) {

        }
        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Posts> courses) {
        super.onPostExecute(courses);
        if (mListener != null) {
            mListener.onDownloadComplete(courses);
        }
    }
}

interface OnDownloadCompleteListener {

    void onDownloadComplete(ArrayList<Posts> coursesList);

}

