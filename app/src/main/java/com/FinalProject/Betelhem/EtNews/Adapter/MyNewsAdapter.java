package com.FinalProject.Betelhem.EtNews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.FinalProject.Betelhem.EtNews.Interface.ItemClickListner;
import com.FinalProject.Betelhem.EtNews.Model.Item;
import com.FinalProject.Betelhem.EtNews.PageViewer;
import com.FinalProject.Betelhem.EtNews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.FinalProject.Betelhem.EtNews.common.NewsContract.NewsEntery;

/**
 * Created by voodoo on 11/10/2017.
 */

   class NewsAdapteHOlder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

       public TextView title, pubdate,content,tags;
       private ItemClickListner itemClickListner;

       public NewsAdapteHOlder(View itemview){
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

public class MyNewsAdapter extends RecyclerView.Adapter<NewsAdapteHOlder> {

    String title="", date,url,tags,content;
    List<Item> items;
    private Context mcontext;
    private LayoutInflater inflater;



   public MyNewsAdapter(List<Item> items, Context mcontext) {
        this.items=items;
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);

    }

    @Override
    public NewsAdapteHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = inflater.inflate(R.layout.row, parent, false);

        return new NewsAdapteHOlder(itemview);

    }


    @Override
    public void onBindViewHolder(NewsAdapteHOlder holder, int position) {


        title = items.get(position).getTitle();

        url=items.get(position).getUrl();
        try{
            tags=items.get(position).getTags().get(0).toString();

        }catch (Exception e){
            tags="uncategrorized";
        }
        content= items.get(position).getContent_text().toString();

        //DATE FORMAT
       String unFormateDdate = items.get(position).getDate_published();

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
                    pageviewr.putExtra("urlLink", items.get(position).getUrl());
                    pageviewr.putExtra("title",  items.get(position).getTitle());
                    pageviewr.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(pageviewr);




                }
            }
        });

         if (tags.equals("የሀገር-ውስጥ-ዜናዎች"))
            holder.tags.setTextColor(Color.parseColor("#2196f3"));

       else if (tags.equals("business"))
            holder.tags.setTextColor(Color.parseColor("#FF5722"));

        else if (tags.equals("sport"))
            holder.tags.setTextColor(Color.parseColor("#3F51B5"));

         else if (tags.equals("health"))
             holder.tags.setTextColor(Color.parseColor("#00C853"));
         else if (tags.equals("App"))
             holder.tags.setTextColor(Color.parseColor("#4CAF50"));

         else  if (tags.equals("all"))
            holder.tags.setTextColor(Color.parseColor("#6200EA"));


        else if (tags.equals("uncatagorized"))
            holder.tags.setTextColor(Color.parseColor("#9E9E9E"));



        else{
            holder.tags.setTextColor(Color.parseColor("#FFC107"));
        }


    }

    @Override
    public int getItemCount() {

            return items.size();

        }







}
