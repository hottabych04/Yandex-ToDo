package com.yandex.entity.tasks;

import com.yandex.enums.TasksStatus;

import java.util.Objects;

public class SimpleTask {

    protected String name;
    protected String description;
    protected Long id;
    protected TasksStatus status;

    public SimpleTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TasksStatus.NEW;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public TasksStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(TasksStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTask that = (SimpleTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
