package net.serkanbal.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BaseAdapter mToDoListAdapter;
    ListView mToDoListListView;
    List<ToDoList> mToDoList;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToDoList = new ArrayList<>();

        mToDoListAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mToDoList.size();
            }

            @Override
            public Object getItem(int position) {
                return mToDoList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                textView.setText(mToDoList.get(position).getToDoList());

                return convertView;
            }

        };

        //Set the adapter
        mToDoListListView = (ListView) findViewById(R.id.list_view);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mToDoListListView.setAdapter(mToDoListAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                final View promptsView = layoutInflater.inflate(R.layout.dialoglist_layout, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        EditText input = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                                        String inputUser = input.getText().toString();
                                        if (inputUser.isEmpty()) {
                                            input.setError("Be logical!");
                                            Toast.makeText(MainActivity.this, "Can't be empty!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mToDoList.add(new ToDoList(inputUser));
                                            mToDoListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        mToDoListListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please Confirm:")
                        .setMessage("Do you really want to delete: " + mToDoList.get(position).getToDoList()+"?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mToDoList.remove(position);
                                mToDoListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        mToDoListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TodoitemActivity.class);
                startActivity(intent);
            }
        });

    }

}
