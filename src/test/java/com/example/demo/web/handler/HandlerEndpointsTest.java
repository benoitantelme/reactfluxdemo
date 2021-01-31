package com.example.demo.web.handler;

import com.example.demo.service.ProfileService;
import com.example.demo.web.AbstractBaseProfileEndpoints;
import com.example.demo.web.controller.ProfileRestController;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@Log4j2
@ActiveProfiles("handler")
@Import({ProfileEndpointConfiguration.class,
        ProfileHandler.class, ProfileService.class})
public class HandlerEndpointsTest extends AbstractBaseProfileEndpoints {
    @BeforeAll
    static void before() {
        log.info("running handler " + ProfileRestController.class.getName() + " tests");
    }

    HandlerEndpointsTest(@Autowired WebTestClient client) {
        super(client);
    }
}
