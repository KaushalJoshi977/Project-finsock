package com.example.finsock.project.Repositories;

import com.example.finsock.project.Models.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserActivity,Integer>{

}