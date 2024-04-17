package com.yandex.entity.tasks;

import com.yandex.enums.TasksStatus;

import java.util.HashMap;
import java.util.Set;

public class Epic extends SimpleTask {

    private HashMap<Long, SubTask> tasks;


    public Epic(String name, String description) {
        super(name, description);
        tasks = new HashMap<>();
    }

    public void deleteSubTaskById(Long subTaskId){
        tasks.remove(subTaskId);
    }

    private Set<Long> deleteAllSubTasks(){
        Set<Long> subTasksIds = tasks.keySet();
        tasks.clear();
        return subTasksIds;
    }

    public Set<Long> deleteEpic(){
        return deleteAllSubTasks();
    }

    public void addSubTask(SubTask newTask){
        tasks.putIfAbsent(newTask.id, newTask);
        if(status == TasksStatus.DONE) { status = TasksStatus.IN_PROGRESS; }
    }

    public void updateSubTask(SubTask task) {
        tasks.put(task.id, task);
        if (task.status == TasksStatus.DONE) { statusControl(); }
    }

    private void statusControl(){
        if (tasks.values().stream()
                .allMatch(it -> it.status == TasksStatus.DONE)
        ) {
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
