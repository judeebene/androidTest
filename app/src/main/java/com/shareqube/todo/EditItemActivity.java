package com.shareqube.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText  edit_todo_editText ;
    Button saved_btn ;
    int item_position ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        Bundle b = new Bundle();
        b = getIntent().getExtras();
         item_position  = b.getInt("ITEM_POSITION");
        String item_title = b.getString("ITEM_TITLE") ;


        edit_todo_editText = (EditText) findViewById(R.id.editText_todo) ;

        edit_todo_editText.setText(item_title);

        edit_todo_editText.requestFocus();


        if(edit_todo_editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }



    saved_btn = (Button) findViewById(R.id.saved_btn);

        saved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultTodo = new Intent();

                resultTodo.putExtra("TODO_POSITION_RESULT" ,item_position) ;
                resultTodo.putExtra("TODO_TITLE" , edit_todo_editText.getText().toString());
                setResult(Activity.RESULT_OK, resultTodo);
                finish();
            }
        });


    }
}
