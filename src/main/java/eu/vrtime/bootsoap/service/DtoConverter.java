package eu.vrtime.bootsoap.service;

import eu.vrtime.bootsoap.gen.User;
import eu.vrtime.bootsoap.gen.UserDto;

public interface DtoConverter {
	
	public UserDto toDto(User user);
	
	public User toUser(UserDto userDto);
	
	

}
