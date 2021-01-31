package com.example.demo.data;

import com.example.demo.model.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface  ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
