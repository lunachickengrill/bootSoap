package eu.vrtime.bootsoap.endpoints;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import eu.vrtime.bootsoap.gen.GetUserRequest;
import eu.vrtime.bootsoap.gen.GetUserResponse;
import eu.vrtime.bootsoap.gen.User;
import eu.vrtime.bootsoap.gen.UserDto;
import eu.vrtime.bootsoap.repositories.UserRepository;
import eu.vrtime.bootsoap.service.DtoConverter;

@Endpoint
public class UserEndpoint {
	
	Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

	private static final String NAMESPACE_URI = "http://www.vrtime.eu/bootsoap/gen";

	private UserRepository repo;
	private DtoConverter converter;

	@Autowired
	public UserEndpoint(UserRepository repo, DtoConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
	@ResponsePayload
	public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
		Validate.notNull(request.getCustomerId());
		logger.debug(request.toString());
		String customerId = request.getCustomerId();
		Optional<User> user = repo.findByCustomerId(customerId);
		GetUserResponse response = new GetUserResponse();
		if (user.isPresent()) {
			UserDto dto = converter.toDto(user.get());
			response.setUser(dto);

		} else {
			UserDto dto = new UserDto();
			dto.setFirstname("not found");
			dto.setLastname("not found");
			response.setUser(dto);
		}

		return response;
	}

}
