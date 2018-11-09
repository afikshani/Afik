package com.example.afikshani.gameofthrones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.FamilyMemberHolder> {

    private ArrayList<FamilyMember> mFamilyTree;

    public ListAdapter(ArrayList<FamilyMember> data) {
        mFamilyTree = data;
    }

    @Override
    public FamilyMemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);

        View familyMemberViewer = inflater.inflate(R.layout.family_member, parent, false);

        return new FamilyMemberHolder(familyMemberViewer);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberHolder holder, int position) {

        TextView textToView = holder.mTextView;
        textToView.setText(mFamilyTree.get(position).getName());
        ImageView pictureToView = holder.mImgaeView;
        pictureToView.setImageDrawable(mFamilyTree.get(position).getImg());
    }

    public static class FamilyMemberHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImgaeView;

        public FamilyMemberHolder(View itemAsViewable) {
            super(itemAsViewable);
            mTextView = itemAsViewable.findViewById(R.id.member_name);
            mImgaeView = itemAsViewable.findViewById(R.id.member_image);
        }
    }


    @Override
    public int getItemCount() {
        return mFamilyTree.size();
    }


}
