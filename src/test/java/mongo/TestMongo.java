package mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author keboom
 * @date 2021/12/17
 */
public class TestMongo {

  private MongoDatabase labelDB;
  private MongoDatabase testlabelDb;

  @Before
  public void setup() {
    /*  做了一下数据同步, 别瞎用!!!
    MongoClient mongoClient = new MongoClient("biz.mon.rs1-1.duitang.net",27017);
    labelDB = mongoClient.getDatabase("labelDB");

    MongoClient testclient = new MongoClient("10.1.4.31", 27027);
    testlabelDb = testclient.getDatabase("labelDB");


     */
  }

  @Test
  public void getCollection() {
/*
    MongoCollection<Document> categoryCat = labelDB.getCollection("categorySrc");
//    System.out.println(categoryCat);
    FindIterable<Document> findIterable = categoryCat.find();
    MongoCursor<Document> mongoCursor = findIterable.iterator();
    ArrayList<Document> documents = new ArrayList<>();
    while (mongoCursor.hasNext()) {
      Document next = mongoCursor.next();
      documents.add(next);
    }

    MongoCollection<Document> testcategoryCat = testlabelDb.getCollection("categorySrc");
    testcategoryCat.insertMany(documents);


 */
  }

}
