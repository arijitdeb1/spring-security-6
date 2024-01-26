package com.arijit.springsecurity.service;

import com.arijit.springsecurity.entity.Employee;
import com.arijit.springsecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password=null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Employee> employees = employeeRepository.findByEmail(username);
        if(employees.size()==0){
            throw new UsernameNotFoundException("No such user with name "+ username +" is present in the system");
        }else{
            userName = employees.get(0).getEmail();
            password = employees.get(0).getPwd();
            authorities.add(new SimpleGrantedAuthority(employees.get(0).getRole()));

        }

        return new User(userName,password,authorities);
    }
}
