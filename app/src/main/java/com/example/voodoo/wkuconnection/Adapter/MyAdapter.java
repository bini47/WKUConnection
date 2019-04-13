package com.example.voodoo.wkuconnection.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voodoo.wkuconnection.Interface.ItemClickListner;
import com.example.voodoo.wkuconnection.MainActivity;
import com.example.voodoo.wkuconnection.Model.Item;
import com.example.voodoo.wkuconnection.Model.RssObject;
import com.example.voodoo.wkuconnection.PageViewer;
import com.example.voodoo.wkuconnection.R;


import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by voodoo on 11/10/2017.
 */

   class AdapteHOlder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

       public TextView title, pubdate,content,tags;
       private ItemClickListner itemClickListner;

       public AdapteHOlder(View itemview){
           super(itemview);
           title= (TextView) itemview.findViewById(R.id.title);
           pubdate= (TextView) itemview.findViewById(R.id.textPubDate);
           content= (TextView) itemview.findViewById(R.id.content);
           tags= (TextView) itemview.findViewById(R.id.tags);

           itemview.setOnClickListener(this);
           itemview.setOnLongClickListener(this);

       }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View view) {

           itemClickListner.onClick(view, getAdapterPosition(), false);


    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class MyAdapter extends RecyclerView.Adapter<AdapteHOlder> {

    private List<Item> Item;
    private Context mcontext;
    private LayoutInflater inflater;

    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> pubdates = new ArrayList<String>();
    ArrayList<String> contents = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();

    /*SQLiteDatabase db;
    Cursor res;
    int displayed=0;

    int i = 0;
*/
    //DatabseHelper mydb;


    public MyAdapter(List<Item> rssObject, Context mcontext) {
        this.Item = rssObject;
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);


    }



    @Override
    public AdapteHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = inflater.inflate(R.layout.row, parent, false);




        return new AdapteHOlder(itemview);

    }

   /* private void insertArticleData() {
       // mydb = new DatabseHelper(MainActivity.context);

      //  db.execSQL("DROP TABLE IF EXISTS articles ");

*//*       db.execSQL("create table  IF NOT EXISTS articles  (ARTICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,pubdate VARCHAR(50), " +
                "content TEXT, url TEXT, catagory TEXT)");


         db.execSQL("DELETE from articles");
           Log.i("deleted", "deleted succesfully");*//*
      Log.i("size" ,String.valueOf(Item.size()));
       for (int i = 0; i < Item.size(); i++) {

            titles.clear();
            pubdates.clear();
            contents.clear();
            urls.clear();
            tags.clear();

            titles.add( Item.get(i).getTitle());
            pubdates.add(Item.get(i).getPubDate());
            contents.add(Item.get(i).getContent());
            urls.add(Item.get(i).getLink());
            tags.add(Item.get(i).getCategories().get(0));

           *//* String query= ("INSERT INTO articles (title,pubdate,content,url,catagory) VALUES(?,?,?,?,?)");
            SQLiteStatement statement= db.compileStatement(query);

            statement.bindString(1, Item.get(i).getTitle());
            statement.bindString(2, Item.get(i).getPubDate());
            statement.bindString(3,  Item.get(i).getContent());
            statement.bindString(4, Item.get(i).getLink());
            statement.bindString(5, "tag"*//**//*Item.get(i).getCategories().get(0)*//**//*);
            Log.i("inserted","inserted");
            Log.i("tags i", titles.toString());
*//*
        }

    }
*/
    @Override
    public void onBindViewHolder(AdapteHOlder holder, int position) {


        holder.title.setText(Item.get(position).getTitle());
        holder.pubdate.setText(Item.get(position).getDate_published());
         holder.tags.setText("tags");
        //holder.tags.setTextColor(ContextCompat.getColor(mcontext, R.color.colorAccent));
        holder.content.setText(Item.get(position).getContent_html().replace("<p>", "")
                .replace("</p>", ""));

        holder.setItemClickListner(new ItemClickListner() {

            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {

                    String title=Item.get(position).getTitle();
                    String url=Item.get(position).getUrl();

                    Intent pageviewr = new Intent(mcontext.getApplicationContext(), PageViewer.class);
                    pageviewr.putExtra("urlLink", url);
                    pageviewr.putExtra("title", title);
                    pageviewr.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(pageviewr);

                    Log.i("rss", Item.get(position).getUrl());



                }
            }
        });

         if (tags.equals("Applications"))
            holder.tags.setTextColor(Color.parseColor("#2196f3"));


       else if (tags.equals("Automobile"))
            holder.tags.setTextColor(Color.parseColor("#FF5722"));

        else if (tags.equals("Business"))
            holder.tags.setTextColor(Color.parseColor("#3F51B5"));

        else if (tags.equals("Envato"))
            holder.tags.setTextColor(Color.parseColor("#00C853"));

        else  if (tags.equals("Interior"))
            holder.tags.setTextColor(Color.parseColor("#6200EA"));

        else if (tags.equals("Lifestyle"))
            holder.tags.setTextColor(Color.parseColor("#00BCD4"));

        else if (tags.equals("Travel"))
            holder.tags.setTextColor(Color.parseColor("#00BFA5"));

        else if (tags.equals("Uncategorised"))
            holder.tags.setTextColor(Color.parseColor("#9E9E9E"));



        else{
            holder.tags.setTextColor(Color.parseColor("#FFC107"));
        }








    }

    @Override
    public int getItemCount() {


/*
        if (displayed<2) {

            displayed++;
            db = mcontext.openOrCreateDatabase("articledb", Context.MODE_PRIVATE, null);
            db.execSQL("create table  IF NOT EXISTS articles  (ARTICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,pubdate VARCHAR(50), " +
                    "content TEXT, url TEXT, catagory TEXT)");

            res = db.rawQuery("select * from articles", null);
            try {

                if (isNetworkAvailable()) {
                    insertArticleData();

                    Log.i("called", "called Here"+displayed);


                }
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.context, "No data available", Toast.LENGTH_SHORT).show();

                } else {
                    getAllData();
                    Log.i("twice", titles.toString());
                    Log.i("tags", tags.toString());
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", e.getMessage());


            }
        }*/
            return Item.size();

        }


    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager =(ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo= connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();

    }
}
