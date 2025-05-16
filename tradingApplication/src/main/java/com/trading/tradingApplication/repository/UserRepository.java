package com.trading.tradingApplication.repository;

import com.trading.tradingApplication.model.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByEmail(String email);
}
