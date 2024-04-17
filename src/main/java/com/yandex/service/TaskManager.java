package com.yandex.service;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;
import com.yandex.enums.TasksStatus;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private static Long uniqueId = 1L;
    private HashMap<Long, SimpleTask> simpleTasks;
    private HashMap<Long, SubTask> subTasks;
    private HashMap<Long, Epic> epics;

    public TaskManager() {
        simpleTasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    /*
    Получение списков задач всех типов
    */

    public List<SimpleTask> getAllSimpleTasks(){
        return new ArrayList<>(simpleTasks.values());
    }

    public List<SubTask> getAllSubTasks(){
        return new ArrayList<>(subTasks.values());
    }

    public List<Epic> getAllEpics(){
        return new ArrayList<>(epics.values());
    }

    /*
    Удаление списков задач всех типов
    */

    public void deleteAllSimpleTask(){
        simpleTasks.clear();
    }

    public void deleteAllSubTask(){
        subTasks.values()
                        .forEach(it -> {
                            epics.get(it.getEpicId()).deleteSubTaskById(it.getId());
                        });
        subTasks.clear();
    }

    public void deleteAllEpics(){
        epics.values()
                .forEach( it -> {
                    Set<Long> delSubTasksIds = it.deleteEpic();
                    delSubTasksIds
                            .forEach( id -> {
                                subTasks.remove(id);
                            });
                });
        epics.clear();
    }

    /*
    Получение задачи по идентификатору
    */

    public SimpleTask getSimpleTaskById(Long id){
        return simpleTasks.get(id);
    }

    public SubTask getSubTaskById(Long id){
        return subTasks.get(id);
    }

    public Epic getEpicById(Long id){
        return epics.get(id);
    }

    /*
    Создание новых задач
    */

    public SimpleTask addNewSimpleTask(SimpleTask newTask){
        newTask.setId(uniqueId++);
        simpleTasks.putIfAbsent(newTask.getId(), newTask);
        return simpleTasks.get(newTask.getId());
    }

    public SubTask addNewSubTask(SubTask newTask){
        newTask.setId(uniqueId++);
        subTasks.putIfAbsent(newTask.getId(), newTask);
        epics.get(newTask.getEpicId()).addSubTask(newTask);
        return subTasks.get(newTask.getId());
    }

    public Epic addNewEpic(Epic newEpic){
        newEpic.setId(uniqueId++);
        epics.putIfAbsent(newEpic.getId(), newEpic);
        return epics.get(newEpic.getId());
    }

    /*
    Обновление задач
    */

    public SimpleTask simpleTaskUpdate(SimpleTask task){
        simpleTasks.put(task.getId(), task);
        return simpleTasks.get(task.getId());
    }

    public SubTask subTaskUpdate(SubTask task){
        epics.get(task.getEpicId()).updateSubTask(task);
        subTasks.put(task.getId(), task);
        return subTasks.get(task.getId());
    }

    public Epic epicUpdate(Epic epic){
        Set<Long> delSubTasksIds = epics.get(epic.getId()).deleteEpic();
        delSubTasksIds
                .forEach(it -> {
                    subTasks.remove(it);
                });
        epics.put(epic.getId(), epic);
        return epics.get(epic.getId());
    }

    /*
    Удаление задачи по идентификатору
    */

    public void deleteSimpleTaskById(Long id){
        simpleTasks.remove(id);
    }

    public void deleteSubTaskById(Long id){
        epics.get(subTasks.get(id).getEpicId()).deleteSubTaskById(id);
        subTasks.remove(id);
    }

    public void deleteEpicById(Long id){
        Set<Long> delSubTasksIds = epics.get(id).deleteEpic();
        delSubTasksIds
                .forEach(it -> {
                    subTasks.remove(it);
                });
        epics.remove(id);
    }

    /*
    Получение списка всех подзадач определенного эпика
    */

    public List<SubTask> getAllSubTaskFromEpicByEpicId(Long id){
        return subTasks.values().stream()
                .filter(it -> Objects.equals(it.getEpicId(), id))
                .collect(Collectors.toList());
    }














}
