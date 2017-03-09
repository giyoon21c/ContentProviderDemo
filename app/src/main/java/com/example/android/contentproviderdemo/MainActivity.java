package com.example.android.contentproviderdemo;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ContentProviderDemo";

    private TextView textViewQueryResult;
    private Button buttonLoadData;

    private boolean firstTimeLoaded = false;

    private String[] mColumnProjection = new String[] {
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.Contacts.CONTACT_STATUS,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
    };

    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";

    private String[] mSelectionArguments = new String[] {"John Doe"};

    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLoadData = (Button) findViewById(R.id.buttonLoadData);

        // LoaderManager is initialized when a button is clicked
        buttonLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button pressed");
                getLoaderManager().initLoader(1, null, MainActivity.this); // this creates onCreateLoader
            }
        });

        textViewQueryResult = (TextView) findViewById(R.id.textViewQueryResult);

    }

    // three messages that override LoaderCallbacks<Cursor>
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i == 1) {
            return new CursorLoader(MainActivity.this, ContactsContract.Contacts.CONTENT_URI,
                    mColumnProjection,
                    null,
                    null,
                    null);
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if ((cursor != null) && (cursor.getCount() > 0)) {
            StringBuilder stringBuilderQueryResult= new StringBuilder("");
            while (cursor.moveToNext()) {
                stringBuilderQueryResult.append(cursor.getString(0) + ", " +
                        cursor.getString(1) + ", " +
                        cursor.getString(2) + "\n");
            }
            textViewQueryResult.setText(stringBuilderQueryResult.toString());
        } else {
            textViewQueryResult.setText("No contact in device");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
