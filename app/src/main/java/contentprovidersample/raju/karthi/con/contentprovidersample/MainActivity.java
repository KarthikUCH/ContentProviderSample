package contentprovidersample.raju.karthi.con.contentprovidersample;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import contentprovidersample.raju.karthi.con.contentprovidersample.database.DbConstants;
import contentprovidersample.raju.karthi.con.contentprovidersample.database.DbContract;
import contentprovidersample.raju.karthi.con.contentprovidersample.database.DbManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private DbManager mDbManager;
    private EditText edtItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        intiView();

        mDbManager = DbManager.getInstance(getApplicationContext());
    }

    private void intiView() {
        edtItem = (EditText) findViewById(R.id.edt_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insert(View v)
    {
        new AccessItems().execute("insert");
    }

    public void retrive(View v)
    {
       // new AccessItems().execute("retrive");
        getSupportLoaderManager().initLoader(78,
                new Bundle(), itemsLoader);
    }


    // Access Database directly through SQliteDatabase
    class AccessItems extends AsyncTask<String, Integer, Boolean> {
        String toInsert;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

             toInsert = edtItem.getText().toString();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (TextUtils.equals(params[0], "insert")) {
                    mDbManager.insertItems(toInsert);
                } else {
                    ArrayList<String> items = mDbManager.retriveItems();
                    for(String item : items)
                    {
                        Log.i(TAG, "ITEM "+item);
                    }
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getApplicationContext(), aBoolean ? "Success" : "failiur", Toast.LENGTH_LONG).show();

        }
    }

    // Access database through content provider

    private LoaderManager.LoaderCallbacks<Cursor> itemsLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                    DbContract.Items.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            while (data.moveToNext())
            {
               String item = data.getString(data.getColumnIndex(DbConstants.COL_ITEM_NAME));
                Log.i(TAG, "ITEM "+item);
            }

            data.close();

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
