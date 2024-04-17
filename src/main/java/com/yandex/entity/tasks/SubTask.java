package com.yandex.entity.tasks;

public class SubTask extends SimpleTask {

    private Long epicId;

    public SubTask(String name, String description, Long epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Long getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
