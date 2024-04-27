package com.yandex.service.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;
import com.yandex.enums.TasksStatus;
import com.yandex.service.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void prepare(){
        manager = Managers.getDefault();
    }

    @Test
    @DisplayName("Manager add tasks and search it by id correctly")
    void addTasks(){
        assertAll(
                () -> assertThat(manager.addNewSimpleTask(new SimpleTask("name", "description"))).isTrue(),
                () -> assertThat(manager.addNewEpic(new Epic("name", "description"))).isTrue(),
                () -> assertThat(manager.addNewSubTask(new SubTask("name", "description", manager.getEpicById(2L)))).isTrue(),
                () -> assertThat(manager.getAllSimpleTasks()).isNotNull(),
                () -> assertThat(manager.getAllSubTasks()).isNotNull(),
                () -> assertThat(manager.getAllEpics()).isNotNull()
        );
    }

    @Nested
    @DisplayName("Tasks in manager are immutable")
    class ImmutabilityTasksInManager{

        @Test
        @DisplayName("SimpleTasks are immutable")
        void immutabilitySimpleTasksAddedToManager(){
            SimpleTask simpleTask = new SimpleTask("name", "description");
            manager.addNewSimpleTask(simpleTask);
            simpleTask.setId(3L);
            simpleTask.setName(null);
            simpleTask.setDescription(null);
            simpleTask.setStatus(TasksStatus.DONE);
            assertAll(
                    () -> assertThat(manager.getSimpleTaskById(1L)).isNotEqualTo(simpleTask),
                    () -> assertThat(manager.getSimpleTaskById(1L).getName()).isNotEqualTo(simpleTask.getName()),
                    () -> assertThat(manager.getSimpleTaskById(1L).getDescription()).isNotEqualTo(simpleTask.getDescription()),
                    () -> assertThat(manager.getSimpleTaskById(1L).getStatus()).isNotEqualTo(simpleTask.getStatus())
            );
        }

        @Test
        @DisplayName("SubTasks are immutable")
        void immutabilitySubTasksAddedToManager(){
            manager.addNewEpic(new Epic("name", "description"));
            Epic epic = manager.getEpicById(1L);
            SubTask subTask = new SubTask("name", "description", epic);
            manager.addNewSubTask(subTask);
            subTask.setId(3L);
            subTask.setName(null);
            subTask.setDescription(null);
            subTask.setStatus(TasksStatus.DONE);
            assertAll(
                    () -> assertThat(manager.getSubTaskById(2L)).isNotEqualTo(subTask),
                    () -> assertThat(manager.getSubTaskById(2L).getName()).isNotEqualTo(subTask.getName()),
                    () -> assertThat(manager.getSubTaskById(2L).getDescription()).isNotEqualTo(subTask.getDescription()),
                    () -> assertThat(manager.getSubTaskById(2L).getStatus()).isNotEqualTo(subTask.getStatus())
            );
        }

        @Test
        @DisplayName("Epics are immutable")
        void immutabilityEpicsAddedToManager(){
            Epic epic = new Epic("name", "description");
            manager.addNewEpic(epic);
            epic.setId(3L);
            epic.setName(null);
            epic.setDescription(null);
            epic.setStatus(TasksStatus.DONE);
            assertAll(
                    () -> assertThat(manager.getEpicById(1L)).isNotEqualTo(epic),
                    () -> assertThat(manager.getEpicById(1L).getName()).isNotEqualTo(epic.getName()),
                    () -> assertThat(manager.getEpicById(1L).getDescription()).isNotEqualTo(epic.getDescription()),
                    () -> assertThat(manager.getEpicById(1L).getStatus()).isNotEqualTo(epic.getStatus())
            );
        }
    }
}
