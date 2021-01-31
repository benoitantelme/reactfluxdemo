package com.example.demo.web.controller;

import com.example.demo.service.ProfileService;
import com.example.demo.web.AbstractBaseProfileEndpoints;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@Log4j2
@Import({ProfileRestController.class, ProfileService.class})
@ActiveProfiles("controller")
public class ControllerEndpointsTest extends AbstractBaseProfileEndpoints {
    @BeforeAll
    static void before() {
        log.info("running controller tests");
    }

    ControllerEndpointsTest(@Autowired WebTestClient client) {
        super(client);
    }
}
