package com.dsa.codearena.controller;

import com.dsa.codearena.entity.Topic;
import com.dsa.codearena.service.TopicService;
import com.dsa.codearena.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return CommonUtils.createBuildResponse("Fetched all topics", HttpStatus.OK, topics);
    }

    @PostMapping("/add-topic")
    public ResponseEntity<?> addTopic(@RequestBody Topic topic) {
        Topic addTopic = topicService.addTopic(topic);
        return CommonUtils.createBuildResponse("Saved topic successfully", HttpStatus.CREATED, addTopic);
    }
}
