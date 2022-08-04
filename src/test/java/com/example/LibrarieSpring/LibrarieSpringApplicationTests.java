package com.example.LibrarieSpring;

import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibrarieSpringApplicationTests {

	@Autowired
	UtilizatorService us;

	@Test
	@Transactional
	void contextLoads() {

		List<Utilizator> utilizatori1=us.getAllUtilizatori();
		int i=utilizatori1.size();
		int j=i+1;
		int l=0;
		us.adauaga(new Utilizator("ada@gmail.com","parola22"));

		List<Utilizator> utilizatori2=us.getAllUtilizatori();
		l= utilizatori2.size();
		assertEquals(l,j);
	}

}
