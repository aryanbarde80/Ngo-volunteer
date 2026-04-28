package com.rsai.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rsai.model.Volunteer;
import java.util.List;

public interface VolunteerRepo extends JpaRepository<Volunteer, Long> {
	Volunteer findByPhone(String phone);

	@Query("SELECT v FROM Volunteer v WHERE LOWER(v.email) = LOWER(:email) AND v.password = :password")
	Volunteer findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	List<Volunteer> findByZone(String zone);

	List<Volunteer> findByStatus(String status);
}