package com.yandex.service.managers;

import com.yandex.entity.tasks.SimpleTask;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final LinkedList<SimpleTask> history = new LinkedList<>();

    /*
    Метод для заполнения истории просмотров
    */

    @Override
    public void add(SimpleTask task) {
        if (history.size() == 10) history.pollFirst();
        SimpleTask newTask = new SimpleTask(task);
        history.add(newTask);
    }

    /*
    Получение истории просмотров задач
    */

    @Override
    public List<SimpleTask> getHistory() {
        return history;
    }
}
