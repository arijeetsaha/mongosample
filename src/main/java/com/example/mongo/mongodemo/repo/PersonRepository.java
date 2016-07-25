package com.example.mongo.mongodemo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.mongo.mongodemo.entity.PersonEntity;


public interface PersonRepository extends PagingAndSortingRepository<PersonEntity, Long> {

}
