package com.yandex;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;
import com.yandex.enums.TasksStatus;
import com.yandex.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        SimpleTask firstSimpleTask = manager.addNewSimpleTask(new SimpleTask("firstSimpleTask", "111111111111111"));
        SimpleTask secondSimpleTask = manager.addNewSimpleTask(new SimpleTask("secondSimpleTask", "222222222222222222"));
        Epic firstEpic = manager.addNewEpic(new Epic("firstEpic", "33333333333333333"));
        SubTask firstSubTask = manager.addNewSubTask(new SubTask("firstSubTask", "44444444444444", firstEpic.getId()));
        SubTask secondSubTask = manager.addNewSubTask(new SubTask("secondSubTask", "55555555555555", firstEpic.getId()));
        Epic secondEpic = manager.addNewEpic(new Epic("secondEpic", "6666666666666666"));
        SubTask thirdSubTask = manager.addNewSubTask(new SubTask("thirdSubTask", "7777777777777", secondEpic.getId()));

        System.out.println(firstSimpleTask);
        System.out.println(secondSimpleTask);
        System.out.println(firstEpic);
        System.out.println(secondEpic);
        System.out.println("---------------------------------------------------------------------------");

        firstSimpleTask.setStatus(TasksStatus.IN_PROGRESS);
        secondSimpleTask.setStatus(TasksStatus.DONE);
        manager.simpleTaskUpdate(firstSimpleTask);
        manager.simpleTaskUpdate(secondSimpleTask);
        System.out.println(firstSimpleTask);
        System.out.println(secondSimpleTask);

        firstSubTask.setStatus(TasksStatus.IN_PROGRESS);
        secondSubTask.setStatus(TasksStatus.DONE);
        manager.subTaskUpdate(firstSubTask);
        manager.subTaskUpdate(secondSubTask);
        System.out.println(firstEpic);

        thirdSubTask.setStatus(TasksStatus.DONE);
        manager.subTaskUpdate(thirdSubTask);
        System.out.println(secondEpic);

        thirdSubTask.setStatus(TasksStatus.DONE);
        manager.deleteSubTaskById(thirdSubTask.getId());
        System.out.println(secondEpic);

        manager.deleteAllEpics();
//        manager.subTaskUpdate(thirdSubTask);

    }
}
