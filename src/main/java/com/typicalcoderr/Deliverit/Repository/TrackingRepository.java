package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,Integer> {
}
