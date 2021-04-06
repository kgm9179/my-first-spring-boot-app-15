package org.cnu.realcoding.repository;

import org.cnu.realcoding.domain.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DogRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Dog findDog(String name) {
        return mongoTemplate
                .findOne(
                        Query.query(Criteria.where("name").is(name)),
                        Dog.class
                );
    }

    public void insertDog(Dog dog) {
        mongoTemplate.insert(dog);
    }

    public void updateDogs(String name, Dog dog) {
        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", dog.getName());
        update.set("kind", dog.getKind());
        update.set("ownerName", dog.getOwnerName());
        update.set("ownerPhoneNumber", dog.getOwnerPhoneNumber());
        update.set("medicalRecords", findDog(name).getMedicalRecords());
        mongoTemplate.updateFirst(query, update, Dog.class);

    }


    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
    }

}