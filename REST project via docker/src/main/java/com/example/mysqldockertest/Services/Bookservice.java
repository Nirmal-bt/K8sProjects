package com.example.mysqldockertest.Services;

import java.util.ArrayList;
import com.example.mysqldockertest.Bookinfo;


public interface Bookservice {

    public String createbook(Bookinfo bookinfo);
    public String updatebook(Bookinfo bookinfo);
    public String deletebook(String bookid);
    public Bookinfo getbook(String bookid);
    public ArrayList<Bookinfo> getAllbook();
}
