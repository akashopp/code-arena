package com.dsa.codearena.service;

import com.dsa.codearena.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    Topic addTopic(Topic topic);
}
