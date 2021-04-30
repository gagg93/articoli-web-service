package com.si2001.webapp.repository;

import com.si2001.webapp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @Query(value = "SELECT * FROM User WHERE username like :field", nativeQuery = true)
    List<User> selByUsernameLike(@Param("field") String resField);

    List<User> findByUserLike(String field, Pageable pageable);

    User findUserById(int id);
}
