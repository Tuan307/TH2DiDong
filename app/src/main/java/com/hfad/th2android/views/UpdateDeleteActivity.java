package com.hfad.th2android.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.hfad.th2android.R;
import com.hfad.th2android.db.SQLiteHelper;
import com.hfad.th2android.models.Book;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity {
    private EditText edName, edAuthor, edPublishDate, edPrice;
    private Spinner spPublisher;
    private Button btnUpdate, btnCancel, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();

        // Hiện ra thông tin của book cần update
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        edName.setText(book.getName());
        edAuthor.setText(book.getAuthor());
        edPublishDate.setText(book.getPublishDate());
        edPrice.setText(book.getPrice());
        for(int i=0;i<spPublisher.getCount();i++){
            // Duyệt qua list item trong spinner publisher xem item nào trùng với publisher của book cần sửa thì hiện item ý được chọn trong spinner
            if(book.getPublisher().equals(spPublisher.getItemAtPosition(i))){
                spPublisher.setSelection(i);
                break;
            }
        }

        // Bấm vào ed publisher thì hiện dialog chọn ngày
        edPublishDate.setOnClickListener(view ->{
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    m++;
                    String date = d+"/"+m+"/"+y;
                    edPublishDate.setText(date);
                }
            },year, month, day);
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

            Book bookToUpdate = new Book(book.getId(),name,author,publishDate,publisher,price);

            SQLiteHelper sqLiteHelper = new SQLiteHelper(UpdateDeleteActivity.this);
            sqLiteHelper.updateBook(bookToUpdate);

            Intent intentToMainActivity = new Intent(UpdateDeleteActivity.this, TabLayoutMainActivity.class);
            startActivity(intentToMainActivity);
        });

        btnDelete.setOnClickListener(view -> {
            SQLiteHelper sqLiteHelper = new SQLiteHelper(UpdateDeleteActivity.this);
            sqLiteHelper.deleteBook(book.getId());

            Intent intentToMainActivity = new Intent(UpdateDeleteActivity.this, TabLayoutMainActivity.class);
            startActivity(intentToMainActivity);
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
        btnDelete = findViewById(R.id.btnDelete);
        spPublisher.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.publisher)));
    }
}