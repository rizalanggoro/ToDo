package com.bonfire.todo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleAdapter extends RecyclerView.Adapter {

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).a(position);
  }

  @Override
  public int getItemCount() {
    return 30;
  }

  public class Holder extends RecyclerView.ViewHolder {

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }

    private void a(int position) {
      if (position == 0) {
        // use div top
      } else if (position == 30 - 1) {
        // use div bottom
      }
    }
  }
}
