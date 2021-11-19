package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Integer> {
}
