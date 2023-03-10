package com.example.finsock.project.Repositories;

import com.example.finsock.project.Models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepo extends MongoRepository<UserEntity,Integer> {
}
