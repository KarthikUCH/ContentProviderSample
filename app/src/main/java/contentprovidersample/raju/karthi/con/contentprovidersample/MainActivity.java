package contentprovidersample.raju.karthi.con.contentprovidersample;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
    RecyclerView mRecyclerView;
    ItemAdapter mItemAdapter;
    LinearLayoutManager mLayoutManager;
    private ArrayList<Item> mItemLst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        intiView();
        loadItems();
        mDbManager = DbManager.getInstance(getApplicationContext());
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

    private void intiView() {
        edtItem = (EditText) findViewById(R.id.edt_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        edtItem.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == event.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        insert();
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });
    }

    private void loadItems() {
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemAdapter = new ItemAdapter(mItemLst);
        mRecyclerView.setAdapter(mItemAdapter);
        getSupportLoaderManager().restartLoader(78,
                new Bundle(), itemsLoader);
    }

    public void insert() {
        new InsertItemsTask().execute("insert");
    }


    class InsertItemsTask extends AsyncTask<String, Integer, Boolean> {
        String toInsert;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            toInsert = edtItem.getText().toString();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                mDbManager.providerInsertItems(toInsert);
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
            mItemLst.clear();
            while (data.moveToNext()) {
                String name = data.getString(data.getColumnIndex(DbConstants.COL_ITEM_NAME));
                int id = data.getInt(data.getColumnIndex(DbConstants.COL_ITEM_ID));
                mItemLst.add(new Item(id, name));

            }

            mItemAdapter.notifyDataSetChanged();

            /**
             * In order to get notified by the Content Provider, in case of any change to the Item Table
             * Cursor should not be closed
             */
            // data.close();

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
