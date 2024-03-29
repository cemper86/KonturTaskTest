package ru.stairenx.konturtasktest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.stairenx.konturtasktest.R;
import ru.stairenx.konturtasktest.adapter.RecyclerViewAdapter;
import ru.stairenx.konturtasktest.item.ContactItem;
import ru.stairenx.konturtasktest.server.ConnectServer;
import ru.stairenx.konturtasktest.server.ParserJSON;

public class MainFragment extends Fragment {

    private SearchView searchView;
    private static SearchView.OnQueryTextListener searchViewListener;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    private static List<ContactItem> data = new ArrayList<>();

    public static MainFragment getInstance(){
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initView(view);
        initRecyclerView();
        checkData(view);
        swipeUpdate(view);
        initSearchView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView(View view){
        searchView = view.findViewById(R.id.search_view);
        searchView.setFocusable(false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        swipe = view.findViewById(R.id.swipe_refresh);
    }

    private void initSearchView(){
        searchView.setOnQueryTextListener(initSearchViewListener(searchViewListener));
    }

    private SearchView.OnQueryTextListener initSearchViewListener(SearchView.OnQueryTextListener listener){
        listener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        };
        return listener;
    }

    private void setVisualUpdate(){
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setVisualFinishUpdate(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter = new RecyclerViewAdapter(getContext().getApplicationContext(), data, new RecyclerViewAdapter.ClickCard() {
            @Override
            public void onClicked(View view, ContactItem item) {
                loadFragment(ContactFragment.getInstance(item));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getDataFromServer(final View view) {
        setVisualUpdate();
        data.clear();
        ConnectServer.query(getContext().getApplicationContext(), new ConnectServer.ResultResponse() {

                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            data.add(ParserJSON.parserJSONObject(jsonArray.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.getStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    setVisualFinishUpdate();
                }

                @Override
                public void onFail() {
                    setVisualFinishUpdate();
                    Snackbar.make(view, "Нет подключения к сети", Snackbar.LENGTH_LONG).show();
                }
            });
    }

    private void swipeUpdate(final View view){
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer(view);
                swipe.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void checkData(View view){
        /* space for check local database, but none */
        getDataFromServer(view);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment, fragment).commit();
    }

}
