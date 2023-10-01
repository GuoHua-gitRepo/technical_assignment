package com.example.technical_test.model.JPAService;


import com.example.technical_test.model.Price;
import com.example.technical_test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(String userId);
}
