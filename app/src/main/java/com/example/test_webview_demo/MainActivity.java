package com.example.test_webview_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_advanced);
        initData();
    }

    private void initData() {
        //1.view
        GridView gridView = findViewById(R.id.item_grid);

        //2.data
        ArrayList<HashMap<String, Object>> items = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.index_titles);
        int[] iconResource = {R.drawable.tbsweb, R.drawable.fullscreen, R.drawable.filechooser};

        HashMap<String, Object> item;
        for (int i = 0; i < titles.length; i++) {
            item = new HashMap<>();
            item.put("title", titles[i]);
            item.put("icon", iconResource[i]);
            items.add(item);
        }

        //3.adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                items,
                R.layout.function_block,
                new String[]{"title", "icon"},
                new int[]{R.id.tv_item, R.id.iv_item}
        );

        //4.setAdapter
        gridView.setAdapter(simpleAdapter);

        //5.listener
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> gridView, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0: {
                        intent = new Intent(MainActivity.this, BrowserActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                    case 1: {
                        intent = new Intent(MainActivity.this, FullScreenActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                    case 2: {
                        intent = new Intent(MainActivity.this, FileChooserActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            tbsSuiteExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void tbsSuiteExit() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("X5功能演示");
        dialog.setPositiveButton("OK", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Process.killProcess(Process.myPid());
            }
        });
        dialog.setMessage("quit now?");
        dialog.create().show();
    }
}
