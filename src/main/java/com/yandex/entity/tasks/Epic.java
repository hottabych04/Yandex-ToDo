package com.yandex.entity.tasks;

import com.yandex.enums.TasksStatus;

import java.util.HashMap;
import java.util.Set;

public class Epic extends SimpleTask {

    private HashMap<Long, SubTask> tasks = new HashMap<>();


    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(Epic epic){
        super(epic.name, epic.description);
        this.id = epic.id;
        this.tasks = epic.tasks;
    }

    private HashMap<Long, SubTask> getTasks() {
        return tasks;
    }

    public void addSubTask(SubTask newTask){
        tasks.putIfAbsent(newTask.id, newTask);
    }

    public void updateSubTask(SubTask task) {
        tasks.put(task.id, task);
    }

    public void deleteSubTaskById(Long subTaskId){
        tasks.remove(subTaskId);
    }

    public void deleteEpic(){
        deleteAllSubTasks();
    }

    public void deleteAllSubTasks(){
        tasks.clear();
    }

    public void statusControl(){
        if (tasks.isEmpty() || tasks.values().stream().anyMatch(it -> it.status == TasksStatus.NEW)) {
            status = TasksStatus.NEW;
        } else if (tasks.values().stream().anyMatch(it -> it.status == TasksStatus.DONE)) {
            status = TasksStatus.DONE;
        } else {
            status = TasksStatus.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "tasks=" + tasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
