package com.experimentos.batch.configuration;

import com.experimentos.batch.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.List;

@StepScope
public class ItemReaderUser implements ItemReader<User> {

    @Value("#{jobParameters['filename']}")
    private String filename;

    private List<User> users;
    private int index;

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        User user = null;

        if (index < users.size()) {
            user = users.get(index);
            index++;
        }
        System.out.println("filename param: " + filename);

        return user;
    }

    private void newArray() {
        this.users = new ArrayList<>();
        users.add(new User("a", "001", 1400));
        users.add(new User("b", "001", 1400));
        users.add(new User("c", "001", 1400));
        users.add(new User("d", "001", 1400));
    }
}
