package com.example.mysqldockertest.controller;

import com.example.mysqldockertest.Bookinfo;
import com.example.mysqldockertest.Services.Bookservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/Bookinfo")
public class Bookcontroller {

    @Autowired KafkaTemplate<String, String> kafkaTemplate;

    private static final String INTOPIC = "inputtopic";
    private static final String OUTTOPIC = "outputtopic";


    @GetMapping("/publish/{message}")

    public String publishMessage(@PathVariable("message") final String message) {
        // Sending the message
        kafkaTemplate.send(OUTTOPIC, message);

        return "Published Successfully";
    }

    @KafkaListener(topics = INTOPIC)
    public void consume(@Payload String message) {


        System.out.println(message);
        converter(message);


//
    }

    public void converter(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Bookinfo bookinfo = null;
        try {
            bookinfo = mapper.readValue(message, Bookinfo.class);

            if(bookservice.getbook(bookinfo.getBookid()) != null) {
                bookservice.updatebook(bookinfo);
                System.out.println("Book Updated Successfully");
            }
            else {
                System.out.println("Book Created!!");

                bookservice.createbook(bookinfo);
            }
            int accessnumber = bookservice.getbook(bookinfo.getBookid()).getNumberoftimesaccessed();
            String accessnumberstring = String.valueOf(accessnumber);
             
            Pattern pattern = Pattern.compile("\"quantity\":\"(\\d+)\"");
            Matcher matcher = pattern.matcher(message);
            String quantstring= "\"quantity\":\""+String.valueOf(bookinfo.getQuantity())+"\"";
            if(matcher.find()){
                message = matcher.replaceFirst(quantstring);
            }


            String newmessage = message + ",\"numberoftimesaccessed\":\"" + accessnumberstring;
            newmessage = newmessage.replace("}","").trim();
            newmessage = newmessage + "\"}";
            kafkaTemplate.send(OUTTOPIC,newmessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    Bookservice bookservice;

    public Bookcontroller(Bookservice bookservice) {
        this.bookservice = bookservice;
    }

    Bookinfo bookinfo;
    @GetMapping("{bookid}")
    public Bookinfo getbookinfo(@PathVariable("bookid") String bookid) {
        //return new Bookinfo("A1","nirmalbttt","how to kill 2 mockingbirds");

        return bookservice.getbook(bookid);

    }

    @GetMapping()
    public ArrayList<Bookinfo> getallbookinfo() {
        //return new Bookinfo("A1","nirmalbttt","how to kill 2 mockingbirds");

        return  bookservice.getAllbook();

    }

    @PostMapping
    public String createbookinfo(@RequestBody Bookinfo bookinfo)  {

        bookinfo.setNumberoftimesaccessed(1);
        bookservice.createbook(bookinfo);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String temp = mapper.writeValueAsString(bookinfo);

            kafkaTemplate.send(OUTTOPIC, temp);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "successfully executed createbookinfo";
    }

    @PutMapping
    public String updatebookinfo(@RequestBody Bookinfo bookinfo) {


        bookservice.updatebook(bookinfo);

        //what
        String tempbookid = bookinfo.getBookid();
        bookinfo =bookservice.getbook(tempbookid);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String temp = mapper.writeValueAsString(bookinfo);


            kafkaTemplate.send(OUTTOPIC, temp);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "successfully executed updatebookinfo";
    }

    @DeleteMapping("{bookid}")
    public String deletebookinfo(@PathVariable("bookid")  String bookid) {
        //return new bookinfo("A1","nirmal","how to kill 2 mockingbirds");
        bookservice.deletebook(bookid);
        return "successfully deleted bookinfo with id"+bookid;
    }
}
