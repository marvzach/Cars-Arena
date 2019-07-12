package com.marvin.cararenaa.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marvin.cararenaa.Constants;
import com.marvin.cararenaa.R;
import com.marvin.cararenaa.models.Carzarena;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CararenaDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.buildImageView) ImageView mImageLabel;
    @BindView(R.id.carNameTextView) TextView mNameLabel;
    //    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.priceTextView) TextView mPriceLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.Engine) TextView mEngine;
    @BindView(R.id.Body) TextView mBody;
    @BindView(R.id.categoryTextView) TextView mVH;
    @BindView(R.id.trim) TextView mV;
    @BindView(R.id.savecarButton) Button mSaveCarButton;

    private Carzarena mCarzarena;

    public static CararenaDetailFragment newInstance(Carzarena carzarena) {
        CararenaDetailFragment cararenaDetailFragment = new CararenaDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("carzarena", Parcels.wrap(carzarena));
        cararenaDetailFragment.setArguments(args);
        return cararenaDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarzarena = Parcels.unwrap(getArguments().getParcelable("carzarena"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cararena_detail, container, false);
        ButterKnife.bind(this, view);
        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        Picasso.get().load(mCarzarena.getPhoto_links()).into(mImageLabel);

        mNameLabel.setText(mCarzarena.getMake());

        mPriceLabel.setText(mCarzarena.getPrice() + " "+"is the year made");
        mPhoneLabel.setText("Model :" +mCarzarena.getPhone() );
        mEngine.setText("Car Price:"+" "+"$" +mCarzarena.getEngine());
        mBody.setText("Vehicle type:" +mCarzarena.getVehicle_type() );
        mWebsiteLabel.setText("Interior Color:" +mCarzarena.getWebsite() );
        mVH.setText("Short Trim  :" +mCarzarena.getBody_type() );
        mV.setText("Trim  :" +mCarzarena.getPhoto_links() );
        mAddressLabel.setText(mCarzarena.getMade_in()+ "(" + mCarzarena.getLongitude() +"," + mCarzarena.getLatitude()+ ") is the Location made.");


        mWebsiteLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mSaveCarButton.setOnClickListener(this);
        return view;
    }
    //implicit intents//
    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mCarzarena.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mCarzarena.getLatitude()
                            + "," + mCarzarena.getLongitude()
                            + "?q=(" + mCarzarena.getMake() + ")"));
            startActivity(mapIntent);
        }
        if (v == mPhoneLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("tel:" + mCarzarena.getPhone()));
            startActivity(mapIntent);
        }

        if (v == mSaveCarButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference carfoundRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_FOUND_CARS)
                    .child(uid);
            DatabaseReference pushRef = carfoundRef.push();
            String pushId = pushRef.getKey();
            mCarzarena.setPushId(pushId);
            pushRef.setValue(mCarzarena);
            Toast.makeText(getContext(), "Saved car Details", Toast.LENGTH_SHORT).show();
        }
    }
}


