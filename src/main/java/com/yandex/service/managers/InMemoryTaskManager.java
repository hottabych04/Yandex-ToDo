package com.yandex.service.managers;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;
import com.yandex.service.util.Managers;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager{
    private Long uniqueId = 1L;
    private final HashMap<Long, SimpleTask> simpleTasks = new HashMap<>();
    private final HashMap<Long, SubTask> subTasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HistoryManager history = Managers.getDefaultHistory();

    /*
        Получение списков задач всех типов
    */

    @Override
    public List<SimpleTask> getAllSimpleTasks(){
        return new ArrayList<>(simpleTasks.values());
    }

    @Override
    public List<SubTask> getAllSubTasks(){
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getAllEpics(){
        return new ArrayList<>(epics.values());
    }

    /*
        Удаление списков задач всех типов
    */

    @Override
    public void deleteAllSimpleTask(){
        simpleTasks.clear();
    }

    @Override
    public void deleteAllSubTask(){
        epics.values()
                .forEach(it ->{
                    it.deleteAllSubTasks();
                    it.statusControl();
                });
        subTasks.clear();
    }

    @Override
    public void deleteAllEpics(){
        subTasks.clear();
        epics.clear();
    }

    /*
        Получение задачи по идентификатору
    */

    @Override
    public SimpleTask getSimpleTaskById(Long id){
        history.add(simpleTasks.get(id));
        return new SimpleTask(simpleTasks.get(id));
    }

    @Override
    public SubTask getSubTaskById(Long id){
        history.add(subTasks.get(id));
        return new SubTask(subTasks.get(id));
    }

    @Override
    public Epic getEpicById(Long id){
        history.add(epics.get(id));
        return new Epic(epics.get(id));
    }

    /*
        Создание новых задач
    */

    @Override
    public boolean addNewSimpleTask(SimpleTask newTask){
        SimpleTask task = new SimpleTask(newTask);
        task.setId(uniqueId++);
        simpleTasks.putIfAbsent(task.getId(), task);
        return true;
    }

    @Override
    public boolean addNewSubTask(SubTask newTask){
        SubTask task = new SubTask(newTask);
        task.setId(uniqueId++);
        Epic epic = epics.get(task.getEpicId());
        epic.addSubTask(task);
        epic.statusControl();
        subTasks.putIfAbsent(task.getId(), task);
        return true;
    }

    @Override
    public boolean addNewEpic(Epic newEpic){
        Epic epic = new Epic(newEpic);
        epic.setId(uniqueId++);
        epics.putIfAbsent(epic.getId(), epic);
        return true;
    }

    /*
        Вспомогательный метод для присваивания Id
    */

    private void setId(SimpleTask task){
        if (task.getId() == null) task.setId(uniqueId++);
        if (Objects.equals(task.getId(), uniqueId)) uniqueId++;
    }

    /*
        Обновление задач
    */

    @Override
    public boolean simpleTaskUpdate(SimpleTask task){
        simpleTasks.put(task.getId(), task);
        return true;
    }

    @Override
    public boolean subTaskUpdate(SubTask task){
        Epic epic = epics.get(task.getEpicId());
        epic.updateSubTask(task);
        subTasks.put(task.getId(), task);
        epic.statusControl();
        return true;
    }

    @Override
    public boolean epicUpdate(Epic epic){
        subTasks.values().stream()
                .filter(it -> Objects.equals(it.getEpicId(), epic.getId()))
                .map(SimpleTask::getId)
                .forEach(subTasks::remove);
        epics.get(epic.getId()).deleteEpic();
        epics.put(epic.getId(), epic);
        epics.get(epic.getId()).statusControl();
        return true;
    }

    /*
        Удаление задачи по идентификатору
    */

    @Override
    public void deleteSimpleTaskById(Long id){
        simpleTasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(Long id){
        Epic epic = epics.get(subTasks.get(id).getEpicId());
        epic.deleteSubTaskById(id);
        epic.statusControl();
        subTasks.remove(id);
    }

    @Override
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

    @Override
    public List<SubTask> getAllSubTaskFromEpicByEpicId(Long id){
        return subTasks.values().stream()
                .filter(it -> Objects.equals(it.getEpicId(), id))
                .collect(Collectors.toList());
    }

    /*
        Получение истории просмотров задач
    */

    @Override
    public List<SimpleTask> getHistory(){
        return history.getHistory();
    }

}
