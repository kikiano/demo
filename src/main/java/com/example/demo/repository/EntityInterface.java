package com.example.demo.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.example.demo.entities.MyEntity;

public interface EntityInterface extends CassandraRepository<MyEntity, Integer> {

}
