package com.dga.homebooking.repo;

import com.dga.homebooking.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
        User findByEmail(String email);
}
