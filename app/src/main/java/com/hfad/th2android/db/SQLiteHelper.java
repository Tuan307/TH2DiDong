package com.hfad.th2android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hfad.th2android.models.Book;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String dbName = "bookDB";

    public SQLiteHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE books(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "author TEXT," +
                "publishDate TEXT," +
                "publisher TEXT," +
                "price REAL)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addBook(Book book){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", book.getName());
        contentValues.put("author", book.getAuthor());
        contentValues.put("publishDate", book.getPublishDate());
        contentValues.put("publisher", book.getPublisher());
        contentValues.put("price", Float.parseFloat(book.getPrice()));
        long result = sqLiteDatabase.insert("books", null,contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<Book> getAllBook(){
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM books ORDER BY price DESC",null);
        while (cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String publishDate = cursor.getString(3);
            String publisher = cursor.getString(4);
            String price = cursor.getFloat(5)+"";
            Book book = new Book(id,name,author,publishDate,publisher,price);
            bookList.add(book);
        }
        return bookList;
    }

    public long updateBook(Book book){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", book.getName());
        contentValues.put("author", book.getAuthor());
        contentValues.put("publishDate", book.getPublishDate());
        contentValues.put("publisher", book.getPublisher());
        contentValues.put("price",Float.parseFloat(book.getPrice()));
        long result = sqLiteDatabase.update("books",
                contentValues,"_id=?",
                new String[]{book.getId()+""});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteBook(int bookId){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.delete("books", "_id=?",
                new String[]{bookId+""});
        sqLiteDatabase.close();
        return result;
    }

    public List<Book> findBooksByPrice(String startPrice, String endPrice){
        List<Book> listBook = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("books",null,"price BETWEEN ? AND ?"
        , new String[]{startPrice, endPrice},null,null,null);
        while(cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String publishDate = cursor.getString(3);
            String publisher = cursor.getString(4);
            String price = cursor.getFloat(5)+"";
            Book book = new Book(id,name,author,publishDate,publisher,price);
            listBook.add(book);
        }
        return listBook;
    }

    public List<Book> getStatistic(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<Book> listBook = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("books",new String[]{"_id","name","author","publishDate","publisher","MAX(price) AS price"},
                null, null,"publisher",null,"price DESC");
        while(cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String publishDate = cursor.getString(3);
            String publisher = cursor.getString(4);
            String price = cursor.getFloat(5)+"";
            Book book = new Book(id,name,author,publishDate,publisher,price);
            listBook.add(book);
        }
        return listBook;
    }
}
