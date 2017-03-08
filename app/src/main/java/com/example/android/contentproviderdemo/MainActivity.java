package com.example.android.contentproviderdemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ContentProviderDemo";

    private TextView textViewQueryResult;

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

        textViewQueryResult = (TextView) findViewById(R.id.textViewQueryResult);

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection,
                mSelectionClause,
                mSelectionArguments,
                null);

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
}