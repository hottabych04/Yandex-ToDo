package com.yandex.service.util;

import com.yandex.service.managers.HistoryManager;
import com.yandex.service.managers.InMemoryHistoryManager;
import com.yandex.service.managers.InMemoryTaskManager;
import com.yandex.service.managers.TaskManager;

public class Managers {

    /*
        Получение дефолтного менеджера задач
    */

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    /*
        Получение дефолтного менеджера историй
    */

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}
