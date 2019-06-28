package eu.vrtime.bootsoap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.vrtime.bootsoap.gen.User;
import eu.vrtime.bootsoap.gen.UserDto;
import eu.vrtime.bootsoap.repositories.UserRepository;
import eu.vrtime.bootsoap.service.DtoConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootSoapApplicationTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private DtoConverter converter;

	@Test
	public void contextLoads() {
	}

	
	@Test
	public void saveUserTest() {
		User user = new User("1111","firstname","lastname");
		User dbUser = repo.saveAndFlush(user);
		assertNotNull(dbUser);
		assertTrue(dbUser.getCustomerId().equals("1111"));
	}
	
	@Test
	public void convertTest() {
		User user = new User("2222","firstname","lastname");
		User dbUser = repo.saveAndFlush(user);
		
		UserDto dto = converter.toDto(dbUser);
		
		assertNotNull(dto);
		assertTrue(dto.getCustomerId().equals("2222"));
		
		User converted = converter.toUser(dto);
		assertNotNull(converted);
		assertTrue(user.getCustomerId().equals("2222"));
	}
	
	@Test
	public void autoconfigServiceTest() {
		long count = repo.count();
		assertTrue(count==3);
	}
}
