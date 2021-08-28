package com.planeter.review;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/10 21:27
 * @status dev
 */
@SpringBootTest
public class MongoDBTest {
    @Resource
    public MongoTemplate mongoTemplate;
    @Test
    void initializeBloomFilter(){

    }
//    public static List<Document> getDocumentsFromJsonFile(File file) {
//        //逐行转换
//        List<Document> observationDocuments = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()));) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                observationDocuments.add(Document.parse(line));
//            }
//        } catch (IOException ex) {
//            ex.getMessage();
//        }
//        return observationDocuments;
//    }
//    @Test
//    void insertDict(){
//        List<Document> cet4 = getDocumentsFromJsonFile(new File("./src/main/resources/static/CET4_3.json"));
//        mongoTemplate.getCollection("word").insertMany(cet4);
//        List<Document> cet6 = getDocumentsFromJsonFile(new File("./src/main/resources/static/CET6_3.json"));
//        mongoTemplate.getCollection("word").insertMany(cet6);
//    }

}
