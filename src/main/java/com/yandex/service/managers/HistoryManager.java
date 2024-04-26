package com.yandex.service.managers;

import com.yandex.entity.tasks.SimpleTask;

import java.util.List;

public interface HistoryManager {

    void add(SimpleTask task);

    List<SimpleTask> getHistory();
}
