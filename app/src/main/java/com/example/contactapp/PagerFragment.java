package com.example.contactapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import static android.app.Activity.RESULT_OK;

public class PagerFragment extends Fragment {

    private ImageView mViewContactImage, mHomeCall, mWorkCall, mOtherCall, mMobileCall, mHomeSms, mWorkSms, mOtherSms, mMobileSms, mHomeEmailView, mWorkEmailView, mOtherEmailView;
    private RelativeLayout mHomePhnTxt, mWorkPhnTxt, mMobilePhnTxt, mOtherPhnTxt, mHomeEmailTxt, mWorkEmailTxt, mOtherEmailTxt, mDeleteContact, mEditContact;
    private TextView mHomeAddressTxt, mWorkAddressTxt, mOtherAddressTxt, mViewHomePhone, mViewMobilePhone, mViewOtherPhone, mViewWorkPhone, mViewHomeEmail, mViewWorkEmail, mViewOtherEmail, mViewHomeAddress, mViewWorkAddress, mViewOtherAddress;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ContactJDO mContactJDO;
    private int mRequestCode = 01;
    private final String TAG = "PagerFragment";
    private int mPosition;
    public PagerFragment(ContactJDO pContactJDO, int pPosition) {
        mContactJDO =pContactJDO;
        mPosition = pPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_pager_list, container,
                false);

        mCollapsingToolbarLayout = layoutView.findViewById(R.id.collapsing_toolbar);
        mViewContactImage = layoutView.findViewById(R.id.view_contact_image);

        mHomePhnTxt = layoutView.findViewById(R.id.home_phone_layout);
        mMobilePhnTxt = layoutView.findViewById(R.id.mobile_phone_layout);
        mOtherPhnTxt = layoutView.findViewById(R.id.other_phone_layout);
        mWorkPhnTxt = layoutView.findViewById(R.id.work_phone_layout);
        mViewHomePhone = layoutView.findViewById(R.id.view_home_phone);
        mViewMobilePhone = layoutView.findViewById(R.id.view_mobile_phone);
        mViewOtherPhone = layoutView.findViewById(R.id.view_other_phone);
        mViewWorkPhone = layoutView.findViewById(R.id.view_work_phone);

        mHomeEmailTxt = layoutView.findViewById(R.id.home_email_layout);
        mWorkEmailTxt = layoutView.findViewById(R.id.work_email_layout);
        mOtherEmailTxt = layoutView.findViewById(R.id.other_email_layout);
        mViewHomeEmail = layoutView.findViewById(R.id.view_home_email);
        mViewWorkEmail = layoutView.findViewById(R.id.view_work_email);
        mViewOtherEmail = layoutView.findViewById(R.id.view_other_email);

        mHomeAddressTxt = layoutView.findViewById(R.id.home_address_txt);
        mWorkAddressTxt = layoutView.findViewById(R.id.work_address_txt);
        mOtherAddressTxt = layoutView.findViewById(R.id.other_address_txt);

        mViewHomeAddress = layoutView.findViewById(R.id.view_home_address);
        mViewWorkAddress = layoutView.findViewById(R.id.view_work_address);
        mViewOtherAddress = layoutView.findViewById(R.id.view_other_address);
        mHomeCall = layoutView.findViewById(R.id.home_call);
        mMobileCall = layoutView.findViewById(R.id.mobile_call);
        mHomeSms = layoutView.findViewById(R.id.home_sms);
        mMobileSms = layoutView.findViewById(R.id.mobile_sms);
        mWorkCall = layoutView.findViewById(R.id.work_call);
        mOtherCall = layoutView.findViewById(R.id.other_call);
        mWorkSms = layoutView.findViewById(R.id.work_sms);
        mOtherSms = layoutView.findViewById(R.id.other_sms);
        mHomeEmailView = layoutView.findViewById(R.id.home_email);
        mWorkEmailView = layoutView.findViewById(R.id.work_email);
        mOtherEmailView = layoutView.findViewById(R.id.other_email);
        mDeleteContact = layoutView.findViewById(R.id.delete_contact);
        mEditContact = layoutView.findViewById(R.id.edit_contact);

        mDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContactItem();
            }

        });

        mEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateData();
            }
        });

        Log.d(TAG, "onCreateView: ");
        getData();

        return layoutView;
    }

    private void getData() {
        mCollapsingToolbarLayout.setTitle(mContactJDO.getContactName());

        if (mContactJDO.getContactPhoto() != null && !mContactJDO.getContactPhoto().equals("")) {

            Glide.with(this).load(mContactJDO.getContactPhoto()).apply(RequestOptions.circleCropTransform()).into(mViewContactImage);

        } else {
            Glide.with(this).load(getResources().getDrawable(R.drawable.camera)).apply(RequestOptions.circleCropTransform()).into(mViewContactImage);
        }

        if (mContactJDO.getContactHomeNumber() != null && !mContactJDO.getContactHomeNumber().equals("") ) {
            mViewHomePhone.setText(mContactJDO.getContactHomeNumber());
        } else {
            mHomePhnTxt.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactMobileNumber() != null && !mContactJDO.getContactMobileNumber().equals("") ) {
            mViewMobilePhone.setText(mContactJDO.getContactMobileNumber());
        } else {
            mMobilePhnTxt.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactWorkNumber() != null && !mContactJDO.getContactWorkNumber().equals("")) {
            mViewWorkPhone.setText(mContactJDO.getContactWorkNumber());
        } else {
            mWorkPhnTxt.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactOtherNumber() != null && !mContactJDO.getContactOtherNumber().equals("")) {
            mViewOtherPhone.setText(mContactJDO.getContactOtherNumber());
        } else {
            mOtherPhnTxt.setVisibility(View.GONE);
        }
//Email
        if (mContactJDO.getContactHomeEmail() != null && !mContactJDO.getContactHomeEmail().equals("")) {
            mViewHomeEmail.setText(mContactJDO.getContactHomeEmail());
        } else {
            mHomeEmailTxt.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactWorkEmail() != null && !mContactJDO.getContactWorkEmail().equals("") ) {
            mViewWorkEmail.setText(mContactJDO.getContactWorkEmail());
        } else {
            mWorkEmailTxt.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactOtherEmail() != null && !mContactJDO.getContactOtherEmail().equals("") ) {
            mViewOtherEmail.setText(mContactJDO.getContactOtherEmail());
        } else {
            mOtherEmailTxt.setVisibility(View.GONE);
        }

//Address

        if (mContactJDO.getContactHomeAddress() != null && !mContactJDO.getContactHomeAddress().equals("")) {
            mViewHomeAddress.setText(mContactJDO.getContactHomeAddress());
        } else {
            mHomeAddressTxt.setVisibility(View.GONE);
            mViewHomeAddress.setVisibility(View.GONE);
        }

        if (mContactJDO.getContactWorkAddress() != null && !mContactJDO.getContactWorkAddress().equals(""))  {
            mViewWorkAddress.setText(mContactJDO.getContactWorkAddress());
        } else {
            mWorkAddressTxt.setVisibility(View.GONE);
            mViewWorkAddress.setVisibility(View.GONE);
        }

        if ( mContactJDO.getContactOtherAddress() != null && !mContactJDO.getContactOtherAddress().equals("")) {
            mViewOtherAddress.setText(mContactJDO.getContactOtherAddress());
        } else {
            mOtherAddressTxt.setVisibility(View.GONE);
            mViewOtherAddress.setVisibility(View.GONE);
        }
        makeCallMessage();
    }

    private void makeCallMessage() {
        mHomeCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                makeCall(mContactJDO.getContactHomeNumber());
            }
        });
        mMobileCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                makeCall(mContactJDO.getContactMobileNumber());
            }
        });
        mWorkCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                makeCall(mContactJDO.getContactWorkNumber());
            }
        });
        mOtherCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                makeCall(mContactJDO.getContactOtherNumber());
            }
        });
        mHomeSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(mContactJDO.getContactHomeNumber());
            }
        });
        mMobileSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(mContactJDO.getContactMobileNumber());
            }
        });
        mWorkSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(mContactJDO.getContactWorkNumber());
            }
        });
        mOtherSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(mContactJDO.getContactOtherNumber());
            }
        });
        mHomeEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmail(mContactJDO.getContactHomeEmail());

            }
        });
        mWorkEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmail(mContactJDO.getContactWorkEmail());
            }
        });
        mOtherEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmail(mContactJDO.getContactOtherEmail());
            }
        });

    }

    private void makeCall(String pPhone) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pPhone, null)));
    }

    private void makeMessage(String pPhone) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + pPhone));
        startActivity(smsIntent);
    }

    private void makeEmail(String pEmail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {pEmail};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    private void deleteContactItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteContactAsyncTask().execute();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class deleteContactAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Contact lContact = new Contact();
            lContact.setContactID(mContactJDO.getContactID());
            MainActivity.mCopyArrayList.remove(mPosition);
            MainActivity.mMyAppDatabase.contactDao().deleteContact(lContact);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
            getActivity().setResult(RESULT_OK);
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( mRequestCode== requestCode) {
            if (resultCode == RESULT_OK) {
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void sendUpdateData() {
        Intent intent = new Intent(getActivity(), UpdateContact.class);
        intent.putExtra("contactObject", mContactJDO);
        intent.putExtra("contactPosition", mPosition);
        startActivityForResult(intent, mRequestCode);

    }


}
