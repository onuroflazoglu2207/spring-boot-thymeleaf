package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserModel, Long> {


    @Query("select u from UserModel u where "
            + "(:#{#model.name} is null or u.name = :#{#model.name}) and "
            + "(:#{#model.birthday} is null or u.birthday = :#{#model.birthday}) and "
            + "(:#{#model.gender} is null or u.gender = :#{#model.gender}) and "
            + "(:#{#model.email} is null or u.email = :#{#model.email}) and "
            + "(:#{#model.phone} is null or u.phone = :#{#model.phone}) and"
            + "(:#{#model.password} is null or u.password = :#{#model.password}) ")
    public Page<UserModel> getByValuesAndPagination(@Param("model") UserModel model, Pageable pageable);

    @Query("select u from UserModel u where "
            + "(:#{#model.name} is not null and u.name = :#{#model.name}) or "
            + "(:#{#model.birthday} is not null and u.birthday = :#{#model.birthday}) or "
            + "(:#{#model.gender} is not null and u.gender = :#{#model.gender}) or "
            + "(:#{#model.email} is not null and u.email = :#{#model.email}) or "
            + "(:#{#model.phone} is not null and u.phone = :#{#model.phone}) or"
            + "(:#{#model.password} is not null and u.password = :#{#model.password}) ")
    public Page<UserModel> getByValuesOrPagination(@Param("model") UserModel model, Pageable pageable);

}


