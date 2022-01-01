package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Inquiry;
import com.typicalcoderr.Deliverit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Integer> {
    List<Inquiry> findInquiriesByUser_EmailOrderByCreatedAtDesc(String email);
    List<Inquiry> findInquiriesByInquiryStatusIsLikeAndShipmentWarehouseWarehouseNumberOrderByCreatedAtDesc(String inquiryStatus, String warehouseNumber);
    List<Inquiry> findInquiriesByInquiryStatusIsLikeOrderByCreatedAtDesc(String inquiryStatus);
}
