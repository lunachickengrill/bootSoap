package eu.vrtime.bootsoap.service;

import org.springframework.stereotype.Service;

import eu.vrtime.bootsoap.gen.User;
import eu.vrtime.bootsoap.gen.UserDto;

@Service
public class DtoConverterImpl implements DtoConverter {

	public DtoConverterImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDto toDto(User user) {
		UserDto dto = new UserDto();
		dto.setCustomerId(user.getCustomerId());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		return dto;
	}

	@Override
	public User toUser(UserDto userDto) {
		User user = new User(userDto.getCustomerId(), userDto.getFirstname(), userDto.getLastname());
		return user;
	}

}
