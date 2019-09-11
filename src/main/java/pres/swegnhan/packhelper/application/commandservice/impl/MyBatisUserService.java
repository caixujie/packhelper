package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.application.commandservice.UserService;
import pres.swegnhan.packhelper.core.User;
import pres.swegnhan.packhelper.infrastructure.userrepository.UserRepository;

@Service
public class MyBatisUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void create(User user) {
        userRepository.insert(user);
    }
}
