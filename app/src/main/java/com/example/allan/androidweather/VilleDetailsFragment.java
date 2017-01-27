package com.example.allan.androidweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment class used to dislpay the contact details.
 */
public class VilleDetailsFragment extends Fragment {

    final static String ITEM_INDEX = "position";

    private int mItemIdx = -1;
    public TextView mNom;
    public TextView mTemperatureActuel;
    public TextView mTemperatureMax;
    public TextView mTemperatureMin;
    public TextView mDescription;

    public VilleDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Restores the views for multi panels version
        if (savedInstanceState != null){
            mItemIdx = savedInstanceState.getInt(ITEM_INDEX);
        }


        View view = inflater.inflate(R.layout.fragment_ville_details, container, false);

        mNom = (TextView) view.findViewById(R.id.ville_details_nom);
        mTemperatureActuel = (TextView) view.findViewById(R.id.ville_details_temp);
        mTemperatureMax = (TextView) view.findViewById(R.id.ville_details_tempMax);
        mTemperatureMin = (TextView) view.findViewById(R.id.ville_details_tempMin);
        mDescription = (TextView) view.findViewById(R.id.ville_details_description);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Sets the views content
        Bundle args = getArguments();
        if (args != null){
            setVilleDetails(args.getInt(ITEM_INDEX));
        } else if(mItemIdx != -1){
            // Sets views form the saved data
            setVilleDetails(mItemIdx);
        }
    }

    public void setVilleDetails(int descriptionIndex){
        Ville ville = ((MainActivity) getActivity()).getVilles().get(descriptionIndex);

        mNom.setText(ville.getNom());
        mTemperatureActuel.setText(String.valueOf(ville.getMeteo().getTemperatureActuelle()));
        mTemperatureMax.setText(String.valueOf(ville.getMeteo().getTemperatureMax()));
        mTemperatureMin.setText(String.valueOf(ville.getMeteo().getTemparatureMinimum()));
        mDescription.setText(ville.getMeteo().getDescription());
        mItemIdx = descriptionIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current index in case we need to recreate the view
        outState.putInt(ITEM_INDEX, mItemIdx);
    }
}

