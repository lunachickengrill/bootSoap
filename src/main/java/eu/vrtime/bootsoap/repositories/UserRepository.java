package eu.vrtime.bootsoap.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.vrtime.bootsoap.gen.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findById(Long id);

	public void deleteById(Long id);

	public void delete(User entity);

	public <S extends User> S saveAndFlush(S entity);

	public long count();
	
	public Optional<User> findByCustomerId(String customerId);
	
	

}
