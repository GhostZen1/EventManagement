package edu.my.utem.ftmk.projecteventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdminAdapter extends RecyclerView.Adapter<UserAdminAdapter.UserViewHolder> {

    private Context context;
    private List<UserAdmin> userList;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public UserAdminAdapter(Context context, List<UserAdmin> userList, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.userList = userList;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.admin_user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        UserAdmin user = userList.get(position);
        holder.tvUserId.setText(String.valueOf(user.getUserId()));
        holder.tvUserName.setText(user.getUsername());
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserRole.setText(user.getRole());

        // Delete button click listener
        holder.ivDeleteUser.setOnClickListener(v -> deleteClickListener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserId, tvUserName, tvUserEmail, tvUserRole;
        public ImageView ivDeleteUser;

        public UserViewHolder(View view) {
            super(view);
            tvUserId = view.findViewById(R.id.tvUserId);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvUserEmail = view.findViewById(R.id.tvUserEmail);
            tvUserRole = view.findViewById(R.id.tvUserRole);
            ivDeleteUser = view.findViewById(R.id.ivDeleteUser);
        }
    }
}
