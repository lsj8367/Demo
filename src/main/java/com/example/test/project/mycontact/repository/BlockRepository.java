package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
