package com.typicalcoderr.Deliverit.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 7:08 PM
 */
public class UserUtilities {

    public static boolean hasRole(String role){
        boolean hasRole = false;
        try{
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for (GrantedAuthority authority : authorities){
                hasRole = authority.getAuthority().equals(role);
                System.out.println(authority.getAuthority());
                if(hasRole){
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return hasRole;
    }

}
