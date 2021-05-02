package com.example.demo.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserHistoryRepository : JpaRepository<UserHistory, Long>