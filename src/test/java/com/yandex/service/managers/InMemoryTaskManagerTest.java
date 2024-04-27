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
    void addTasksAndSearchIt(){
        Long simpleTaskId = manager.addNewSimpleTask(new SimpleTask("name", "description"));
        Long epicId = manager.addNewEpic(new Epic("name", "description"));
        Long subTaskId = manager.addNewSubTask(new SubTask("name", "description", manager.getEpicById(epicId)));
        assertAll(
                () -> assertThat(manager.getSimpleTaskById(simpleTaskId)).isNotNull(),
                () -> assertThat(manager.getSubTaskById(subTaskId)).isNotNull(),
                () -> assertThat(manager.getEpicById(epicId)).isNotNull()
        );
    }

    @Test
    @DisplayName("Tasks added to the manager with Id do not conflict in the manager")
    void addNewTasksWithIdToManager(){
        SimpleTask simpleTask = new SimpleTask("name", "description");
        simpleTask.setId(4L);
        Long simpleTaskId = manager.addNewSimpleTask(simpleTask);
        Epic epic = new Epic("name", "description");
        epic.setId(4L);
        Long epicId = manager.addNewEpic(epic);
        SubTask subTask = new SubTask("name", "description", manager.getEpicById(epicId));
        subTask.setId(4L);
        Long subTaskId = manager.addNewSubTask(subTask);
        assertAll(
                () -> assertThat(manager.getSimpleTaskById(simpleTaskId)).isNotEqualTo(simpleTask),
                () -> assertThat(manager.getEpicById(epicId)).isNotEqualTo(epic),
                () -> assertThat(manager.getSubTaskById(subTaskId)).isNotEqualTo(subTask)

        );
    }

    @Nested
    @DisplayName("Tasks in manager are immutable")
    class ImmutabilityTasksInManager{

        @Test
        @DisplayName("SimpleTasks are immutable")
        void immutabilitySimpleTasksAddedToManager(){
            SimpleTask simpleTask = new SimpleTask("name", "description");
            Long simpleTaskId = manager.addNewSimpleTask(simpleTask);
            simpleTask.setId(3L);
            simpleTask.setName(null);
            simpleTask.setDescription(null);
            simpleTask.setStatus(TasksStatus.DONE);
            SimpleTask managersSimpleTask = manager.getSimpleTaskById(simpleTaskId);
            assertAll(
                    () -> assertThat(managersSimpleTask).isNotEqualTo(simpleTask),
                    () -> assertThat(managersSimpleTask.getName()).isNotEqualTo(simpleTask.getName()),
                    () -> assertThat(managersSimpleTask.getDescription()).isNotEqualTo(simpleTask.getDescription()),
                    () -> assertThat(managersSimpleTask.getStatus()).isNotEqualTo(simpleTask.getStatus())
            );
        }

        @Test
        @DisplayName("SubTasks are immutable")
        void immutabilitySubTasksAddedToManager(){
            Long epicId = manager.addNewEpic(new Epic("name", "description"));
            Epic epic = manager.getEpicById(epicId);
            SubTask subTask = new SubTask("name", "description", epic);
            Long subTaskId = manager.addNewSubTask(subTask);
            subTask.setId(3L);
            subTask.setName(null);
            subTask.setDescription(null);
            subTask.setStatus(TasksStatus.DONE);
            SubTask managersSubTask = manager.getSubTaskById(subTaskId);
            assertAll(
                    () -> assertThat(managersSubTask).isNotEqualTo(subTask),
                    () -> assertThat(managersSubTask.getName()).isNotEqualTo(subTask.getName()),
                    () -> assertThat(managersSubTask.getDescription()).isNotEqualTo(subTask.getDescription()),
                    () -> assertThat(managersSubTask.getStatus()).isNotEqualTo(subTask.getStatus())
            );
        }

        @Test
        @DisplayName("Epics are immutable")
        void immutabilityEpicsAddedToManager(){
            Epic epic = new Epic("name", "description");
            Long epicId = manager.addNewEpic(epic);
            epic.setId(3L);
            epic.setName(null);
            epic.setDescription(null);
            epic.setStatus(TasksStatus.DONE);
            Epic managersEpic = manager.getEpicById(epicId);
            assertAll(
                    () -> assertThat(managersEpic).isNotEqualTo(epic),
                    () -> assertThat(managersEpic.getName()).isNotEqualTo(epic.getName()),
                    () -> assertThat(managersEpic.getDescription()).isNotEqualTo(epic.getDescription()),
                    () -> assertThat(managersEpic.getStatus()).isNotEqualTo(epic.getStatus())
            );
        }
    }
}
