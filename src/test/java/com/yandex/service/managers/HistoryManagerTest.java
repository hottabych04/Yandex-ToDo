package com.yandex.service.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.yandex.entity.tasks.SimpleTask;
import com.yandex.enums.TasksStatus;
import com.yandex.service.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoryManagerTest {

    private TaskManager manager;

    @BeforeEach
    void prepare(){
        manager = Managers.getDefault();
    }

    @Test
    void correctAddTaskToHistoryManager(){
        manager.addNewSimpleTask(new SimpleTask("name", "description"));
        SimpleTask task = manager.getSimpleTaskById(1L);
        SimpleTask historyTask = manager.getHistory().stream().findFirst().get();
        task.setId(3L);
        task.setName(null);
        task.setDescription(null);
        task.setStatus(TasksStatus.DONE);
        assertAll(
                () -> assertThat(manager.getSimpleTaskById(1L)).isNotEqualTo(task),
                () -> assertThat(manager.getSimpleTaskById(1L).getName()).isNotEqualTo(task.getName()),
                () -> assertThat(manager.getSimpleTaskById(1L).getDescription()).isNotEqualTo(task.getDescription()),
                () -> assertThat(manager.getSimpleTaskById(1L).getStatus()).isNotEqualTo(task.getStatus())
        );
    }
}
