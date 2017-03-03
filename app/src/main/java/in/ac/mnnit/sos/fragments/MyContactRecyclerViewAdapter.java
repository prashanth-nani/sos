package in.ac.mnnit.sos.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.extras.RoundedImageView;
import in.ac.mnnit.sos.extras.Utils;
import in.ac.mnnit.sos.models.Contact;
import in.ac.mnnit.sos.models.Phone;


public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mContacts;
    private final ContactFragment.OnListFragmentInteractionListener mListener;

    public MyContactRecyclerViewAdapter(List<Contact> contacts, ContactFragment.OnListFragmentInteractionListener listener) {
        mContacts = contacts;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mContacts.get(position);
        Contact contact = mContacts.get(position);
        holder.mContactNameView.setText(mContacts.get(position).getName());
        List<Phone> phones = mContacts.get(position).getPhones();
        String number;
        if(phones.size() > 0)
             number = phones.get(0).getNumber();
        else
            number = null;
        if(number != null)
            holder.mContentView.setText(number);

        byte[] photo = contact.getHighResPhoto();
        if(photo != null){
            Utils utils = new Utils();
            holder.mContactImageView.setImageBitmap(utils.getBitmapFromBytes(photo));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void onContactAddition(List<Contact> contacts){
        mContacts.clear();
        mContacts.addAll(contacts);
        this.notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContactNameView;
        public final TextView mContentView;
        public final RoundedImageView mContactImageView;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContactNameView = (TextView) view.findViewById(R.id.contact_name);
            mContentView = (TextView) view.findViewById(R.id.content);
            mContactImageView = (RoundedImageView) view.findViewById(R.id.contact_photo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
