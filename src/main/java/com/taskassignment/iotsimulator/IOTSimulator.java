package com.taskassignment.iotsimulator;

import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class IOTSimulator {
    public int temperatureGenerator(int previousTemperature,int caseNumber,int interval) throws IOException {
        Logger logger = Logger.getLogger(
                KafkaTopicWriter.class.getName());
        FileHandler fh = new FileHandler("simulatorLog.log");
        logger.addHandler(fh);

        int Temp=0;
        int TempMin=-10, TempMax=30;

        switch (caseNumber){
            case 0 :
                logger.info("Case 0 is called");
                Temp=previousTemperature;
                break;

            case 1:
                if((interval/200000)>=1){
                    Temp=previousTemperature-1;
                }
                else{
                    Temp=previousTemperature;
                }
                if(Temp<TempMin){
                    Temp=TempMin;
                }
                logger.info("Case 1 is called");
                break;

            case 2:
                if((interval/150000)>=1){
                    Temp=previousTemperature+1;
                }
                else{
                    Temp=previousTemperature;
                }

                if(Temp>TempMax){
                    Temp=TempMax;
                }
                logger.info("Case 2 is called");
                break;

            case 3:
                if((interval/100000)>=1){
                    Temp=previousTemperature+1;
                }
                else{
                    Temp=previousTemperature;
                }
                if(Temp>TempMax){
                    Temp=TempMax;
                }
                logger.info("Case 3 is called");
                break;


        }

        return Temp;

    }
}
