package edu.java.bot.controllers;

import edu.java.DTO.LinkUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/updates/")
public class UpdateController {

    private static final Logger LOGGER = LogManager.getLogger(UpdateController.class);

    @PostMapping()
    public ResponseEntity<String> updateLink(@RequestBody LinkUpdateRequest linkRequest) {
        //TODO
        /* example:
        {
            "id": 1,
             "url": "https://editor.swagger.io/",
             "description": "some description",
             "tgChatIds": 1
        }
         */
        LOGGER.info("{}. has been updated!", linkRequest);
        return new ResponseEntity<>(linkRequest.url() + " has been updated!", HttpStatus.OK);
    }
}
