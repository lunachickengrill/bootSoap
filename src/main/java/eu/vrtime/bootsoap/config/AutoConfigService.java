package eu.vrtime.bootsoap.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.vrtime.bootsoap.gen.User;
import eu.vrtime.bootsoap.repositories.UserRepository;

@Service
public class AutoConfigService {
	
	private UserRepository repo;
	
	@Autowired
	public AutoConfigService(UserRepository repo) {
		this.repo=repo;
	}
	
	@PostConstruct
	private void initData() {
		User user1 = new User("1111","user1","lastname");
		User user2 = new User("2222","user2","lastname");
		User user3 = new User("3333","user3", "lastname");
		
		repo.saveAndFlush(user1);
		repo.saveAndFlush(user2);
		repo.saveAndFlush(user3);
	}

}
