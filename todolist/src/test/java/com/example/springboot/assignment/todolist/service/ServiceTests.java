package com.example.springboot.assignment.todolist;

import com.example.springboot.assignment.todolist.dao.TodoRepo;
import com.example.springboot.assignment.todolist.dao.UserRepo;
import com.example.springboot.assignment.todolist.entity.TodoItem;
import com.example.springboot.assignment.todolist.entity.User;
import com.example.springboot.assignment.todolist.service.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceTests {
	@Autowired
	private TodoServiceImpl todoService;
	@MockBean
	private TodoRepo todoRepo;

	@MockBean
	private UserRepo userRepo;

	@Test
	void findAllTest() {
		when(todoRepo.findAll())
				.thenReturn(Stream.of(new TodoItem("Dummy", true), new TodoItem("Dummy2", false))
						.collect(Collectors.toList()));
		assertEquals(2, todoService.findAll().size());

	}
	@Test
	void saveTest(){
		TodoItem item=new TodoItem("Dummy",false);
		todoService.save(item);
		verify(todoRepo,times(1)).save(item);
	}
	@Test
	void deleteTest(){
		int id=1;
		todoService.deleteById(id);
		verify(todoRepo,times(1)).deleteById(id);
	}
	@Test
	void findByIdTest(){
		TodoItem item=new TodoItem("Dummy",false);
		when(todoRepo.findById(1)).thenReturn(Optional.of(item));
		assertThat(todoService.findById(1)).isEqualTo(item);
	}
	@Test
	void findUserTest(){
		User user= new User("dummy", "abc");
		when(userRepo.findByUserName("dummy")).thenReturn(user);
		assertThat(todoService.findByUserName("dummy")).isEqualTo(user);
	}
}
