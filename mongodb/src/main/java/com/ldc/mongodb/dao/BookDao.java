package com.ldc.mongodb.dao;

import com.ldc.mongodb.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/20 22:20
 */
public interface BookDao extends MongoRepository<Book,Integer> {

    List<Book> findByAuthorContains(String author);

    Book findByNameEquals(String name);

}
