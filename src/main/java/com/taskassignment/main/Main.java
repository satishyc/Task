package com.taskassignment.main;

import com.taskassignment.kafkatopicreader.KafkaTopicReader;
import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        KafkaTopicWriter kafkaTopicWriter = new KafkaTopicWriter();
        KafkaTopicReader kafkaTopicReader = new KafkaTopicReader();
        kafkaTopicReader.main(null);
        kafkaTopicWriter.main(null);

    }

}