package com.experimentos.batch.configuration;

import com.experimentos.batch.model.User;
import com.experimentos.batch.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserItemWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {
        userRepository.saveAll(users);
    }

}
