package edu.java.controllers;

import edu.java.DAO.TrackingDao;
import edu.java.DTO.LinkResponse;
import edu.java.DTO.LinkUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/links/")
public class LinksController {
    private static final Logger LOGGER = LogManager.getLogger(LinksController.class);
    private final TrackingDao trackingDao;

    @Autowired
    public LinksController(TrackingDao trackingDao) {
        this.trackingDao = trackingDao;
    }

    @PostMapping()
    public ResponseEntity<String> addLink(@RequestBody LinkResponse linkRequest) {
        /* example:
         {
             "url": "https://editor.swagger.io/",
             "tgChatId": 1
        }
         */
        trackingDao.addLink(linkRequest.tgChatId(), linkRequest.url());
        String message = linkRequest.url() + " has been added!";
        LOGGER.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public LinkUpdateRequest[] getLink(@PathVariable int id) {
        return trackingDao.getLinks(id).stream()
            .map(linkModel -> new LinkUpdateRequest(linkModel.url(), linkModel.description(), id))
            .toArray(LinkUpdateRequest[]::new);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteLink(@PathVariable int id, @RequestParam String url) {
        trackingDao.deleteLink(id, url);
        String message = url + " has been deleted!";
        LOGGER.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
