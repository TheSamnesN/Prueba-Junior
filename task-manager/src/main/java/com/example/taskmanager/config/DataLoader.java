package com.example.taskmanager.config;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, TaskRepository taskRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User alice = new User("Alice", "alice@example.com", null);
                User bob = new User("Bob", "bob@example.com", null);

                userRepository.save(alice);
                userRepository.save(bob);

                Task t1 = new Task("Comprar harina", "Comprar 2kg de harina para pastelería", alice);
                t1.setStatus(TaskStatus.PENDING);
                Task t2 = new Task("Refactor componentes", "Revisar componente Header en React", bob);
                t2.setStatus(TaskStatus.IN_PROGRESS);

                taskRepository.save(t1);
                taskRepository.save(t2);
            }
        };
    }
}