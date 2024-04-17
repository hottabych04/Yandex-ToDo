package com.yandex.service;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private Long uniqueId = 1L;
    private final HashMap<Long, SimpleTask> simpleTasks = new HashMap<>();
    private final HashMap<Long, SubTask> subTasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();

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
        epics.values()
                .forEach(it ->{
                    it.deleteAllSubTasks();
                    it.statusControl();
                });
        subTasks.clear();
    }

    public void deleteAllEpics(){
        subTasks.clear();
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
        Epic epic = epics.get(newTask.getEpicId());
        epic.addSubTask(newTask);
        epic.statusControl();
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
        Epic epic = epics.get(task.getEpicId());
        epic.updateSubTask(task);
        subTasks.put(task.getId(), task);
        epic.statusControl();
        return subTasks.get(task.getId());
    }

    public Epic epicUpdate(Epic epic){
        subTasks.values().stream()
                .filter(it -> Objects.equals(it.getEpicId(), epic.getId()))
                .map(SimpleTask::getId)
                .forEach(subTasks::remove);
        epics.get(epic.getId()).deleteEpic();
        epics.put(epic.getId(), epic);
        epics.get(epic.getId()).statusControl();
        return epics.get(epic.getId());
    }

    /*
    Удаление задачи по идентификатору
    */

    public void deleteSimpleTaskById(Long id){
        simpleTasks.remove(id);
    }

    public void deleteSubTaskById(Long id){
        Epic epic = epics.get(subTasks.get(id).getEpicId());
        epic.deleteSubTaskById(id);
        epic.statusControl();
        subTasks.remove(id);
    }

    public void deleteEpicById(Long id){
        subTasks.values().stream()
                .filter(it -> Objects.equals(it.getEpicId(), id))
                .map(SimpleTask::getId)
                .forEach(subTasks::remove);
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
