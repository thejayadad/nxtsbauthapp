package com.student.studentlog.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.student.studentlog.entity.UserEntity;
import com.student.studentlog.models.User;
import com.student.studentlog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();

      List<User> users = userEntities
            .stream()
            .map(userEntity -> new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
            ))

            .collect(Collectors.toList());
            return users;
    }

    @Override
    public User getUserById(Long id){
        UserEntity userEntity
            = userRepository.findById(id).get();

            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            return user;
    }
    @Override
    public boolean deleteUser(Long id) {
        UserEntity user =  userRepository.findById(id).get();
        userRepository.delete(user);
        return true;
    }

    @Override
    public User updateUser(Long id, User user) {
        UserEntity userEntity =
                userRepository.findById(id).get();
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        userRepository.save(userEntity);
        return user;
    }

}
