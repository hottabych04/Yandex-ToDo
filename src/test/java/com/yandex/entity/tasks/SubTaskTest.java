package com.yandex.entity.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SubTaskTest {

    @Test
    @DisplayName("SubTasks equals by id")
    void simpleTasksEqualsById() {
        SubTask task1 = new SubTask("task1", "111111", null);
        SubTask task2 = new SubTask("task2", "222222", null);
        task1.setId(1L);
        task2.setId(1L);
        assertThat(task1).isEqualTo(task2);
    }
}
