package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.data.ProfileRepository;
import com.example.demo.service.ProfileService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Set;
import java.util.UUID;

@Log4j2
@DataMongoTest
@Import(ProfileService.class)
public class ProfileServiceTest {

    private final ProfileService service;
    private final ProfileRepository repository;

    public ProfileServiceTest(@Autowired ProfileService service,
                              @Autowired ProfileRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test
    public void getAll() {
        Set<String> emails = Set.of("Josh", "Matt", "Jane");

        Flux<Profile> saved = repository.saveAll(
                Flux.just(
                        new Profile(null, "Josh"),
                        new Profile(null, "Matt"),
                        new Profile(null, "Jane")));

        Flux<Profile> composite = service.all().thenMany(saved);

        StepVerifier
                .create(composite)
                .expectNextMatches(profile -> emails.contains(profile.getEmail()))
                .expectNextMatches(profile -> emails.contains(profile.getEmail()))
                .expectNextMatches(profile -> emails.contains(profile.getEmail()))
                .verifyComplete();
    }

    @Test
    public void save() {
        Mono<Profile> profileMono = this.service.create("email@email.com");
        StepVerifier
                .create(profileMono)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    @Test
    public void delete() {
        String test = "test";

        Mono<Profile> deleted = this.service
                .create(test)
                .flatMap(saved -> this.service.delete(saved.getId()));

        StepVerifier
                .create(deleted)
                .expectNextMatches(profile -> profile.getEmail().equalsIgnoreCase(test))
                .verifyComplete();
    }

    @Test
    public void update() throws Exception {
        Mono<Profile> saved = this.service
                .create("test")
                .flatMap(p -> this.service.update(p.getId(), "test1"));

        StepVerifier
                .create(saved)
                .expectNextMatches(p -> p.getEmail().equalsIgnoreCase("test1"))
                .verifyComplete();
    }

    @Test
    public void getById() {
        String test = UUID.randomUUID().toString();

        Mono<Profile> deleted = this.service
                .create(test)
                .flatMap(saved -> this.service.get(saved.getId()));

        StepVerifier
                .create(deleted)
                .expectNextMatches(profile ->
                        StringUtils.hasText(profile.getId()) && test.equalsIgnoreCase(profile.getEmail()))
                .verifyComplete();
    }

}