package com.taskassignment.kafkatopicreader;
import com.taskassignment.dbupdate.DBUpdate;
import com.taskassignment.emailalert.EmailAlert;
import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class KafkaTopicReader extends Thread{
    Logger logger = Logger.getLogger(
            KafkaTopicWriter.class.getName());


    private final ConsumerConnector consumer;
    public static String topicName = "TestTopic";



    public KafkaTopicReader() throws IOException {
        System.out.println("** Initialize **");
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "TestTopic-Group");
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        FileHandler fh = new FileHandler("readerLog.log");
        logger.addHandler(fh);

    }

    public static void main(String[] args) throws IOException {


        //System.out.println("******* Consumer Started ***************");
        KafkaTopicReader demo = new KafkaTopicReader();
        demo.start();


    }


    @Override
    public void run() {


        String cidC100 = "", timeStampC100 = "", tempC100 = "", stCodeC100 = "", emailC100 = "";
        String cidC101 = "", timeStampC101 = "", tempC101 = "", stCodeC101 = "", emailC101 = "";
        String cidC102 = "", timeStampC102 = "", tempC102 = "", stCodeC102 = "", emailC102 = "";
        String cidC103 = "", timeStampC103 = "", tempC103 = "", stCodeC103 = "", emailC103 = "";

        EmailAlert emailAlert = new EmailAlert();

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topicName, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consuerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consuerMap.get(topicName).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            logger.info("Getting Data from Kafaka");

            String str = new String(it.next().message());
            String[] strArray = str.split(",");
            cidC100 = strArray[0];
            timeStampC100 = strArray[1];
            tempC100 = strArray[2];
            stCodeC100 = strArray[3];
            emailC100 = strArray[4];
            cidC101 = strArray[5];
            timeStampC101 = strArray[6];
            tempC101 = strArray[7];
            stCodeC101 = strArray[8];
            emailC101 = strArray[9];
            cidC102 = strArray[10];
            timeStampC102 = strArray[11];
            tempC102 = strArray[12];
            stCodeC102 = strArray[13];
            emailC102 = strArray[14];
            cidC103 = strArray[15];
            timeStampC103 = strArray[16];
            tempC103 = strArray[17];
            stCodeC103 = strArray[18];
            emailC103 = strArray[19];
            if ("3".equals(emailC100)){
                try {
                    emailAlert.SendMail(timeStampC100, cidC100);
                    logger.info("There is fluctuation of Temperature in the Container C100 and email" +
                            "notification is sent to respective email id");
                } catch (IOException e) {
                    logger.warning("email Notification for Container C100 is not sent");
                    e.printStackTrace();
                }
            }
            if ("3".equals(emailC101)) {
                try {
                    emailAlert.SendMail(timeStampC101, cidC101);
                    logger.info("There is fluctuation of Temperature in the Container C101 and email" +
                            "notification is sent to respective email id");

                } catch (IOException e) {
                    logger.warning("email Notification for Container C101 is not sent");
                    e.printStackTrace();
                }
            }
            if ("3".equals(emailC102)){
                try {
                    logger.info("There is fluctuation of Temperature in the Container C102 and email" +
                            "notification is sent to respective email id");
                    emailAlert.SendMail(timeStampC102, cidC102);
                } catch (IOException e) {
                    logger.warning("email Notification for Container C102 is not sent");
                    e.printStackTrace();

                }
            }

            if ("3".equals(emailC103)) {
                try {
                    emailAlert.SendMail(timeStampC103, cidC103);
                    logger.info("There is fluctuation of Temperature in the Container C103 and email" +
                            "notification is sent to respective email id");
                } catch (IOException e) {
                    logger.warning("email Notification for Container C103 is not sent");
                    e.printStackTrace();
                }
            }
            List<String> C100 = new ArrayList<>();
            C100.add(timeStampC100);
            C100.add(tempC100);
            C100.add(stCodeC100);
            List<String> C101 = new ArrayList<>();
            C101.add(timeStampC101);
            C101.add(tempC101);
            C101.add(stCodeC101);
            List<String> C102 = new ArrayList<>();
            C102.add(timeStampC102);
            C102.add(tempC102);
            C102.add(stCodeC102);
            List<String> C103 = new ArrayList<>();
            C103.add(timeStampC103);
            C103.add(tempC103);
            C103.add(stCodeC103);
            DBUpdate dbUpdate = new DBUpdate();
            dbUpdate.insertIntoDB(C100,C101,C102,C103);


        }

    }


}
