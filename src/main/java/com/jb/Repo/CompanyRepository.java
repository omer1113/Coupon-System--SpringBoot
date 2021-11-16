package com.jb.Repo;

import com.jb.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByEmailAndPassword(String email, String password);

    Company findById(int companyID);

    Company findByEmailAndPassword(String email,String password);

    Company findByEmail(String email);
    Company getOneByEmailAndPassword(String email,String password);
    boolean existsByEmail(String email);

    boolean existsByName(String name);

}
