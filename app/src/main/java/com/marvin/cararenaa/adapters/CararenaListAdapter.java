package com.marvin.cararenaa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marvin.cararenaa.R;
import com.marvin.cararenaa.models.Carzarena;
import com.marvin.cararenaa.ui.CararenaDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CararenaListAdapter extends RecyclerView.Adapter<CararenaListAdapter.CararenaViewHolder> {
    private ArrayList<Carzarena> mCarzarenas = new ArrayList<>();
    private Context mContext;

    public CararenaListAdapter(Context context, ArrayList<Carzarena> carzarenas) {
        mContext = context;
        mCarzarenas = carzarenas;
    }

    @Override
    public CararenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        CararenaViewHolder viewHolder = new CararenaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CararenaViewHolder holder, int position) {
        holder.bindRestaurant(mCarzarenas.get(position));
    }

    @Override
    public int getItemCount() {
        return mCarzarenas.size();
    }

    public class CararenaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.buildImageView) ImageView mCararenaImageView;
        @BindView(R.id.carNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.priceTextView) TextView mRatingTextView;
        @BindView(R.id.Body) TextView mBody;
        @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
        @BindView(R.id.Engine) TextView mEngine;






        private Context mContext;

        public CararenaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRestaurant(Carzarena mCarzarenas) {
            Picasso.get().load(mCarzarenas.getPhoto_links()).into(mCararenaImageView);
            mNameTextView.setText("Model :"+mCarzarenas.getMake());
            mNameTextView.setText(mCarzarenas.getMake());
            mCategoryTextView.setText(mCarzarenas.getPhone());
            mBody.setText("Short Trim :" +mCarzarenas.getBody_type() );
            mWebsiteLabel.setText("Interior Color :" +mCarzarenas.getWebsite() );
            mRatingTextView.setText("Year Made: " + mCarzarenas.getPrice() );
            mEngine.setText("Car Price :"+ " "+"$"  +mCarzarenas.getEngine() );
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, CararenaDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("carzarenas", Parcels.wrap(mCarzarenas));
            mContext.startActivity(intent);
        }
    }
}