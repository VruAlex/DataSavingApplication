package com.example.datasavingapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.datasavingapplication.R;
import com.example.datasavingapplication.UpdateActivity;
import com.example.datasavingapplication.database.UserDatabaseContract;
import com.example.datasavingapplication.database.UserDatabaseHelper;
import com.example.datasavingapplication.model.UserDetails;

import java.util.List;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.UserViewHolder> {

    List<UserDetails> userDetailsList;
    Context context;
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;


    public UserDetailAdapter(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.frame, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(iteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        UserDetails userDetails = userDetailsList.get(position);
        holder.Text1.setText(userDetails.getOne());
        holder.Text2.setText(userDetails.getTwo());
        holder.Text3.setText(userDetails.getThree());
        holder.Text4.setText(userDetails.getFour());
        holder.Text5.setText(userDetails.getFive());
        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final UserDetails userDetails = userDetailsList.get(position);
                final int userId = userDetails.getUserId();
                dbHelper = new UserDatabaseHelper(context);
                db = dbHelper.getWritableDatabase();
                PopupMenu menu = new PopupMenu(context, holder.ivMenu);
                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Delete:
                                db.delete(UserDatabaseContract.UserDatabase.TABLE_NAME,
                                        UserDatabaseContract.UserDatabase._ID + " = " + userId, null);
                                notifyItemRangeChanged(position, userDetailsList.size());
                                userDetailsList.remove(position);
                                notifyItemRemoved(position);
                                db.close();
                                break;
                            case R.id.Update:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("USERID", userId);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (userDetailsList == null) {
        }
        return userDetailsList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView Text1, Text2, Text3, Text4, Text5;
        Button ivMenu;

        public UserViewHolder(View itemView) {
            super(itemView);
            Text1 = (TextView) itemView.findViewById(R.id.balanceOne);
            Text2 = (TextView) itemView.findViewById(R.id.balanceTwo);
            Text3 = (TextView) itemView.findViewById(R.id.balanceThree);
            Text4 = (TextView) itemView.findViewById(R.id.balanceFour);
            Text5 = (TextView) itemView.findViewById(R.id.balanceFive);
            ivMenu = (Button) itemView.findViewById(R.id.iv_menu);
            ivMenu.setBackgroundResource(R.color.colorAccent);
        }
    }

}










