package com.taskassignment.kafkatopicwriter;
import com.google.common.primitives.Bytes;
import com.taskassignment.iotsimulator.IOTSimulator;
import com.taskassignment.loadyaml.LoadYAML;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class KafkaTopicWriter{
    Logger logger = Logger.getLogger(
            KafkaTopicWriter.class.getName());

    Properties props = new Properties();
    private static int numberOfRecrod = 10000;
    IOTSimulator iotSimulator = new IOTSimulator();
    public KafkaTopicWriter() throws IOException,SecurityException {




    }


    public void init(){

        logger.info("init() Method is Invoked");
        byte[]  payload1,payload2,payload3,payload4;
        int Temp1=-2,Temp2=-2,Temp3=-2,Temp4=-2;
        int emailC100=0,emailC101=0,emailC102=0,emailC103=0;
        int Count =1,Count1=1,Count2=1;
        Callback callback = new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null)
                    e.printStackTrace();
            }
        };
        for(int i=1;i<numberOfRecrod;i++){
            LoadYAML loadYAML = new LoadYAML();
            int timeInterval=loadYAML.LoadData();

            logger.info("IOT simulator started");

            int time=timeInterval*Count;
            int time1=timeInterval*Count1;
            int time2=timeInterval*Count2;

            props.setProperty("bootstrap.servers", "localhost:9092");
            props.setProperty("kafka.topic.name", "TestTopic");
            KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(this.props,new StringSerializer(), new ByteArraySerializer());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeNow=timestamp.toString();
            logger.info("Simulator method is called");
            try{
                Temp1=iotSimulator.temperatureGenerator(Temp1,0,100);
                Temp2=iotSimulator.temperatureGenerator(Temp2,1,time);
                Temp3=iotSimulator.temperatureGenerator(Temp3,2,time1);
                Temp4=iotSimulator.temperatureGenerator(Temp4,3,time2);
                logger.info("Got the data from Simulator");


            }
            catch(Exception e){
                logger.warning("Data is not recevied from IOT simulator");
                e.printStackTrace();
            }
            //System.out.println(time+" "+ time1+" "+time2);

            if((time/200000)>=1){
                Count=1;
                logger.info("Case 2 Counter is Rested");
            }
            if((time1/150000)>=1){
                Count1=1;
                logger.info("Case 3 Counter is Rested");
            }
            if((time2/100000)>=1){
                Count2=1;
                logger.info("Case 4 Counter is Rested");
            }
            if(Temp1>0 || Temp1<-4){
                emailC100+=1;
                payload1=("C100,"+timeNow+","+Temp1+",400,"+emailC100+",").getBytes();
            }
            else{
                emailC100=0;
                payload1=("C100,"+timeNow+","+Temp1+",200,"+emailC100+",").getBytes();

            }
            if(Temp2>0 || Temp2<-4){
                emailC101+=1;
                payload2=("C101,"+timeNow+","+Temp2+",400,"+emailC101+",").getBytes();
            }
            else{
                emailC101=0;
                payload2=("C101,"+timeNow+","+Temp2+",200,"+emailC101+",").getBytes();
            }
            if(Temp3>0 || Temp3<-4){
                emailC102+=1;
                payload3=("C102,"+timeNow+","+Temp3+",400,"+emailC102+",").getBytes();
            }
            else{
                emailC102=0;
                payload3=("C102,"+timeNow+","+Temp3+",200,"+emailC102+",").getBytes();
            }
            if(Temp4>0 || Temp4<-4){
                emailC103+=1;
                payload4=("C103,"+timeNow+","+Temp4+",400,"+emailC103+",").getBytes();
            }
            else{
                emailC103=0;
                payload4=("C103,"+timeNow+","+Temp4+",200,"+emailC103+",").getBytes();

            }
            byte[] payload = Bytes.concat(payload1,payload2,payload3,payload4);
            Count++;
            Count1++;
            Count2++;
            //System.out.println(Count+","+Count1+","+Count2);
            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(props.getProperty("kafka.topic.name"), payload);
            try{
                producer.send(record);
                logger.info("Producer Recored is pushed to kafka");
            }
            catch(Exception e){
                logger.warning("Record is not pushed to Kafka Server");
                e.printStackTrace();
            }
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }





    }


    public static void main(String[] args) throws IOException {

        KafkaTopicWriter kafkaWrite = new KafkaTopicWriter();
        kafkaWrite.init();

    }




}
