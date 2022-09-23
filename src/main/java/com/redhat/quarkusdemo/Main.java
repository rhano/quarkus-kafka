/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.redhat.quarkusdemo;

/**
 *
 * @author rhano
 */
import io.quarkus.runtime.QuarkusApplication;


import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@QuarkusMain    
public class Main implements QuarkusApplication {
  @Override
  public int run(String... args) throws Exception {   
   Properties properties = new Properties();
        properties.put("bootstrap.servers", "my-cluster-kafka-bootstrap.openshift-operators.svc.cluster.local:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       
        properties.put("security.protocol","SASL_PLAINTEXT");
properties.put("sasl.mechanism","SCRAM-SHA-512");
properties.put("sasl.jaas.config","org.apache.kafka.common.security.scram.ScramLoginModule required username=my-user2 password=password");
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        try{
            for(;;){
               
                kafkaProducer.send(new ProducerRecord("my-topic", "test message - " + System.currentTimeMillis() ));
                Thread.currentThread().sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaProducer.close();
        }
        return 0;
 }
}
