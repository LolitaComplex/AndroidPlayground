package com.doing.loader.ui;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.doing.loader.R;

import static android.R.attr.data;

public class CursorActivity extends AppCompatActivity {

    protected boolean isChild;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CursorActivity.class));
    }

    public static final int CURSOR_LOADER_ID = 1;
    protected SimpleCursorAdapter mSimpleCursorAdapter;
    protected final String[] CONTACT_NAME_PROJECTTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };
    protected CursorLoader mCursorLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);

        initAdapter();


        if(!isChild){
            mCursorLoader = new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                    CONTACT_NAME_PROJECTTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
            mCursorLoader.registerListener(CURSOR_LOADER_ID, new Loader.OnLoadCompleteListener<Cursor>() {
                @Override
                public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
                    mSimpleCursorAdapter.swapCursor(data);
                }
            });

            mCursorLoader.startLoading();
        }
    }

    protected void initAdapter() {
        mSimpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{android.R.id.text1}, 0);

        ListView listView = (ListView) findViewById(R.id.CursorActivity_lv);
        if (listView != null) {
            listView.setAdapter(mSimpleCursorAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursorLoader.cancelLoad();
    }
}
