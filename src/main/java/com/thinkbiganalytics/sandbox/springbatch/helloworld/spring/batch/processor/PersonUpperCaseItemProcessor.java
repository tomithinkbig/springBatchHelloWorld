package com.thinkbiganalytics.sandbox.springbatch.helloworld.spring.batch.processor;

import com.thinkbiganalytics.sandbox.springbatch.helloworld.dataobjects.Person;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by ts186040 on 2/9/16.
 */
public class PersonUpperCaseItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        return new Person(
                person.getFirstName().toUpperCase(),
                person.getLastName().toUpperCase()
        );
    }
}
