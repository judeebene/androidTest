package com.shareqube.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
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
    ArrayList<String> todoItem;
    ArrayAdapter<String> todoItemAdapter ;
    ListView todoListItem;
    Button add_Item ;
    EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readTodoItem();

        todoListItem = (ListView) findViewById(R.id.listView) ;

        todoItem = new ArrayList<>();

        todoItemAdapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItem);

        todoListItem.setAdapter(todoItemAdapter);

        todoItem.add("Item one");
        todoItem.add("item two");


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

                String pos  = todoListItem.getAdapter().getItem(position).toString();




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
            BufferedReader br = new BufferedReader(new FileReader(newfile));
            String todo;

            while ((todo = br.readLine()) != null) {
                todoItem = new ArrayList<String>();
                todoItem.add(todo);

            }
            br.close();
        }
        catch (IOException e) {
            todoItem = new ArrayList<String>();

        }
    }

    public void writeTodoItem(){
        File fileDir = getFilesDir() ;
        File newfile = new File(fileDir , "todo.txt");

        try {
            // create a file in downloads directory
            FileOutputStream fos =
                    new FileOutputStream(
                            new File(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS), newfile.getName())
                    );
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(todoItem);
            os.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            ;
        }
    }
}
