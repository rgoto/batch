package com.experimentos.batch.configuration;

import com.experimentos.batch.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;

public class UserItemProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);
    private static final Map<String, String> DETP_NAMES = new HashMap<>();

    public UserItemProcessor() {
        DETP_NAMES.put("001", "Technology");
        DETP_NAMES.put("002", "Operations");
        DETP_NAMES.put("003", "Support");
    }

    @Override
    public User process(User user) throws Exception {
        String name = user.getName().toUpperCase();
        String dept = DETP_NAMES.get(user.getDept());

        User tranformationUser = new User(name, dept, user.getSalary());
        log.info("Converting (" + user + ") into (" + tranformationUser + ")");

        return tranformationUser;
    }
}
