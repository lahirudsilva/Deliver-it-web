package com.typicalcoderr.Deliverit.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "user")
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNo;
    private String userRole;
}
