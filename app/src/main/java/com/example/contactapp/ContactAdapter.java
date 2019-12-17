package com.example.contactapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    public ArrayList<ContactJDO> mContactArrayList;
    private OnItemClickListener mListener;
    private final String TAG = "ContactAdapter";

    public ArrayList<ContactJDO> mSelectedUsersList = new ArrayList<>();

    ContactAdapter(ArrayList<ContactJDO> pContactList, ArrayList<ContactJDO> pMultiSelectList, OnItemClickListener pListener) {
        this.mContactArrayList = pContactList;
        this.mSelectedUsersList = pMultiSelectList;
        mListener = pListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lNameTxt;
        TextView lImageTxt;
        ImageView lContactImage;
        ImageView lSelectImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "MyViewHolder: ");
            lNameTxt = itemView.findViewById(R.id.contactName);
            lContactImage = itemView.findViewById(R.id.contactImage);
            lImageTxt = itemView.findViewById(R.id.imageTxt);
            lSelectImage = itemView.findViewById(R.id.selectImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemLongClick(position);
                        }
                    }
                    return false;
                }
            });

        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactJDO lContactJDO = mContactArrayList.get(position);
        holder.lNameTxt.setText(lContactJDO.getContactName());
        Log.i(TAG, "onBindViewHolder: ");

        if (mSelectedUsersList.contains(mContactArrayList.get(position))) {
            holder.lSelectImage.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(R.drawable.selected_icon).apply(RequestOptions.circleCropTransform()).into(holder.lSelectImage);
            holder.lImageTxt.setVisibility(View.GONE);
            holder.lContactImage.setVisibility(View.GONE);
        } else {
            setContactImage(lContactJDO, holder);
        }
    }

    private void setContactImage(ContactJDO lContactJDO, MyViewHolder holder) {
        if (lContactJDO.getContactPhoto() != null && !lContactJDO.getContactPhoto().equals("")) {
            Glide.with(holder.itemView.getContext()).load(lContactJDO.getContactPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.lContactImage);
            holder.lContactImage.setVisibility(View.VISIBLE);
            holder.lImageTxt.setVisibility(View.GONE);
            holder.lSelectImage.setVisibility(View.GONE);
        } else {
            holder.lImageTxt.setText(String.valueOf(lContactJDO.getContactName().charAt(0)));
            holder.lImageTxt.setVisibility(View.VISIBLE);
            holder.lContactImage.setVisibility(View.GONE);
            holder.lSelectImage.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        if (mContactArrayList != null) {
            return mContactArrayList.size();
        } else {
            return 0;
        }
    }


}
