package com.yandex.service.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.yandex.entity.tasks.SimpleTask;
import com.yandex.enums.TasksStatus;
import com.yandex.service.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HistoryManagerTest {

    private TaskManager manager;

    @BeforeEach
    void prepare(){
        manager = Managers.getDefault();
    }

    @Nested
    @DisplayName("Viewed tasks add to history correctly")
    class CorrectlyAddTasksToHistoryManager {

        @Test
        @DisplayName("Tasks added ti history are immutable")
        void tasksAddedToHistoryManagerImmutable(){
            manager.addNewSimpleTask(new SimpleTask("name", "description"));
            SimpleTask task = manager.getSimpleTaskById(1L);
            SimpleTask historyTask = manager.getHistory().stream().findFirst().orElse(task);
            task.setName(null);
            task.setDescription(null);
            task.setStatus(TasksStatus.DONE);
            manager.simpleTaskUpdate(task);
            assertAll(
                    () -> assertThat(manager.getSimpleTaskById(1L).getName()).isNotEqualTo(historyTask.getName()),
                    () -> assertThat(manager.getSimpleTaskById(1L).getDescription()).isNotEqualTo(historyTask.getDescription()),
                    () -> assertThat(manager.getSimpleTaskById(1L).getStatus()).isNotEqualTo(historyTask.getStatus())
            );
        }
    }
}
