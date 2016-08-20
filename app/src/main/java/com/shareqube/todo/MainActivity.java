package com.shareqube.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_TODO_RESULT =  700;
    private static final String LOG_TAG =  MainActivity.class.getSimpleName();
    ArrayList<String> todoItem;
    ArrayAdapter<String> todoItemAdapter ;
    ListView todoListItem;
    Button add_Item ;
    EditText edit_item;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItem = new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean isTodoEmpty = preferences.getBoolean("TODO", true);
        if( !isTodoEmpty) {
            readTodoItem();
        }


        todoListItem = (ListView) findViewById(R.id.listView) ;



        todoItemAdapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItem);

        todoListItem.setAdapter(todoItemAdapter);


        Log.e(LOG_TAG ," count " + todoListItem.getAdapter().getCount());




        edit_item = (EditText) findViewById(R.id.edit_todo) ;

        add_Item = (Button) findViewById(R.id.add_todo_btn) ;
        add_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                todoItemAdapter.add(edit_item.getText().toString());

                edit_item.setText("");

                writeTodoItem();



            }
        });

        todoListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


               Object Item =  adapterView.getItemAtPosition(position);


                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("ITEM_POSITION",position);
                intent.putExtra("ITEM_TITLE" , Item.toString());
                startActivityForResult(intent,  EDIT_TODO_RESULT);

            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                if ( requestCode == EDIT_TODO_RESULT && resultCode == Activity.RESULT_OK) {


                   int todoPosition = data.getIntExtra("TODO_POSITION_RESULT" , -1);

                    String todoTitle  = data.getStringExtra("TODO_TITLE");

                    todoItem.remove(todoPosition);
                    todoItemAdapter.insert(todoTitle , todoPosition);


                    todoItemAdapter.notifyDataSetChanged();
                    writeTodoItem();
                }


    }


    public void readTodoItem(){
        File fileDir = getFilesDir() ;

        File newfile = new File(fileDir , "todo.txt");


        try {

      todoItem = new ArrayList<>(FileUtils.readLines(newfile));

            Log.e(LOG_TAG , "todo item" + todoItem);


        } catch ( IOException e ) {

            Log.e(LOG_TAG ," Errorrr " + e.getMessage());

        }





    }

    public void writeTodoItem(){
        File fileDir = getFilesDir() ;
        File newfile = new File(fileDir , "todo.txt");



        try {
            FileUtils.writeLines(newfile, todoItem);

        } catch ( Exception e ) {
           Log.e(LOG_TAG, e.getMessage());
        }

        // set isTodoEmpty to false

        preferences.edit().putBoolean("TODO", false).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
