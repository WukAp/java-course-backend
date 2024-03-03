package edu.java.controllers;

import edu.java.DAO.TrackingDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tg-chat/")
public class TgChatController {
    private static final Logger LOGGER = LogManager.getLogger(TgChatController.class);

    private final TrackingDao trackingDao;

    @Autowired
    public TgChatController(TrackingDao trackingDao) {
        this.trackingDao = trackingDao;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addChat(@PathVariable int id) {
        trackingDao.addChat(id);
        String message = getMessage(id, " has been added!");
        LOGGER.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable int id) {
        trackingDao.deleteChat(id);
        String message = getMessage(id, " has been deleted!");
        LOGGER.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private String getMessage(int id, String endOfMessage) {
        return "chat " + id + endOfMessage;
    }
}
