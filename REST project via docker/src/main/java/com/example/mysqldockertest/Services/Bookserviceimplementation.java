package com.example.mysqldockertest.Services;

import com.example.mysqldockertest.Bookinfo;
import com.example.mysqldockertest.Bookrepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Bookserviceimplementation implements Bookservice {


    Bookrepo bookrepo;
    public Bookserviceimplementation(Bookrepo bookrepo) {
        this.bookrepo = bookrepo;
    }

    @Override
    public String createbook(Bookinfo bookinfo) {

        bookinfo.setQuantity(1);
        bookinfo.setNumberoftimesaccessed(1);

        bookrepo.save(bookinfo);
        return "created";

    }

    @Override
    public String updatebook(Bookinfo bookinfo) {

        bookinfo.setNumberoftimesaccessed(getbook(bookinfo.getBookid()).getNumberoftimesaccessed()+1);

        bookinfo.setQuantity(getbook(bookinfo.getBookid()).getQuantity()+bookinfo.getQuantity());

        bookrepo.save(bookinfo);
        return "updated";
    }

    @Override
    public String deletebook(String bookid) {
        bookrepo.deleteById(bookid);
        return "deleted";

    }

    @Override
    public Bookinfo getbook(String bookid) {
        return bookrepo.findById(bookid).orElseGet(()->{
            System.out.println("no such book bro");
            return null;
        });

    }

    @Override
    public ArrayList<Bookinfo> getAllbook() {

        return (ArrayList<Bookinfo>) bookrepo.findAll();
    }
}