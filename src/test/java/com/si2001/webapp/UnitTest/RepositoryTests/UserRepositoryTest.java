package com.si2001.webapp.UnitTest.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.print.Pageable;
import java.util.List;

import com.si2001.webapp.Application;
import com.si2001.webapp.entity.User;
import com.si2001.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest()
@ContextConfiguration(classes = Application.class)
public class UserRepositoryTest
{

	@Autowired
	private UserRepository userRepository;

	@Test
	public void TestfindByDescrizioneLike()
	{
		List<User> items = userRepository.selByUsernameLike("ACQUA ULIVETO%");
		assertEquals(2, items.size());
	}

	@Test
	public void TestfindByDescrizioneLikePage()
	{
		List<User> items = userRepository.findByUserLike("ACQUA%", (Pageable) PageRequest.of(0, 10));
		assertEquals(10, items.size());
	}


	@Test
	public void TestfindByCodArt() throws Exception
	{
		assertThat(userRepository.findUserById("002000301"))
				.extracting(User::getDescrizione)
				.isEqualTo("ACQUA ULIVETO 15 LT");

	}


}
