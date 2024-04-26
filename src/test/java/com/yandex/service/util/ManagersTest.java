package com.yandex.service.util;

import com.yandex.service.managers.HistoryManager;
import com.yandex.service.managers.TaskManager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    @Test
    void managersDontReturnNull(){
        TaskManager taskManagerDefault = Managers.getDefault();
        HistoryManager historyManagerDefault = Managers.getDefaultHistory();
        assertAll(
                () -> assertNotNull(taskManagerDefault),
                () -> assertNotNull(historyManagerDefault)
        );
    }
}
