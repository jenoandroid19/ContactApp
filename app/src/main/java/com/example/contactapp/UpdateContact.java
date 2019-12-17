package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateContact extends AppCompatActivity {

    private RelativeLayout mHomePhnLayout, mMobilePhnLayout, mWorkPhnLayout, mOtherPhnLayout, mHomeEmailLayout, mWorkEmailLayout, mOtherEmailLayout, mHomeAddressLayout, mWorkAddressLayout, mOtherAddressLayout;
    private EditText mViewName, mViewHomePhone, mViewMobilePhone, mViewOtherPhone, mViewWorkPhone, mViewHomeEmail, mViewWorkEmail, mViewOtherEmail, mViewHomeAddress, mViewWorkAddress, mViewOtherAddress;
    private ImageView mBackButton, mAddPhoneTypes, mRemoveHomePhone, mRemoveMobilePhone, mRemoveOtherPhone, mRemoveWorkPhone, mRemoveHomeEmail, mRemoveOtherEmail, mRemoveWorkEmail, mRemoveHomeAddress, mRemoveWorkAddress, mRemoveOtherAddress, mAddEmailTypes, mAddAddressTypes;
    private TextView mSaveButton;
    private String mName, mHomePhone, mWorkPhone, mMobilePhone, mOtherPhone, mHomeEmail, mWorkEmail, mOtherEmail, mHomeAddress, mWorkAddress, mOtherAddress;

    private ArrayList<String> mPhoneOptions = new ArrayList<>();
    private ArrayList<String> mEmailOptions = new ArrayList<>();
    private ArrayList<String> mAddressOptions = new ArrayList<>();
    private ContactJDO mContactJDO;
    private int mContactPosition;
    private String fullString = "";
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        setOptionsList();
        mViewName = findViewById(R.id.update_name);
        mHomePhnLayout = findViewById(R.id.update_home_phone_layout);
        mMobilePhnLayout = findViewById(R.id.update_mobile_phone_layout);
        mOtherPhnLayout = findViewById(R.id.update_other_phone_layout);
        mWorkPhnLayout = findViewById(R.id.update_work_phone_layout);
        mViewHomePhone = findViewById(R.id.update_home_phone);
        mViewMobilePhone = findViewById(R.id.update_mobile_phone);
        mViewOtherPhone = findViewById(R.id.update_other_phone);
        mViewWorkPhone = findViewById(R.id.update_work_phone);
        mHomeEmailLayout = findViewById(R.id.update_home_email_layout);
        mWorkEmailLayout = findViewById(R.id.update_work_email_layout);
        mOtherEmailLayout = findViewById(R.id.update_other_email_layout);
        mViewHomeEmail = findViewById(R.id.update_home_email);
        mViewWorkEmail = findViewById(R.id.update_work_email);
        mViewOtherEmail = findViewById(R.id.update_other_email);
        mHomeAddressLayout = findViewById(R.id.update_home_address_layout);
        mWorkAddressLayout = findViewById(R.id.update_work_address_layout);
        mOtherAddressLayout = findViewById(R.id.update_other_address_layout);
        mViewHomeAddress = findViewById(R.id.update_home_address);
        mViewWorkAddress = findViewById(R.id.update_work_address);
        mViewOtherAddress = findViewById(R.id.update_other_address);
        mSaveButton = findViewById(R.id.save_btn);
        mSaveButton.setVisibility(View.GONE);
        mBackButton = findViewById(R.id.cancel_btn);

        mAddPhoneTypes = findViewById(R.id.update_phn_types);
        mRemoveHomePhone = findViewById(R.id.remove_home_phone);
        mRemoveMobilePhone = findViewById(R.id.remove_mobile_phone);
        mRemoveOtherPhone = findViewById(R.id.remove_other_phone);
        mRemoveWorkPhone = findViewById(R.id.remove_work_phone);
        mRemoveHomeEmail = findViewById(R.id.remove_home_email);
        mRemoveOtherEmail = findViewById(R.id.remove_other_email);
        mRemoveWorkEmail = findViewById(R.id.remove_work_email);
        mRemoveHomeAddress = findViewById(R.id.remove_home_address);
        mRemoveWorkAddress = findViewById(R.id.remove_work_address);
        mRemoveOtherAddress = findViewById(R.id.remove_other_address);
        mAddEmailTypes = findViewById(R.id.update_email_types);
        mAddAddressTypes = findViewById(R.id.update_address_types);
        mContactJDO = getIntent().getExtras().getParcelable("contactObject");
        mContactPosition = getIntent().getExtras().getInt("contactPosition");


        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = mViewName.getText().toString().trim();
                if (!name.isEmpty()) {
                    mSaveButton.setVisibility(View.VISIBLE);
                }
            }
        };

        mViewName.addTextChangedListener(tw);
        mViewHomePhone.addTextChangedListener(tw);
        mViewMobilePhone.addTextChangedListener(tw);
        mViewWorkPhone.addTextChangedListener(tw);
        mViewOtherPhone.addTextChangedListener(tw);
        mViewHomeEmail.addTextChangedListener(tw);
        mViewWorkEmail.addTextChangedListener(tw);
        mViewOtherEmail.addTextChangedListener(tw);
        mViewHomeAddress.addTextChangedListener(tw);
        mViewWorkAddress.addTextChangedListener(tw);
        mViewOtherAddress.addTextChangedListener(tw);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mName = mViewName.getText().toString();
                if (mName.isEmpty()) {
                    Toast.makeText(UpdateContact.this, "Name is required", Toast.LENGTH_SHORT).show();
                } else {
                    onBackPressed();
                }

            }
        });

        mAddAddressTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });

        mAddEmailTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmail();
            }
        });

        mAddPhoneTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhone();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setData();
        removePhoneEmailAddress();
    }

    private void setOptionsList() {
        mPhoneOptions.add("home");
        mPhoneOptions.add("mobile");
        mPhoneOptions.add("work");
        mPhoneOptions.add("other");
        mEmailOptions.add("home");
        mEmailOptions.add("work");
        mEmailOptions.add("other");
        mAddressOptions.addAll(mEmailOptions);
    }

    private void setData() {

        mViewName.setText(mContactJDO.getContactName());
        fullString = fullString + mContactJDO.getContactName();
//Phone
        if (mContactJDO.getContactHomeNumber() != null && !mContactJDO.getContactHomeNumber().equals("")) {
            mViewHomePhone.setText(mContactJDO.getContactHomeNumber());
            fullString = fullString + mContactJDO.getContactHomeNumber();
            mPhoneOptions.remove("home");
            mHomePhnLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactMobileNumber() != null && !mContactJDO.getContactMobileNumber().equals("")) {
            mViewMobilePhone.setText(mContactJDO.getContactMobileNumber());
            fullString = fullString + (mContactJDO.getContactMobileNumber());
            mPhoneOptions.remove("mobile");
            mMobilePhnLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactWorkNumber() != null && !mContactJDO.getContactWorkNumber().equals("")) {
            mViewWorkPhone.setText(mContactJDO.getContactWorkNumber());
            fullString = fullString + (mContactJDO.getContactWorkNumber());
            mPhoneOptions.remove("work");
            mWorkPhnLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactOtherNumber() != null && !mContactJDO.getContactOtherNumber().equals("")) {
            mViewOtherPhone.setText(mContactJDO.getContactOtherNumber());
            fullString = fullString + (mContactJDO.getContactOtherNumber());
            mPhoneOptions.remove("other");
            mOtherPhnLayout.setVisibility(View.VISIBLE);
        }
//Email
        if (mContactJDO.getContactHomeEmail() != null && !mContactJDO.getContactHomeEmail().equals("")) {
            mViewHomeEmail.setText(mContactJDO.getContactHomeEmail());
            fullString = fullString + (mContactJDO.getContactHomeEmail());
            mEmailOptions.remove("home");
            mHomeEmailLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactWorkEmail() != null && !mContactJDO.getContactWorkEmail().equals("")) {
            mViewWorkEmail.setText(mContactJDO.getContactWorkEmail());
            fullString = fullString + (mContactJDO.getContactWorkEmail());
            mEmailOptions.remove("work");
            mWorkEmailLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactOtherEmail() != null && !mContactJDO.getContactOtherEmail().equals("")) {
            mViewOtherEmail.setText(mContactJDO.getContactOtherEmail());
            fullString = fullString + (mContactJDO.getContactOtherEmail());
            mEmailOptions.remove("other");
            mOtherEmailLayout.setVisibility(View.VISIBLE);
        }

//Address

        if (mContactJDO.getContactHomeAddress() != null && !mContactJDO.getContactHomeAddress().equals("")) {
            mViewHomeAddress.setText(mContactJDO.getContactHomeAddress());
            fullString = fullString + (mContactJDO.getContactHomeAddress());
            mAddressOptions.remove("home");
            mHomeAddressLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactWorkAddress() != null && !mContactJDO.getContactWorkAddress().equals("")) {
            mViewWorkAddress.setText(mContactJDO.getContactWorkAddress());
            fullString = fullString + (mContactJDO.getContactWorkAddress());
            mAddressOptions.remove("work");
            mWorkAddressLayout.setVisibility(View.VISIBLE);
        }

        if (mContactJDO.getContactOtherAddress() != null && !mContactJDO.getContactOtherAddress().equals("")) {
            mViewOtherAddress.setText(mContactJDO.getContactOtherAddress());
            fullString = fullString + (mContactJDO.getContactOtherAddress());
            mAddressOptions.remove("other");
            mOtherAddressLayout.setVisibility(View.VISIBLE);
        }

    }

    private void addPhone() {
        if (mPhoneOptions.size() > 0) {
            String lPhone = mPhoneOptions.get(0);
            switch (lPhone) {
                case "home":
                    mHomePhnLayout.setVisibility(View.VISIBLE);
                    mPhoneOptions.remove(lPhone);
                    break;
                case "mobile":
                    mMobilePhnLayout.setVisibility(View.VISIBLE);
                    mPhoneOptions.remove(lPhone);
                    break;
                case "work":
                    mWorkPhnLayout.setVisibility(View.VISIBLE);
                    mPhoneOptions.remove(lPhone);
                    break;
                case "other":
                    mOtherPhnLayout.setVisibility(View.VISIBLE);
                    mPhoneOptions.remove(lPhone);
                    break;
            }
        }

    }

    private void addEmail() {
        if (mEmailOptions.size() > 0) {
            String lEmail = mEmailOptions.get(0);
            switch (lEmail) {
                case "home":
                    mHomeEmailLayout.setVisibility(View.VISIBLE);
                    mEmailOptions.remove(lEmail);
                    break;
                case "work":
                    mWorkEmailLayout.setVisibility(View.VISIBLE);
                    mEmailOptions.remove(lEmail);
                    break;
                case "other":
                    mOtherEmailLayout.setVisibility(View.VISIBLE);
                    mEmailOptions.remove(lEmail);
                    break;
            }
        }

    }

    private void addAddress() {
        if (mAddressOptions.size() > 0) {
            String lAddress = mAddressOptions.get(0);
            switch (lAddress) {
                case "home":
                    mHomeAddressLayout.setVisibility(View.VISIBLE);
                    mAddressOptions.remove(lAddress);
                    break;
                case "work":
                    mWorkAddressLayout.setVisibility(View.VISIBLE);
                    mAddressOptions.remove(lAddress);
                    break;
                case "other":
                    mOtherAddressLayout.setVisibility(View.VISIBLE);
                    mAddressOptions.remove(lAddress);
                    break;
            }
        }

    }

    private void removePhoneEmailAddress() {
        mRemoveHomePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomePhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("home");
                mViewHomePhone.setText("");
            }
        });

        mRemoveWorkPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkPhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("work");
                mViewWorkPhone.setText("");
            }
        });
        mRemoveMobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMobilePhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("mobile");
                mViewMobilePhone.setText("");
            }
        });
        mRemoveOtherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherPhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("other");
                mViewOtherPhone.setText("");
            }
        });

        mRemoveHomeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("home");
                mViewHomeEmail.setText("");
            }
        });

        mRemoveWorkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("work");
                mViewWorkEmail.setText("");
            }
        });
        mRemoveOtherEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("other");
                mViewOtherEmail.setText("");
            }
        });


        mRemoveHomeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("home");
                mViewHomeAddress.setText("");
            }
        });

        mRemoveWorkAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("work");
                mViewWorkAddress.setText("");
            }
        });
        mRemoveOtherAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("other");
                mViewOtherAddress.setText("");
            }
        });
    }

    private class editContactAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getContactDetails();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            setContactDetails();
            MainActivity.mCopyArrayList.set(mContactPosition, new ContactJDO(mContactJDO.getContactID(), mName, mHomePhone, mWorkPhone, mMobilePhone, mOtherPhone, mHomeEmail, mWorkEmail, mOtherEmail, mHomeAddress, mWorkAddress, mOtherAddress, mContactJDO.getContactPhoto()));
            MainActivity.mMyAppDatabase.contactDao().updateContact(contact);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(UpdateContact.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();

        }
    }

    private void getContactDetails() {
        mName = mViewName.getText().toString();
        mHomePhone = mViewHomePhone.getText().toString();
        mWorkPhone = mViewWorkPhone.getText().toString();
        mOtherPhone = mViewOtherPhone.getText().toString();
        mMobilePhone = mViewMobilePhone.getText().toString();
        mHomeEmail = mViewHomeEmail.getText().toString();
        mWorkEmail = mViewWorkEmail.getText().toString();
        mOtherEmail = mViewOtherEmail.getText().toString();
        mHomeAddress = mViewHomeAddress.getText().toString();
        mWorkAddress = mViewWorkAddress.getText().toString();
        mOtherAddress = mViewOtherAddress.getText().toString();
    }


    private void setContactDetails() {
        contact.setContactID(mContactJDO.getContactID());
        contact.setName(mName);
        contact.setHomeNumber(mHomePhone);
        contact.setMobileNumber(mMobilePhone);
        contact.setWorkNumber(mWorkPhone);
        contact.setOtherNumber(mOtherPhone);
        contact.setHomeEmail(mHomeEmail);
        contact.setWorkEmail(mWorkEmail);
        contact.setOtherEmail(mOtherEmail);
        contact.setHomeAddress(mHomeAddress);
        contact.setWorkAddress(mWorkAddress);
        contact.setOtherAddress(mOtherAddress);
        contact.setImageSource(mContactJDO.getContactPhoto());
    }


    @Override
    public void onBackPressed() {

        getContactDetails();
        ContactJDO contactJDO = new ContactJDO(mContactJDO.getContactID(), mName, mHomePhone, mWorkPhone, mMobilePhone, mOtherPhone, mHomeEmail, mWorkEmail, mOtherEmail, mHomeAddress, mWorkAddress, mOtherAddress, mContactJDO.getContactPhoto());

        if (mContactJDO.equals(contactJDO)) {
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to save the changes ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mName = mViewName.getText().toString();
                    if (mName.isEmpty()) {
                        Toast.makeText(UpdateContact.this, "Name is required", Toast.LENGTH_SHORT).show();
                    } else {
                        new editContactAsyncTask().execute();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}
