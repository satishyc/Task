package com.taskassignment.loadyaml;
import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LoadYAML {
    public static int LoadData() {
        Logger logger = Logger.getLogger(
                KafkaTopicWriter.class.getName());

        String confPath="C:\\Users\\satish.y\\Pictures\\Task\\src\\main\\java\\config.yaml";
        Map conf = new HashMap();

        Yaml yaml = new Yaml();

        try {
            InputStream stream = new FileInputStream(confPath);

            conf = (Map) yaml.load(stream);
            if (conf == null || conf.isEmpty() == true) {
                logger.warning("Failed to read config file");
                throw new RuntimeException("Failed to read config file");
            }

        } catch (FileNotFoundException e) {
            logger.warning("No such file " + confPath);
            throw new RuntimeException("No config file");

        }
        int time= (Integer) conf.get("timeInterval");
        return time;
    }

}
