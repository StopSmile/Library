package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_find_no_users_if_repository_is_empty() {
        Iterable<User> users = userRepository.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    public void should_store_a_user() {
        User user = userRepository.save(new User("Mike", "mike@gmail.com"));

        assertThat(user).hasFieldOrPropertyWithValue("name", "Mike");
        assertThat(user).hasFieldOrPropertyWithValue("email", "mike@gmail.com");
    }

    @Test
    void should_returnUserByNameIfExist() throws Exception {
        this.testEntityManager.persist(new User("Mike", "mike@mail.com"));
        User user = this.userRepository.findByName("Mike");

        assertThat(user.getName()).isEqualTo("Mike");
        assertThat(user.getEmail()).isEqualTo("mike@mail.com");
    }

    @Test
    void should_returnUserByEmailIfExist() throws Exception {
        this.testEntityManager.persist(new User("Mike", "mike@mail.com"));
        User user = this.userRepository.findByEmail("mike@mail.com");

        assertThat(user.getName()).isEqualTo("Mike");
        assertThat(user.getEmail()).isEqualTo("mike@mail.com");
    }

    @Test
    public void should_find_all_users() {
        User user1 = new User("Mike", "mike@gmail.com");
        testEntityManager.persist(user1);
        User user2 = new User("Jack", "jack@gmail.com");
        testEntityManager.persist(user2);
        User user3 = new User("James", "james@gmail.com");
        testEntityManager.persist(user3);

        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(3).contains(user1, user2, user3);
    }

    @Test
    public void should_find_users_by_id() {
        User user1 = new User("Mike", "mike@gmail.com");
        testEntityManager.persist(user1);
        User user2 = new User("Jack", "jack@gmail.com");
        testEntityManager.persist(user2);

        User foundUser = userRepository.findById(user2.getId()).get();
        assertThat(foundUser).isEqualTo(user2);
    }

    @Test
    public void should_delete_all_users() {
        testEntityManager.persist(new User("Mike", "mike@mail.com"));
        testEntityManager.persist(new User("Jack", "jack@gmail.com"));

        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void should_delete_tutorial_by_id() {
        User user1 = new User("Mike", "mike@gmail.com");
        testEntityManager.persist(user1);
        User user2 = new User("Jack", "jack@gmail.com");
        testEntityManager.persist(user2);
        User user3 = new User("James", "james@gmail.com");
        testEntityManager.persist(user3);

        userRepository.deleteById(user3.getId());

        Iterable<User> users = userRepository.findAll();

        assertThat(users).hasSize(2).contains(user1, user2);
    }


}
