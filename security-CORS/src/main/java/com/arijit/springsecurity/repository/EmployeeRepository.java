package com.arijit.springsecurity.repository;

import com.arijit.springsecurity.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public List<Employee> findByEmail(String email);
}
