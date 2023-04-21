package com.hfad.th2android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.th2android.R;
import com.hfad.th2android.models.Book;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter{
    public interface ItemClickListener{
        void onItemClick(Book book);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setListBook(List<Book> listBook) {
        this.listBook = listBook;
        notifyDataSetChanged();
    }

    private List<Book> listBook;

    public RecyclerViewAdapter(List<Book> listBook) {
        this.listBook = listBook;
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvAuthor, tvPublishDate, tvPublisher, tvPrice;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPublishDate = itemView.findViewById(R.id.tvPublishDate);
            tvPublisher = itemView.findViewById(R.id.tvPublisher);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookViewHolder bookViewHolder = (BookViewHolder) holder;
        Book book = listBook.get(position);
        bookViewHolder.tvName.setText(book.getName());
        bookViewHolder.tvAuthor.setText(book.getAuthor());
        bookViewHolder.tvPublishDate.setText(book.getPublishDate());
        bookViewHolder.tvPublisher.setText(book.getPublisher());
        bookViewHolder.tvPrice.setText(book.getPrice());

        bookViewHolder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(book);
        });
    }

    @Override
    public int getItemCount() {
        return listBook!=null ? listBook.size() : 0;
    }
}
