package com.yandex;

import com.yandex.entity.tasks.Epic;
import com.yandex.entity.tasks.SimpleTask;
import com.yandex.entity.tasks.SubTask;
import com.yandex.service.managers.TaskManager;
import com.yandex.service.util.Managers;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.addNewSimpleTask(new SimpleTask("firstSimpleTask", "111111111111111"));
        manager.addNewSimpleTask(new SimpleTask("secondSimpleTask", "222222222222222222"));
        manager.addNewEpic(new Epic("firstEpic", "33333333333333333"));
        manager.addNewSubTask(new SubTask("firstSubTask", "44444444444444", manager.getEpicById(3L)));
        manager.addNewSubTask(new SubTask("secondSubTask", "55555555555555", manager.getEpicById(3L)));
        manager.addNewEpic(new Epic("secondEpic", "6666666666666666"));
        manager.addNewSubTask(new SubTask("thirdSubTask", "7777777777777", manager.getEpicById(6L)));

        manager.getEpicById(3L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getSimpleTaskById(2L);
        manager.getEpicById(3L);
        manager.getEpicById(3L);

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (SimpleTask task : manager.getAllSimpleTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (SubTask task : manager.getAllSubTaskFromEpicByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (SubTask subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("стория:");
        for (SimpleTask task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
