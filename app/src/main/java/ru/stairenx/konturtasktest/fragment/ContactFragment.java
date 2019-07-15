package ru.stairenx.konturtasktest.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.stairenx.konturtasktest.MainActivity;
import ru.stairenx.konturtasktest.R;
import ru.stairenx.konturtasktest.adapter.ObjectFormatter;
import ru.stairenx.konturtasktest.item.ContactItem;

public class ContactFragment extends Fragment {


    private Toolbar toolbar;
    private TextView name, phone, temperament, education, biography;

    private static ContactItem contactItem;

    public static ContactFragment getInstance(ContactItem item){
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        contactItem = item;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);
        initToolbar(view);
        initView(view);
        setInformation(contactItem);
        onClickPhoneListener();
        return view;
    }
    private void initToolbar(View view){
        toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle("");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        int color = Color.parseColor("#FFFFFF");
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        for(int  i = 0;i<toolbar.getChildCount();i++){
            View v = toolbar.getChildAt(i);
            if(v instanceof ImageButton){
                ((ImageButton) v).setColorFilter(colorFilter);
            }
        }
    }

    private void initView(View view){
        name = view.findViewById(R.id.contact_name);
        phone = view.findViewById(R.id.contact_phone);
        temperament = view.findViewById(R.id.contact_temperament);
        education = view.findViewById(R.id.contact_education);
        biography = view.findViewById(R.id.contact_biography);
    }

    private void setInformation(ContactItem item){
        name.setText(item.getName());
        phone.setText(item.getPhone());
        temperament.setText(item.getTemperament().toString());
        String eduText = ObjectFormatter.getValidDate(item.getEducationPeriod().getStart())
                + " - " + ObjectFormatter.getValidDate(item.getEducationPeriod().getFinish());
        education.setText(eduText);
        biography.setText(item.getBiography());
    }

    private void onClickPhoneListener(){
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactItem.getPhone()));
                startActivity(callAction);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .replace(R.id.fragment,MainFragment.getInstance()).commit();
                break;
        }
        return true;
    }
}
