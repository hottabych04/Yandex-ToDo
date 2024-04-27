package com.yandex.service.managers;

import com.yandex.entity.tasks.SimpleTask;

import java.util.List;

public interface HistoryManager {

    /*
        Метод для заполнения истории просмотров
    */

    void add(SimpleTask task);

    /*
        Получение истории просмотров задач
    */

    List<SimpleTask> getHistory();
}
