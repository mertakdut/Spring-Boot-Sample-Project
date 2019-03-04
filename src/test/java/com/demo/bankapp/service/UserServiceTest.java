package com.demo.bankapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.bankapp.model.User;
import com.demo.bankapp.repository.UserRepository;
import com.demo.bankapp.service.concretions.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@MockBean
	private UserRepository repository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	private UserService service;

	@Before
	public void setUp() {
		service = new UserService(repository, passwordEncoder);
	}

	@Test
	public void findAll() {
		List<User> mockedUserList = new ArrayList<>();
		mockedUserList.add(new User("Mert", "mert123", "52125082721"));

		Mockito.when(repository.findAll()).thenReturn(mockedUserList);
		List<User> foundUserList = service.findAll();

		assertThat(foundUserList).isEqualTo(mockedUserList);
	}

	@Test
	public void createNewUser() {
		String mockedEncodedPassword = "EncodedPassword5128uawyr";
		User mockedUser = new User("Mert", "mert123", "52125082721");

		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(mockedEncodedPassword);
		Mockito.when(repository.save(Mockito.any())).thenReturn(mockedUser);

		User createdUser = service.createNewUser(mockedUser);
		
		assertThat(createdUser.getUsername()).isEqualTo(mockedUser.getUsername());
		assertThat(createdUser.getPassword()).isEqualTo(mockedUser.getPassword());
		assertThat(createdUser.getTcno()).isEqualTo(mockedUser.getTcno());
	}
	
	@Test
	public void findByUserName() {
		User mockedUser = new User("Mert", "mert123", "52125082721");
		Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(mockedUser);
		
		User createdUser = service.findByUserName(mockedUser.getUsername());
		
		assertThat(createdUser.getUsername()).isEqualTo(mockedUser.getUsername());
		assertThat(createdUser.getPassword()).isEqualTo(mockedUser.getPassword());
		assertThat(createdUser.getTcno()).isEqualTo(mockedUser.getTcno());
	}
	
	@Test
	public void findByTcno() {
		User mockedUser = new User("Mert", "mert123", "52125082721");
		Mockito.when(repository.findByTcno(Mockito.anyString())).thenReturn(mockedUser);
		
		User createdUser = service.findByTcno(mockedUser.getTcno());
		
		assertThat(createdUser.getUsername()).isEqualTo(mockedUser.getUsername());
		assertThat(createdUser.getPassword()).isEqualTo(mockedUser.getPassword());
		assertThat(createdUser.getTcno()).isEqualTo(mockedUser.getTcno());
	}
	
	@Test
	public void loadUserByUsername() {
		User mockedUser = new User("Mert", "mert123", "52125082721");
		Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(mockedUser);
		
		UserDetails loadedUser = service.loadUserByUsername(mockedUser.getUsername());
		
		assertThat(loadedUser.getUsername()).isEqualTo(mockedUser.getUsername());
		assertThat(loadedUser.getPassword()).isEqualTo(mockedUser.getPassword());
		assertThat(loadedUser).isExactlyInstanceOf(org.springframework.security.core.userdetails.User.class);	
	}
	
	@Test
	public void isUsernameExist() {
		User mockedUser = new User("Mert", "mert123", "52125082721");
		Mockito.when(repository.findByUsername(mockedUser.getUsername())).thenReturn(mockedUser);
		
		boolean isUsernameExist = service.isUsernameExist(mockedUser.getUsername());
		assertThat(isUsernameExist).isEqualTo(true);
	}
	
	@Test
	public void isTcNoExist() {
		User mockedUser = new User("Mert", "mert123", "52125082721");
		Mockito.when(repository.findByTcno(mockedUser.getTcno())).thenReturn(mockedUser);
		
		boolean isTcnoExist = service.isTcnoExist(mockedUser.getTcno());
		assertThat(isTcnoExist).isEqualTo(true);
	}
	
}
