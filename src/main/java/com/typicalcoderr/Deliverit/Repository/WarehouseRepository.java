package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 12:47 AM
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
}
