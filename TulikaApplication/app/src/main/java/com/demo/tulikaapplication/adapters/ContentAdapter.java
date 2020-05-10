package com.demo.tulikaapplication.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;

import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.tulikaapplication.R;
import com.demo.tulikaapplication.models.ContentItems;

import java.util.ArrayList;

import static com.demo.tulikaapplication.constants.Constants.poster_im;
import static com.demo.tulikaapplication.constants.Constants.poster_im_list;
import static com.demo.tulikaapplication.constants.Constants.poster_name;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> implements Filterable {

    private ArrayList<ContentItems> list;
    private ArrayList<ContentItems> mFilteredList;
    private Context ctx;

    public ContentAdapter(ArrayList<ContentItems> Data, Context context) {
        list = Data;
        mFilteredList = Data;
        this.ctx=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_list_item, parent, false);
        view.getLayoutParams().width = (int) (getScreenWidth() /2); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.content_tv.setText(mFilteredList.get(position).getName());
        for(int i=0;i<poster_name.length;i++){
            poster_im_list.put(poster_name[i],poster_im[i]);
        }

        if(poster_im_list.containsKey(mFilteredList.get(position).getPoster_image())){
            holder.content_imv.setImageResource(poster_im_list.get(list.get(position).getPoster_image()));
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
    public int getScreenWidth() {

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {

                    ArrayList<ContentItems> filteredList = new ArrayList<>();

                    for (ContentItems contentItem : list) {

                        if (contentItem.getName().toLowerCase().contains(charString)) {

                            filteredList.add(contentItem);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContentItems>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

      TextView content_tv;
      ImageView content_imv;

        public MyViewHolder(View v) {
            super(v);
            content_tv = (TextView) v.findViewById(R.id.content_textview);
            content_imv = (ImageView) v.findViewById(R.id.content_imageview);


        }
    }
}
