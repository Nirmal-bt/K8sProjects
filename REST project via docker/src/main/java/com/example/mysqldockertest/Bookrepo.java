package com.example.mysqldockertest;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mysqldockertest.Bookinfo;

public interface Bookrepo extends JpaRepository<Bookinfo, String> {

}
