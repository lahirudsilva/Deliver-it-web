package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.Model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry,Integer> {
}
