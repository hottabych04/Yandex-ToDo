package com.yandex.entity.tasks;

public class SubTask extends SimpleTask {

    private final Epic epic;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public SubTask(SubTask subTask) {
        super(subTask.name, subTask.description);
        this.epic = subTask.epic;
    }

    public Long getEpicId() {
        return epic.getId();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epic.getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
