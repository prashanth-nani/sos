package in.ac.mnnit.sos.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.extras.RoundedImageView;
import in.ac.mnnit.sos.extras.Utils;
import in.ac.mnnit.sos.models.Contact;
import in.ac.mnnit.sos.models.Phone;


public class MyContactRecyclerViewAdapter
        extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder>
        implements LocalDatabaseAdapter.OnDatabaseChangeListener{

    private final List<Contact> mContacts;
    private final ContactFragment.OnListFragmentInteractionListener mListener;

    public MyContactRecyclerViewAdapter(List<Contact> contacts, ContactFragment.OnListFragmentInteractionListener listener) {
        mContacts = contacts;
        mListener = listener;
        onDatabaseListenerInit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        holder.mContactDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onContactDelete(holder.mItem, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    @Override
    public void onDatabaseListenerInit() {
        LocalDatabaseAdapter.contactsViewAdapter = this;
    }

    @Override
    public void onContactAdd(List<Contact> contacts) {
        mContacts.clear();
        mContacts.addAll(contacts);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onContactDelete(int position) {
        mContacts.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContactNameView;
        public final TextView mContentView;
        public final RoundedImageView mContactImageView;
        public final ImageButton mContactDeleteView;
        public final ImageButton mContactEditView;
        public final ImageButton mContactMoreView;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContactNameView = (TextView) view.findViewById(R.id.contact_name);
            mContentView = (TextView) view.findViewById(R.id.content);
            mContactImageView = (RoundedImageView) view.findViewById(R.id.contact_photo);
            mContactEditView = (ImageButton) view.findViewById(R.id.edit_contact);
            mContactDeleteView = (ImageButton) view.findViewById(R.id.delete_contact);
            mContactMoreView = (ImageButton) view.findViewById(R.id.more_contact_actions);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
