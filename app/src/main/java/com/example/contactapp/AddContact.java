package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Random;

public class AddContact extends AppCompatActivity {

    private TextView mAddContactButton;
    private ImageView mBackButton,mAddPhoneTypes,mRemoveHomePhone,mRemoveMobilePhone,mRemoveOtherPhone,mRemoveWorkPhone,mRemoveHomeEmail,mRemoveOtherEmail,mRemoveWorkEmail,mRemoveHomeAddress,mRemoveWorkAddress,mRemoveOtherAddress,mAddEmailTypes,mAddAddressTypes;
    private EditText mNameTxt,mHomePhoneTxt,mMobilePhoneTxt,mWorkPhoneTxt,mOtherPhoneTxt,mHomeEmailTxt,mWorkEmailTxt,mOtherEmailTxt,mHomeAddressTxt,mWorkAddressTxt,mOtherAddressTxt;
    private RelativeLayout mHomePhnLayout,mMobilePhnLayout,mWorkPhnLayout,mOtherPhnLayout,mHomeEmailLayout,mWorkEmailLayout,mOtherEmailLayout,mHomeAddressLayout,mWorkAddressLayout,mOtherAddressLayout;
    private ArrayList<String> mPhoneOptions = new ArrayList<>();
    private ArrayList<String> mEmailOptions = new ArrayList<>();
    private ArrayList<String> mAddressOptions = new ArrayList<>();
    private String mName, mHomePhone, mWorkPhone, mMobilePhone, mOtherPhone, mHomeEmail, mWorkEmail, mOtherEmail, mHomeAddress, mWorkAddress, mOtherAddress, mContactImage="";
    private long mContactID;
    private int mResultCode = 02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mBackButton = findViewById(R.id.back_btn);
        mHomePhnLayout = findViewById(R.id.remove_homePhone_layout);
        mMobilePhnLayout = findViewById(R.id.remove_mobilePhone_layout);
        mWorkPhnLayout = findViewById(R.id.remove_workPhone_layout);
        mOtherPhnLayout = findViewById(R.id.remove_otherPhone_layout);
        mAddPhoneTypes = findViewById(R.id.add_phn_types);
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
        mAddEmailTypes = findViewById(R.id.add_email_types);
        mAddAddressTypes = findViewById(R.id.add_address_types);
        mHomePhoneTxt = findViewById(R.id.add_home_phone);
        mMobilePhoneTxt = findViewById(R.id.add_mobile_phone);
        mWorkPhoneTxt = findViewById(R.id.add_work_phone);
        mOtherPhoneTxt = findViewById(R.id.add_other_phone);
        mHomeEmailLayout = findViewById(R.id.remove_homeEmail_layout);
        mWorkEmailLayout = findViewById(R.id.remove_workEmail_layout);
        mOtherEmailLayout = findViewById(R.id.remove_otherEmail_layout);
        mHomeEmailTxt = findViewById(R.id.add_home_email);
        mWorkEmailTxt = findViewById(R.id.add_work_email);
        mOtherEmailTxt = findViewById(R.id.add_other_email);
        mHomeAddressTxt = findViewById(R.id.add_home_address);
        mWorkAddressTxt = findViewById(R.id.add_work_address);
        mOtherAddressTxt = findViewById(R.id.add_other_address);
        mHomeAddressLayout = findViewById(R.id.remove_homeAddress_layout);
        mWorkAddressLayout = findViewById(R.id.remove_workAddress_layout);
        mOtherAddressLayout = findViewById(R.id.remove_otherAddress_layout);
        mAddContactButton = findViewById(R.id.add_contact_button);
        mNameTxt = findViewById(R.id.add_name);


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddPhoneTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhone();
            }
        });

        mAddEmailTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmail();
            }
        });

        mAddAddressTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });

        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mNameTxt.getText().toString();
                if (mName.isEmpty()) {
                    Toast.makeText(AddContact.this, "Name is required", Toast.LENGTH_SHORT).show();
                } else {
                    new addContact().execute();
                }
            }
        });

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before > 0 || start > 0){
                    mAddContactButton.setVisibility(View.VISIBLE);
                }else {
                    mAddContactButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };

        mNameTxt.addTextChangedListener(tw);
        mHomePhoneTxt.addTextChangedListener(tw);
        mMobilePhoneTxt.addTextChangedListener(tw);
        mWorkPhoneTxt.addTextChangedListener(tw);
        mOtherPhoneTxt.addTextChangedListener(tw);
        mHomeEmailTxt.addTextChangedListener(tw);
        mWorkEmailTxt.addTextChangedListener(tw);
        mOtherEmailTxt.addTextChangedListener(tw);
        mHomeAddressTxt.addTextChangedListener(tw);
        mWorkAddressTxt.addTextChangedListener(tw);
        mOtherAddressTxt.addTextChangedListener(tw);
        setOptionsList();
        removePhoneEmailAddress();


    }

    private void setOptionsList() {
        mPhoneOptions.add("home");
        mPhoneOptions.add("mobile");
        mPhoneOptions.add("work");
        mPhoneOptions.add("other");
        mEmailOptions.add("work");
        mEmailOptions.add("other");
        mAddressOptions.addAll(mEmailOptions);
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
                mHomePhoneTxt.setText("");
            }
        });

        mRemoveWorkPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkPhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("work");
                mWorkPhoneTxt.setText("");
            }
        });
        mRemoveMobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMobilePhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("mobile");
                mMobilePhoneTxt.setText("");
            }
        });
        mRemoveOtherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherPhnLayout.setVisibility(View.GONE);
                mPhoneOptions.add("other");
                mOtherPhoneTxt.setText("");
            }
        });

        mRemoveHomeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("home");
                mHomeEmailTxt.setText("");
            }
        });

        mRemoveWorkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("work");
                mWorkEmailTxt.setText("");
            }
        });
        mRemoveOtherEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherEmailLayout.setVisibility(View.GONE);
                mEmailOptions.add("other");
                mOtherEmailTxt.setText("");
            }
        });


        mRemoveHomeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("home");
                mHomeAddressTxt.setText("");
            }
        });

        mRemoveWorkAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("work");
                mWorkAddressTxt.setText("");
            }
        });
        mRemoveOtherAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtherAddressLayout.setVisibility(View.GONE);
                mAddressOptions.add("other");
                mOtherAddressTxt.setText("");
            }
        });
    }

    private class addContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Random random = new Random();
            mContactID = random.nextInt(1000);
            mName = mNameTxt.getText().toString();
            mHomePhone = mHomePhoneTxt.getText().toString();
            mWorkPhone = mWorkPhoneTxt.getText().toString();
            mOtherPhone = mOtherPhoneTxt.getText().toString();
            mMobilePhone = mMobilePhoneTxt.getText().toString();
            mHomeEmail = mHomeEmailTxt.getText().toString();
            mWorkEmail = mWorkEmailTxt.getText().toString();
            mOtherEmail = mOtherEmailTxt.getText().toString();
            mHomeAddress = mHomeAddressTxt.getText().toString();
            mWorkAddress = mWorkAddressTxt.getText().toString();
            mOtherAddress = mOtherAddressTxt.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Contact contact = new Contact();
            contact.setContactID(mContactID);
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
            contact.setImageSource(mContactImage);
            MainActivity.mCopyArrayList.add(new ContactJDO(mContactID, mName, mHomePhone, mWorkPhone, mMobilePhone, mOtherPhone, mHomeEmail, mWorkEmail, mOtherEmail, mHomeAddress, mWorkAddress, mOtherAddress,mContactImage));
            MainActivity.mMyAppDatabase.contactDao().addContact(contact);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddContact.this, "Successfully Added", Toast.LENGTH_SHORT).show();
            setResult(mResultCode);
            finish();

        }
    }
}
