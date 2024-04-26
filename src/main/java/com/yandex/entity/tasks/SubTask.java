package com.yandex.entity.tasks;

public class SubTask extends SimpleTask {

    private Epic epic;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public SubTask(SubTask subTask) {
        super(subTask.getName(), subTask.getDescription());
        this.epic = subTask.getEpic();
    }

    public Long getEpicId() {
        return epic.getId();
    }

    private Epic getEpic(){
        return epic;
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
