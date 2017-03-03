package com.doing.loader.ui;

import android.app.LoaderManager;
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

import static com.doing.loader.ui.AsyncActivity.LOADER_ID;

public class BindingLoaderActivity extends CursorActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    {
        isChild = true;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, BindingLoaderActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(CURSOR_LOADER_ID, bundle, this);

        initAdapter();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mCursorLoader =  new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                CONTACT_NAME_PROJECTTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
