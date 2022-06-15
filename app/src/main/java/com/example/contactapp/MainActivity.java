package com.example.contactapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mContactListview;
    private ArrayList<ContactJDO> mOriginalArrayList = new ArrayList<>();
    public static ArrayList<ContactJDO> mCopyArrayList = new ArrayList<>();
    private ArrayList<Contact> mRoomArrayList = new ArrayList<>();
    private ContactAdapter mAdapter;
    public static MyAppDatabase mMyAppDatabase;
    private static final int REQUEST_CONTACT = 325;
    private PermissionUtil mPermissionUtil;
    private EditText mSearchContact;
    private ProgressDialog mProgressDialog;
    private int mRequestCode = 01;
    private int APP_DETAIL_SETTINGS = 11;
    private Paint p = new Paint();
    private LoadContacts mLoadContact;
    private FloatingActionButton mAddContactButton;
    boolean isMultiSelect = false;

    private ActionMode mActionMode;
    private Menu mContextMenu;
    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<ContactJDO> mMultiSelectList = new ArrayList<>();
    boolean isNotFound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContactListview = findViewById(R.id.contact_list);
        mSearchContact = findViewById(R.id.search_contact);
        mAddContactButton = findViewById(R.id.create_contact);
        mPermissionUtil = new PermissionUtil(this);
        mLoadContact = new LoadContacts();

        mSearchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterList(editable.toString());
            }
        });

        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddContact.class), 02);
            }
        });


        askContactPermission();

        enableSwipe();
    }


    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final ContactJDO contactJDO = mCopyArrayList.get(position);
                if (direction == ItemTouchHelper.LEFT) {
                    makeSwipeCall(contactJDO);
                    refreshAdapter();
                } else {
                    makeSwipeMessage(contactJDO);
                    refreshAdapter();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mContactListview);
    }

    private void makeSwipeCall(ContactJDO pContactJDO) {
        if (pContactJDO.getContactHomeNumber() != null && !pContactJDO.getContactHomeNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + pContactJDO.getContactHomeNumber())));

        } else if (pContactJDO.getContactMobileNumber() != null && !pContactJDO.getContactMobileNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + pContactJDO.getContactMobileNumber())));

        } else if (pContactJDO.getContactWorkNumber() != null && !pContactJDO.getContactWorkNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + pContactJDO.getContactWorkNumber())));

        } else if (pContactJDO.getContactOtherNumber() != null && !pContactJDO.getContactOtherNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + pContactJDO.getContactOtherNumber())));

        } else {
            Toast.makeText(this, "No Contact Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeSwipeMessage(ContactJDO pContactJDO) {
        if (pContactJDO.getContactHomeNumber() != null && !pContactJDO.getContactHomeNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pContactJDO.getContactHomeNumber(), null)));

        } else if (pContactJDO.getContactMobileNumber() != null && !pContactJDO.getContactMobileNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pContactJDO.getContactMobileNumber(), null)));

        } else if (pContactJDO.getContactWorkNumber() != null && !pContactJDO.getContactWorkNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pContactJDO.getContactWorkNumber(), null)));

        } else if (pContactJDO.getContactOtherNumber() != null && !pContactJDO.getContactOtherNumber().equals("")) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pContactJDO.getContactOtherNumber(), null)));

        } else {
            Toast.makeText(this, "No Contact Number", Toast.LENGTH_SHORT).show();
        }
    }


    private void askContactPermission() {
        if (checkPermission() != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation();
            } else if (!mPermissionUtil.checkPermissionPreference()) {
                requestPermission();
                mPermissionUtil.updatePermissionPreference();
            } else {
                showPermissionSettings();
            }
        } else {

            mLoadContact.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACT) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLoadContact.execute();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation();
            } else if (!mPermissionUtil.checkPermissionPreference()) {
                requestPermission();
                mPermissionUtil.updatePermissionPreference();
            } else {
                showPermissionSettings();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mRequestCode == requestCode && resultCode == RESULT_OK) {
            updateAdapter();
        } else if (requestCode == APP_DETAIL_SETTINGS) {
            new LoadContacts().execute();
        }else if (requestCode == 02){
            new LoadContacts().execute();
        }

    }

    private class LoadContacts extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            mMyAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "contactDB").build();

            List<Contact> contacts = MainActivity.mMyAppDatabase.contactDao().getContacts();
            if (!contacts.isEmpty()) {
                fetchFromDB();
            } else {
                fetchContentProvider();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (mOriginalArrayList != null && mOriginalArrayList.size() > 0) {


                mAdapter = null;
                updateAdapter();
            } else {
                Toast.makeText(MainActivity.this, "There are no contacts.",
                        Toast.LENGTH_LONG).show();
            }
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.setTitle("Contacts");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

    }

    private void updateAdapter() {
        getSupportActionBar().setSubtitle(mCopyArrayList.size() + " Contacts");
        if (mAdapter == null) {

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
            mContactListview.setLayoutManager(mLayoutManager);

            Collections.sort(mCopyArrayList, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    ContactJDO p1 = (ContactJDO) o1;
                    ContactJDO p2 = (ContactJDO) o2;
                    return p1.getContactName().compareToIgnoreCase(p2.getContactName());
                }
            });
            mAdapter = new ContactAdapter(mCopyArrayList, mMultiSelectList, new ContactAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (isMultiSelect)
                        multiSelect(position);
                    else
                        goToDisplayContact(position);
                }

                @Override
                public void onItemLongClick(int position) {

                    if (!isMultiSelect) {
                        mMultiSelectList = new ArrayList<ContactJDO>();
                        isMultiSelect = true;

                        if (mActionMode == null) {
                            mActionMode = startActionMode(mActionModeCallback);
                        }
                    }

                    multiSelect(position);

                }
            });
            mContactListview.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void multiSelect(int position) {
        if (mActionMode != null) {
            if (mMultiSelectList.contains(mCopyArrayList.get(position))) {
                mMultiSelectList.remove(mCopyArrayList.get(position));
            } else {
                mMultiSelectList.add(mCopyArrayList.get(position));
            }


            if (mMultiSelectList.size() > 0)
                mActionMode.setTitle("" + mMultiSelectList.size() + " Selected");
            else
                mActionMode.finish();

            refreshAdapter();

        }
    }

    public void multiSelectAll() {
        mMultiSelectList.clear();
        if (mActionMode != null) {

            mMultiSelectList.addAll(mCopyArrayList);
            if (mMultiSelectList.size() > 0)
                mActionMode.setTitle("" + mMultiSelectList.size() + " Selected");
            else
                mActionMode.finish();

            refreshAdapter();

        }
    }

    public void refreshAdapter() {
        mAdapter.mSelectedUsersList = mMultiSelectList;
        mAdapter.notifyDataSetChanged();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            mContextMenu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteContacts();
                    return true;
                case R.id.action_all:
                    multiSelectAll();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            mMultiSelectList = new ArrayList<ContactJDO>();
            refreshAdapter();
        }
    };

    private void deleteContacts() {
        final AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setMessage("Delete Contact ?");
        lBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new deleteContactsAsyncTask().execute();
            }
        });

        lBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = lBuilder.create();
        alertDialog.show();

    }

    private class deleteContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ids.clear();
            Contact lContact = new Contact();
            if (mMultiSelectList.size() > 0) {

                for (int i = 0; i < mMultiSelectList.size(); i++) {
                    mCopyArrayList.remove(mMultiSelectList.get(i));
                    lContact.setContactID(mMultiSelectList.get(i).getContactID());
                    mMyAppDatabase.contactDao().deleteContact(lContact);
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateAdapter();
            Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
            mActionMode.finish();
        }
    }

    private void fetchContentProvider() {

        int lCount = 0;

        Uri lUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor lContactsCursor = getContentResolver().query(lUri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        mProgressDialog.setMax(lContactsCursor.getCount());
        if (lContactsCursor.moveToFirst()) {

            do {

                long lContctId = lContactsCursor.getLong(lContactsCursor
                        .getColumnIndex("_ID"));
                mProgressDialog.setProgress(lCount++);
                Uri lDataUri = ContactsContract.Data.CONTENT_URI;

                Cursor lDataCursor = getContentResolver().query(lDataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + lContctId,
                        null, null);

                String lDisplayName = "";
                String lHomePhone = "";
                String lMobilePhone = "";
                String lWorkPhone = "";
                String lOtherPhone = "";

                String lHomeEmail = "";
                String lWorkEmail = "";
                String lOtherEmail = "";

                String lContactNumbers = "";
                String lContactEmailAddresses = "";
                String lContactOtherDetails = "";
                String lHomeAddress = "";
                String lWorkAddress = "";
                String lOtherAddress = "";

                String lPhotoString = "";

                if (lDataCursor.moveToFirst()) {


                    Log.i("DataCount ", String.valueOf(lContactsCursor.getCount()));
                    lDisplayName = lDataCursor
                            .getString(lDataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    do {

                        if (lDataCursor.getString(lDataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (lDataCursor.getInt(lDataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    lHomePhone = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactNumbers += "Home Phone : " + lHomePhone;
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    lWorkPhone = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactNumbers += "Work Phone : " + lWorkPhone;
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    lMobilePhone = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactNumbers += "Mobile Phone : " + lMobilePhone;
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                    lOtherPhone = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactNumbers += "Other Phone : " + lOtherPhone;
                                    break;


                            }
                        }
                        if (lDataCursor.getString(lDataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            switch (lDataCursor.getInt(lDataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    lHomeEmail = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactEmailAddresses += "Home Email : " + lHomeEmail;
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    lWorkEmail = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactEmailAddresses += "Work Email : " + lWorkEmail;
                                    break;

                                case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                    lOtherEmail = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactEmailAddresses += "Other Email : " + lOtherEmail;
                                    break;
                            }
                        }

                        if (lDataCursor.getString(lDataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)) {


                            switch (lDataCursor.getInt(lDataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
                                    lHomeAddress = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactOtherDetails += "Home Address : " + lHomeAddress;
                                    break;
                                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
                                    lWorkAddress = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactOtherDetails += "Work Address : " + lWorkAddress;
                                    break;

                                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER:
                                    lOtherAddress = lDataCursor.getString(lDataCursor
                                            .getColumnIndex("data1"));
                                    lContactOtherDetails += "Other Address : " + lOtherAddress;
                                    break;
                            }

                        }

                        if (lDataCursor
                                .getString(lDataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {


                            lPhotoString = lDataCursor.getString(lDataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                        }

                    } while (lDataCursor.moveToNext());

                }
                Log.i("Contact : ", lContctId + "\n " + lDisplayName + " \nPhone " + lContactNumbers + "\n Email " + lContactEmailAddresses +
                        "\n Address " + lContactOtherDetails + "\n Photo Uri  " + lPhotoString);

                mOriginalArrayList.add(new ContactJDO(lContctId, lDisplayName, lHomePhone, lWorkPhone, lMobilePhone, lOtherPhone, lHomeEmail, lWorkEmail, lOtherEmail, lHomeAddress, lWorkAddress, lOtherAddress, lPhotoString));
                mRoomArrayList.add(new Contact(lContctId, lDisplayName, lHomePhone, lMobilePhone, lWorkAddress, lOtherPhone, lHomeEmail, lWorkEmail, lOtherEmail, lHomeAddress, lWorkAddress, lOtherAddress, lPhotoString));

            } while (lContactsCursor.moveToNext());

        }

        mMyAppDatabase.contactDao().addContacts(mRoomArrayList);
        mCopyArrayList.addAll(new ArrayList<>(mOriginalArrayList));

    }

    private void fetchFromDB() {
        mOriginalArrayList.clear();
        mCopyArrayList.clear();
        List<Contact> lContacts = MainActivity.mMyAppDatabase.contactDao().getContacts();
        for (Contact contact : lContacts) {
            long lId = contact.getContactID();
            String lName = contact.getName();
            String lHomePhone = contact.getHomeNumber();
            String lWorkPhone = contact.getWorkNumber();
            String lMobilePhone = contact.getMobileNumber();
            String lOtherPhone = contact.getOtherNumber();
            String lHomeEmail = contact.getHomeEmail();
            String lWorkEmail = contact.getWorkEmail();
            String lOtherEmail = contact.getOtherEmail();
            String lHomeAddress = contact.getHomeAddress();
            String lWorkAddress = contact.getWorkAddress();
            String lOtherAddress = contact.getOtherAddress();
            String lPhoto = contact.getImageSource();

            mOriginalArrayList.add(new ContactJDO(lId, lName, lHomePhone, lWorkPhone, lMobilePhone, lOtherPhone, lHomeEmail, lWorkEmail, lOtherEmail, lHomeAddress, lWorkAddress, lOtherAddress, lPhoto));

            Log.i("RoomList : ", lId + "\n " + lName + " \nPhone " + lHomePhone + "\n Email " + lHomeEmail +
                    "\n Address " + lHomeAddress + "\n Photo path  " + lPhoto);
        }
        mCopyArrayList.addAll(new ArrayList<>(mOriginalArrayList));


    }

    private void goToDisplayContact(int pPosition) {
        startActivityForResult(new Intent(MainActivity.this, ViewContact.class).putParcelableArrayListExtra("contactObj", mCopyArrayList).putExtra("listPosition", pPosition), mRequestCode);
    }

    private void filterList(String pText) {

        mCopyArrayList.clear();
        if (pText.isEmpty()) {
            mCopyArrayList.addAll(mOriginalArrayList);
        } else {
            for (ContactJDO contactJDO : mOriginalArrayList) {
                if (contactJDO.getContactName().toLowerCase().contains(pText)) {
                    mCopyArrayList.add(contactJDO);
                    isNotFound = true;
                }
            }
        }

        if (mCopyArrayList.isEmpty() && isNotFound){
            Toast.makeText(this, "Record Not Found", Toast.LENGTH_SHORT).show();
            isNotFound = false;
        }
        updateAdapter();
    }

    private int checkPermission() {
        int status = PackageManager.PERMISSION_DENIED;

        status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        return status;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
    }

    private void showPermissionExplanation() {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setMessage("This App need to access your contacts... Please allow");
        lBuilder.setTitle("Contact Permission Needed");
        lBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission();
            }
        });

        lBuilder.setNegativeButton("Close App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = lBuilder.create();
        alertDialog.show();

    }

    private void showPermissionSettings() {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setMessage("To read and update Contacts, allow Phone Book access to your contacts. Tap Settings > Permissions, and turn on contact permission");
        lBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, APP_DETAIL_SETTINGS);
            }
        });

        lBuilder.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = lBuilder.create();
        alertDialog.show();

    }


}
