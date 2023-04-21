package com.hfad.th2android.models;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name, author, publishDate, publisher, price;

    public Book() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Book(String name, String author, String publishDate, String publisher, String price) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.price = price;
    }

    public Book(int id, String name, String author, String publishDate, String publisher, String price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
