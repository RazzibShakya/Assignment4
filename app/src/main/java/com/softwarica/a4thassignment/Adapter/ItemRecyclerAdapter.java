package com.softwarica.a4thassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwarica.a4thassignment.Activity.Description;
import com.softwarica.a4thassignment.Model.Item;
import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemsViewHolder> {
    List<Item> clotheslist;
    Context context;

    public ItemRecyclerAdapter(List<Item> clotheslist, Context context) {
        this.clotheslist = clotheslist;
        this.context = context;
    }
//implementing ItemRecyclerAdapter on RecyclerView
    @NonNull
    @Override
    public ItemRecyclerAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample_row, viewGroup, false);
        return new ItemsViewHolder(itemview);
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
//this will set list of item and images to recycler view
    @Override
    public void onBindViewHolder(@NonNull final ItemRecyclerAdapter.ItemsViewHolder itemsViewHolder, int i) {
        final Item clothes = clotheslist.get(i);
        String imgpath = Url.BASE_URL + "images/" + clothes.getItemImageName();
        System.out.println(imgpath);
        StrictMode();
        try {
            URL ur = new URL(imgpath);
            itemsViewHolder.img.setImageBitmap(BitmapFactory.decodeStream((InputStream) ur.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        itemsViewHolder.item_Name.setText("Name:" + clothes.getItemName());
        itemsViewHolder.item_price.setText("Price:" + clothes.getItemPrice());
        itemsViewHolder.item_des.setText("Description:" + clothes.getItemDescription());
        itemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Description.class);
                intent.putExtra("Item_name", clothes.getItemName());
                intent.putExtra("Item_price", clothes.getItemPrice());
                intent.putExtra("Item_des", clothes.getItemDescription());
                intent.putExtra("Item_img", clothes.getItemImageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return clotheslist.size();
    }
//Creating abstarct class ItemViewHolder
    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView item_Name;
        public TextView item_des;
        public TextView item_price;
        public ImageView img;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            item_Name = itemView.findViewById(R.id.rvItemname);
            item_price = itemView.findViewById(R.id.rvItemPrice);
            item_des = itemView.findViewById(R.id.rvItemDesc);
            img = itemView.findViewById(R.id.rvimage);
        }
    }
}
