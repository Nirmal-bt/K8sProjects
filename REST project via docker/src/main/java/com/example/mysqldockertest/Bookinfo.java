package com.example.mysqldockertest;
import jakarta.persistence.*;

@Entity
public class Bookinfo {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookid;


    private String author;


    private String title;

    private int quantity;



    private int numberoftimesaccessed;


    public Bookinfo() {

    }

    public Bookinfo(String bookid, String author, String title) {
        this.author = author;
        this.bookid = bookid;
        this.title = title;
        this.quantity = 1;
        this.numberoftimesaccessed = 0;
    }

    public Bookinfo(String bookid, String author, String title,int quantity) {
        this.author = author;
        this.bookid = bookid;
        this.title = title;
        this.quantity = quantity;
        this.numberoftimesaccessed = 0;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumberoftimesaccessed() {
        return numberoftimesaccessed;
    }

    public void setNumberoftimesaccessed(int numberoftimesaccessed) {
        this.numberoftimesaccessed = numberoftimesaccessed;
    }

}
