package com.dga.homebooking.repo;

import com.dga.homebooking.models.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<People,Integer> {
}
