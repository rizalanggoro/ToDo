package com.bonfire.todo.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bonfire.todo.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {
  @BindView(R.id.btn_add)
  Button buttonAdd;
  @BindView(R.id.btn_print_log)
  Button buttonPrintLog;

  @BindView(R.id.ln_list)
  LinearLayout linearLayoutList;

  private ArrayList<Data> dataArrayList = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_test);

    this.a();
    this.b();
  }

  private void a() {
    ButterKnife.bind(this);
  }

  private boolean a = true;
  private void b() {
    this.buttonAdd.setOnClickListener(view -> {
      for (int i = 0; i < 5; i++) {
        Data data = new Data();
        data.key = String.valueOf(System.currentTimeMillis());
        data.title = "Some Title Here";
        if (a) {
          data.done = "true";
        } else {
          data.done = "false";
        }
        this.a = !this.a;
        this.dataArrayList.add(data);
        this.add();
      }
    });

    this.buttonPrintLog.setOnClickListener(view -> {
      String gson = new Gson().toJson(this.dataArrayList);
      if (gson.trim().length() > 0) {
        Log.d("log", "\n\n" + gson);
      }
    });
  }

  private void add() {
    View view = LayoutInflater.from(this).inflate(R.layout.list_item_sub_task, null, false);
    if (view != null) {
      this.linearLayoutList.addView(view);

      EditText editTextTitle = (EditText) view.findViewById(R.id.edt_title);
      ImageView imageViewMark = (ImageView) view.findViewById(R.id.img_mark);
      ImageView imageViewDelete = (ImageView) view.findViewById(R.id.img_delete);

      // default
      int indexDefault = this.linearLayoutList.indexOfChild(view);
      if (indexDefault != -1) {
        Data data = this.dataArrayList.get(indexDefault);
        editTextTitle.setText(data.title);
        int res = 0;
        if (data.done.equals("false")) {
          res = R.drawable.mark_undone;
        } else {
          res = R.drawable.mark_done;
        }
        imageViewMark.setImageResource(res);
      } else {
        Log.d("indexDefault", "-1");
      }

      editTextTitle.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          String title = charSequence.toString();
          int index = linearLayoutList.indexOfChild(view);
          if (index != -1) {
            Data data = dataArrayList.get(index);
            data.title = title;
          }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
      });

      imageViewMark.setOnClickListener(view1 -> {
        int index = this.linearLayoutList.indexOfChild(view);
        if (index != -1) {
          Data data = this.dataArrayList.get(index);
          int res = 0;
          if (data.done.equals("true")) {
            // set done to false
            data.done = "false";
            res = R.drawable.mark_undone;
          } else {
            // set done to true
            data.done = "true";
            res = R.drawable.mark_done;
          }
          imageViewMark.setImageResource(res);
        }
      });

      imageViewDelete.setOnClickListener(view1 -> {
        int index = this.linearLayoutList.indexOfChild(view);
        if (index != -1) {
          this.dataArrayList.remove(indexDefault);
          this.linearLayoutList.removeViewAt(indexDefault);
        }
      });
    }
  }

  private class Data {
    String key = "";
    String title = "";
    String done = "";
  }
}
