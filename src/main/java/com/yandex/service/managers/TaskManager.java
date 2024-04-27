package com.yandex.service.managers;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;

import java.util.List;

public interface TaskManager {
    /*
        Получение списков задач всех типов
    */

    List<SimpleTask> getAllSimpleTasks();

    List<SubTask> getAllSubTasks();

    List<Epic> getAllEpics();

    /*
        Удаление списков задач всех типов
    */

     void deleteAllSimpleTask();

     void deleteAllSubTask();

     void deleteAllEpics();

    /*
        Получение задачи по идентификатору
    */

     SimpleTask getSimpleTaskById(Long id);

     SubTask getSubTaskById(Long id);

     Epic getEpicById(Long id);

    /*
        Создание новых задач
    */

     boolean addNewSimpleTask(SimpleTask newTask);

     boolean addNewSubTask(SubTask newTask);

     boolean addNewEpic(Epic newEpic);

    /*
        Обновление задач
    */

     boolean simpleTaskUpdate(SimpleTask task);

     boolean subTaskUpdate(SubTask task);

     boolean epicUpdate(Epic epic);

    /*
        Удаление задачи по идентификатору
    */

     void deleteSimpleTaskById(Long id);

     void deleteSubTaskById(Long id);

     void deleteEpicById(Long id);

    /*
        Получение списка всех подзадач определенного эпика
    */

     List<SubTask> getAllSubTaskFromEpicByEpicId(Long id);

    /*
        Получение истории просмотров задач
    */

    List<SimpleTask> getHistory();
}
