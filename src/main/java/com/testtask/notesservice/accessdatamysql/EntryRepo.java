package com.testtask.notesservice.accessdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.testtask.notesservice.accessdatamysql.Entry;

import java.util.List;

public interface EntryRepo extends CrudRepository<Entry, Integer> {
    Entry getById(Integer id);
    List<Entry> findByTitleContainingOrContentContaining(String title, String content);
}
