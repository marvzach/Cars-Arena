package com.marvin.cararenaa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.marvin.cararenaa.R;
import com.marvin.cararenaa.models.Carzarena;
import com.marvin.cararenaa.ui.CararenaDetailActivity;
import com.marvin.cararenaa.util.ItemTouchHelperAdapter;
import com.marvin.cararenaa.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseCararenaListAdapter extends FirebaseRecyclerAdapter<Carzarena, FirebaseViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Carzarena> mCarzarena = new ArrayList<>();


    public FirebaseCararenaListAdapter(FirebaseRecyclerOptions<Carzarena> options,
                                       Query ref,
                                       OnStartDragListener onStartDragListener,
                                       Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mCarzarena.add(dataSnapshot.getValue(Carzarena.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseViewHolder firebaseViewHolder, int position, @NonNull Carzarena car) {
        firebaseViewHolder.bindRestaurant(car);


        firebaseViewHolder.mImageLabe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseViewHolder);
                }
                return false;
            }
        });

        firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CararenaDetailActivity.class);
                intent.putExtra("position", firebaseViewHolder.getAdapterPosition());
                intent.putExtra("carzarenas", Parcels.wrap(mCarzarena));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item_drag, parent, false);
        return new FirebaseViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mCarzarena, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInForebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        mCarzarena.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInForebase(){
        for(Carzarena car: mCarzarena){
            int index = mCarzarena.indexOf(car);
            DatabaseReference mReference = getRef(index);
            car.setIndex(Integer.toString(index));
            mReference.setValue(car);
        }
    }

    @Override
    public void stopListening(){
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }
}
