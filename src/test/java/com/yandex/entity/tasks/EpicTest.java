package com.yandex.entity.tasks;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class EpicTest {

    @Test
    void simpleTasksEqualsById() {
        Epic task1 = new Epic("task1", "111111");
        Epic task2 = new Epic("task2", "222222");
        task1.setId(1L);
        task2.setId(1L);
        assertThat(task1).isEqualTo(task2);
    }

}
