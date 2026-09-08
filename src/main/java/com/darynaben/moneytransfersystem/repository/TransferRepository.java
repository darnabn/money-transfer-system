package com.darynaben.moneytransfersystem.repository;

import com.darynaben.moneytransfersystem.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
