package com.example.petcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  {

    private ArrayList<Offer> offers;
    final private OnListClickListener onListClickListener;

   public ItemAdapter(ArrayList<Offer> offers,OnListClickListener listClickListener)
    {
        this.offers = offers;
        onListClickListener = listClickListener;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.title.setText(offers.get(position).getTitle());
        holder.price.setText((int) offers.get(position).getPrice()+" DKK");
        holder.localization.setText(offers.get(position).getLocalization());
        holder.img.setImageBitmap(getImageResource(offers.get(position).getPhoto()));
        holder.dates.setText("From: "+offers.get(position).getDateFrom()+"          To: "+offers.get(position).getDateTo());

    }


    private Bitmap getImageResource(byte[] bytes){
       return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView price;
        TextView localization;
        TextView dates;

        ImageView img;

        ViewHolder(View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            price = itemView.findViewById(R.id.priceText);
            img = itemView.findViewById(R.id.imageView);
            localization =itemView.findViewById(R.id.localizationText);
            dates = itemView.findViewById(R.id.datesText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface OnListClickListener{
        void onListItemClick(int clickItemIndex);
    }

}
