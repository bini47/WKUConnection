package com.FinalProject.Betelhem.EtNews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.FinalProject.Betelhem.EtNews.Interface.ItemClickListner;
import com.FinalProject.Betelhem.EtNews.PageViewer;
import com.FinalProject.Betelhem.EtNews.R;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.FinalProject.Betelhem.EtNews.common.NewsContract.*;

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

    String title="", date,url,tags,content;
    private Cursor mCursor;
    private Context mcontext;
    private LayoutInflater inflater;



   public MyAdapter(Cursor cursor, Context mcontext) {
        this.mCursor = cursor;
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);

    }

    @Override
    public AdapteHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = inflater.inflate(R.layout.row, parent, false);

        return new AdapteHOlder(itemview);

    }


    @Override
    public void onBindViewHolder(AdapteHOlder holder, int position) {

       if (!mCursor.moveToPosition(position)){
           return;
       }
        title = mCursor.getString(mCursor.getColumnIndex(NewsEntery.COLUMN_TITlE));
        url= mCursor.getString(mCursor.getColumnIndex(NewsEntery.COLUMN_URL));
        tags= mCursor.getString(mCursor.getColumnIndex(NewsEntery.COLUMN_TAG));
        content= mCursor.getString(mCursor.getColumnIndex(NewsEntery.COLUMN_CONTENT));

        //DATE FORMAT
       String unFormateDdate = mCursor.getString(mCursor.getColumnIndex(NewsEntery.COLUMN_DATE));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' at 'HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(unFormateDdate);
        } catch (ParseException e) {
            date=  Calendar.getInstance().getTime();
            e.printStackTrace();
        }

        //SETTING  TEXT ELEMENTS
        holder.title.setText(title);
        holder.pubdate.setText(dateFormat.format(date));
        holder.tags.setText(tags);
        holder.content.setText(content);
        //holder.tags.setTextColor(ContextCompat.getColor(mcontext, R.color.colorAccent));
      //  holder.content.setText(Item.get(position).getContent_html().replace("<p>", "")
               // .replace("</p>", ""));

        holder.setItemClickListner(new ItemClickListner() {

            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {

                    Intent pageviewr = new Intent(mcontext.getApplicationContext(), PageViewer.class);
                    pageviewr.putExtra("urlLink", url);
                    pageviewr.putExtra("title", title);
                    pageviewr.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(pageviewr);




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
         else if (tags.equals("App"))
             holder.tags.setTextColor(Color.parseColor("#4CAF50"));

         else  if (tags.equals("Interior"))
            holder.tags.setTextColor(Color.parseColor("#6200EA"));

        else if (tags.equals("Lifestyle"))
            holder.tags.setTextColor(Color.parseColor("#00BCD4"));

        else if (tags.equals("Travel"))
            holder.tags.setTextColor(Color.parseColor("#00BFA5"));

        else if (tags.equals("uncatagorized"))
            holder.tags.setTextColor(Color.parseColor("#9E9E9E"));



        else{
            holder.tags.setTextColor(Color.parseColor("#FFC107"));
        }


    }

    @Override
    public int getItemCount() {

            return mCursor.getCount();

        }

        public void swapCursor(Cursor newCursor){
            if(mCursor !=null){
                mCursor.close();
            }
            mCursor = newCursor;
            if (newCursor !=null){
                notifyDataSetChanged();
            }
        }




}
