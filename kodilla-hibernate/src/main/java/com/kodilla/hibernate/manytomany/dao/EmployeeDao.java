package com.kodilla.hibernate.manytomany.dao;

import com.kodilla.hibernate.manytomany.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeDao extends CrudRepository<Employee, Integer> {

    @Query
    List<Employee> findByLastName(@Param("LASTNAME") String lastname);

    @Query("SELECT e FROM Employee e WHERE e.lastname LIKE %:partialLastName%")
    List<Employee> findByPartialLastName(@Param("partialLastName") String partialLastName);
}
