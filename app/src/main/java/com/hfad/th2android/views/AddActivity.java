package com.hfad.th2android.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.th2android.R;
import com.hfad.th2android.db.SQLiteHelper;
import com.hfad.th2android.models.Book;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private EditText edName, edAuthor, edPublishDate, edPrice;
    private Spinner spPublisher;
    private Button btnUpdate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

        // Bấm vào ed publisher thì hiện dialog chọn ngày
        edPublishDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    m++;
                    String date = d + "/" + m + "/" + y;
                    edPublishDate.setText(date);
                }
            }, year, month, day);
            datePickerDialog.show();
        });

        // Bấm nút update để đóng gói thông tin trên form vào 1 đối tượng Book rồi lưu xuống cơ sở dữ liệu
        // Và trở về main activity
        btnUpdate.setOnClickListener(view -> {
            String name = edName.getText().toString();
            String author = edAuthor.getText().toString();
            String publishDate = edPublishDate.getText().toString();
            String publisher = spPublisher.getSelectedItem().toString();
            String price = edPrice.getText().toString();

            Book bookToAdd = new Book(name, author, publishDate, publisher, price);

            SQLiteHelper sqLiteHelper = new SQLiteHelper(AddActivity.this);
            sqLiteHelper.addBook(bookToAdd);

            Intent intent = new Intent(AddActivity.this, TabLayoutMainActivity.class);
            startActivity(intent);
        });
    }


    private void initView() {
        edName = findViewById(R.id.edName);
        edAuthor = findViewById(R.id.edAuthor);
        edPublishDate = findViewById(R.id.edPublishDate);
        edPrice = findViewById(R.id.edPrice);
        spPublisher = findViewById(R.id.spinnerPublisher);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        spPublisher.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.publisher)));
    }
}