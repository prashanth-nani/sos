package in.ac.mnnit.sos.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.ac.mnnit.sos.PlacesActivity;
import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.fragments.PlaceFragment.OnListFragmentInteractionListener;
import in.ac.mnnit.sos.models.Place;

import java.util.List;


public class MyPlaceRecyclerViewAdapter extends RecyclerView.Adapter<MyPlaceRecyclerViewAdapter.ViewHolder> {

    private final List<Place> mPlaces;
    private final OnListFragmentInteractionListener mListener;

    public MyPlaceRecyclerViewAdapter(List<Place> places, OnListFragmentInteractionListener listener) {
        mPlaces = places;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mPlace = mPlaces.get(position);
        final Place mPlace = mPlaces.get(position);

        String placeName = mPlace.getName();
        String placeVicinity = mPlace.getVicinity();
        String placeRating = mPlace.getRating();
        String placePhone = mPlace.getPhone();

        if(placeName != null)
            holder.mPlaceName.setText(placeName);
        else
            holder.mPlaceName.setText("N/A");

        if(placeVicinity != null)
            holder.mPlaceVicinity.setText(placeVicinity);
        else
            holder.mPlaceVicinity.setText("N/A");

        String ratingText;
        if(placeRating != null)
            ratingText = "Rating: "+placeRating;
        else
            ratingText = "Rating: N/A";
        holder.mPlaceRating.setText(ratingText);

        if(placePhone == null){
            holder.mPlaceCallButton.setVisibility(View.GONE);
        }

        holder.mPlaceDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mPlace);
                }
            }
        });

        holder.mPlaceDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+ mPlace.getLatitude()+","+mPlace.getLongitude()));
                ((PlacesActivity)mListener).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlaceName;
        public final TextView mPlaceVicinity;
        public final TextView mPlaceRating;
        public final LinearLayout mPlaceCallButton;
        public final LinearLayout mPlaceDirectionButton;
        public final LinearLayout mPlaceDetailsLayout;
        public Place mPlace;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlaceDetailsLayout = (LinearLayout) view.findViewById(R.id.place_details);
            mPlaceName = (TextView) view.findViewById(R.id.place_name);
            mPlaceVicinity = (TextView) view.findViewById(R.id.place_vicinity);
            mPlaceRating = (TextView) view.findViewById(R.id.place_rating);
            mPlaceCallButton = (LinearLayout) view.findViewById(R.id.place_call_button);
            mPlaceDirectionButton = (LinearLayout) view.findViewById(R.id.place_navigate_button);
        }
    }
}
