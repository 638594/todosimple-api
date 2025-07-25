package com.luizroberto.todosimple.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizroberto.todosimple.models.Task;
import com.luizroberto.todosimple.models.User;
import com.luizroberto.todosimple.repositories.TaskRepository;
import com.luizroberto.todosimple.services.Exceptions.DataBindingViolationException;
import com.luizroberto.todosimple.services.Exceptions.ObjectNotFoundException;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Task nao encontrada: Id" + id + ", Tipo: " + Task.class.getName()));
    }
    
    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);;
        } catch (Exception e) {
            throw new DataBindingViolationException("Nao é possivel excluir, pois ha entidades relacionadas:");
        }

    }
}
