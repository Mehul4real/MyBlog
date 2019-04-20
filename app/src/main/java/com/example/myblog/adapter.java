package com.example.myblog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewholder> {

    Context context;
    ArrayList<Blog> blogs;

    public adapter(Context c, ArrayList<Blog> b){
        context = c;
        blogs = b;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.blog_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(viewholder holder, int i) {
        holder.pTitle.setText(blogs.get(i).getTitle());
        holder.pDesc.setText(blogs.get(i).getDesc());
        holder.pUrl.setText(blogs.get(i).getImage());
        //String Url=blogs.get(i).getImage();
        //Picasso.get().load(Url).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }


    class viewholder extends RecyclerView.ViewHolder{

        TextView pTitle, pDesc,pUrl;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            pUrl=itemView.findViewById(R.id.url);
            pic = itemView.findViewById(R.id.post_img);
            pTitle= itemView.findViewById(R.id.post_title);
            pDesc= itemView.findViewById(R.id.post_decs);

        }


    }
}
