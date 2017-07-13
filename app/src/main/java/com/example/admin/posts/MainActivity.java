package com.example.admin.posts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.posts.network.ApiInterface;
import com.example.admin.posts.network.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    ArrayList<Posts> courses;
    ListView courseListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        courseListView = (ListView) findViewById(R.id.courseListView);

        courses = new ArrayList<>();
        courseNames = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseNames);
        courseListView.setAdapter(adapter);
        fetchCourses();

    }

    private void fetchCourses() {

//        String urlString = "https://jsonplaceholder.typicode.com/posts";
//        PostsAsyncTask courseAsyncTask = new PostsAsyncTask();
//        courseAsyncTask.execute(urlString);
//        courseAsyncTask.setOnDownloadCompleteListener(this);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface =  retrofit.create(ApiInterface.class);
        // implements the interface
        Call<ArrayList<User>> call  =  apiInterface.getPosts();

        // apiInterface.getPost(1, 3);

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                ArrayList<User> courseArrayList = response.body();
                onDownloadComplete(courseArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });






    }


    //@Override
    public void onDownloadComplete(ArrayList<User> coursesList) {

        for (int i = 0; i < coursesList.size(); i++) {
            courseNames.add(coursesList.get(i).getTitle());
        }
        adapter.notifyDataSetChanged();

    }
}

//        for(Course c : courses){
//
//        }