package com.hfad.th2android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.th2android.R;
import com.hfad.th2android.adapters.RecyclerViewAdapter;
import com.hfad.th2android.db.SQLiteHelper;
import com.hfad.th2android.models.Book;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    private EditText edFrom, edTo;
    private Button btnSearch, btnStatistic;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnStatistic = view.findViewById(R.id.btnGetStatistic);
        recyclerView = view.findViewById(R.id.recyclerViewFragmentSearch);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
                List<Book> booksByPrice = sqLiteHelper.findBooksByPrice(edFrom.getText().toString().trim(), edTo.getText().toString().trim());
                recyclerViewAdapter.setListBook(booksByPrice);
            }
        });

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
                List<Book> statistic = sqLiteHelper.getStatistic();
                recyclerViewAdapter.setListBook(statistic);
            }
        });

        recyclerViewAdapter.setItemClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intentOpenUpdateDeleteActivity = new Intent(getActivity(),UpdateDeleteActivity.class);
                intentOpenUpdateDeleteActivity.putExtra("book", book);
                startActivity(intentOpenUpdateDeleteActivity);
            }
        });

    }
}
