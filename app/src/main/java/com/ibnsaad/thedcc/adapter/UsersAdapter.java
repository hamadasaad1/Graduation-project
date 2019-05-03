package com.ibnsaad.thedcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnsaad.thedcc.DetailsActivity;
import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.model.Users;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private List<Users> mUsersList;
    private Context mContext;

    public UsersAdapter(List<Users> mUsersList, Context mContext) {
        this.mUsersList = mUsersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_layout_users, viewGroup, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int i) {

        userHolder.bindUses(mUsersList.get(i));
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }


    public class UserHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user_name)
        TextView mUserName;
        @BindView(R.id.image_users)
        ImageView mImageProfile;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion=getAdapterPosition();
                    if (postion !=RecyclerView.NO_POSITION) {
                        Users usersCliked = mUsersList.get(postion);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("name", mUsersList.get(postion).getUsername());
                        //to check about image profile before send data to anther activity
                        if (mUsersList.get(postion).getPhotoUrl() !=null) {
                            intent.putExtra("profile", mUsersList.get(postion).getPhotoUrl().toString());

                        }
                        intent.putExtra("birthday", mUsersList.get(postion).getDateOfBirth());
                        intent.putExtra("city", mUsersList.get(postion).getCity());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, usersCliked.getUsername()
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void bindUses(Users users) {
            mUserName.setText(users.getUsername());

            if (users.getPhotoUrl()==null){

            }else {
                Picasso.get()
                        .load(users.getPhotoUrl().toString())
                        .placeholder(R.drawable.profile)
                        .into(mImageProfile);
            }
        }

    }
}
