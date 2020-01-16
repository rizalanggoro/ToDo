package com.bonfire.todo.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.datas.SubTaskData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSubTaskAdapter extends RecyclerView.Adapter {
  private ArrayList<SubTaskData> subTaskDataArrayList = new ArrayList<>();

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sub_task, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(position, this.subTaskDataArrayList.get(position));
  }

  @Override
  public int getItemCount() {
    return this.subTaskDataArrayList.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.edt_title)
    EditText editTextTitle;

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }

    private void bind(int position, SubTaskData data) {
      this.editTextTitle.setText(data.getTitle());

      this.editTextTitle.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          data.setTitle(charSequence.toString());
          subTaskDataArrayList.remove(position);
          subTaskDataArrayList.add(position, data);
          notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
      });
    }
  }
}
