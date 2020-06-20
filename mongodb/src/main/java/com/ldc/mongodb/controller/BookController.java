package com.ldc.mongodb.controller;

import com.ldc.mongodb.dao.BookDao;
import com.ldc.mongodb.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/20 22:24
 */
@RestController
public class BookController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookDao bookDao;

    @GetMapping("/test")
    public void test() {
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(1);
        book1.setName("朝花夕拾");
        book1.setAuthor("鲁迅");
        books.add(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setName("呐喊");
        book2.setAuthor("鲁迅");
        books.add(book2);

        //bookDao.insert(books);

        List<Book> bookList = bookDao.findByAuthorContains("鲁迅");
        System.out.println(bookList);

        Book book = bookDao.findByNameEquals("朝花夕拾");
        System.out.println(book);
    }

    @GetMapping("/test2")
    public void test2() {
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(3);
        book1.setName("围城");
        book1.setAuthor("钱钟书");
        books.add(book1);

        Book book2 = new Book();
        book2.setId(4);
        book2.setName("宋诗宋词");
        book2.setAuthor("钱钟书");
        books.add(book2);

        mongoTemplate.insertAll(books);

        List<Book> bookList = mongoTemplate.findAll(Book.class);
        System.out.println(bookList);

        Book book = mongoTemplate.findById(3,Book.class);
        System.out.println(book);
    }

}
