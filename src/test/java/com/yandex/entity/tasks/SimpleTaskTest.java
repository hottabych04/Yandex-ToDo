package com.yandex.entity.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleTaskTest {

    @Test
    @DisplayName("SimpleTasks equals by id")
    void simpleTasksEqualsById() {
        SimpleTask task1 = new SimpleTask("task1", "111111");
        SimpleTask task2 = new SimpleTask("task2", "222222");
        task1.setId(1L);
        task2.setId(1L);
        assertThat(task1).isEqualTo(task2);
    }
}
