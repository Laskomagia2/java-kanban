package tasks;
import taskManager.Manager;

public class Test {
    public static void main(String[] args) {
        System.out.println("Создаю задачи");
        Task task1 = new Task("Задачи", "Решить задачи");
        Epic epic = new Epic("Убраться", "Сделать уборку");
        System.out.println("Выполнено! \n");

        System.out.println("Создаю через менеджер");
        Manager manager = new Manager();
        manager.createTask(task1);
        System.out.println("Task1 создан");
        System.out.println(manager.getListOfTasks());
        System.out.println();
        manager.createEpic(epic);
        System.out.println("Создан Эпик");
        Subtask sub1epic1 = new Subtask("Убраться на кухне", "Помыть посуду", epic.getTaskId());
        Subtask sub2epic1 = new Subtask("Убраться в спальне", "Убрать вещи", epic.getTaskId());
        manager.createSubtask(sub1epic1);
        manager.createSubtask(sub2epic1);
        System.out.println(manager.getEpicSubtasks(epic));
        System.out.println("Создан эпик и два сабтаска");
        System.out.println(manager.getListOfTasks());
        System.out.println();
        System.out.println("Начинаю изменение статусов Тасков");
        manager.setTaskStatus(task1, "DONE");
        manager.setSubtaskStatus(sub1epic1, "DONE");
        manager.setSubtaskStatus(sub2epic1, "DONE");
        System.out.println("Статусы изменены");
        System.out.println(manager.getListOfTasks());
        System.out.println();
        System.out.println("Тест обновления Тасков");
        Task newTask = new Task("Подзадачи", "Решить подзадачи");
        newTask.setTaskId(task1.getTaskId());
        manager.updateTask(newTask);
        System.out.println("Обновлен Таск1");
        System.out.println(manager.getListOfTasks());
        Subtask newSub1Ep1 = new Subtask("Убраться в шкафу на кухне", "Расставить тарелки", epic.getTaskId());
        newSub1Ep1.setTaskId(sub1epic1.getTaskId());
        manager.updateSubtask(newSub1Ep1);
        Subtask newSub2Ep1 = new Subtask("Убраться под кроватью в спальной", "Убрать хлам", epic.getTaskId());
        newSub2Ep1.setTaskId(sub2epic1.getTaskId());
        manager.updateSubtask(newSub2Ep1);
        System.out.println("Изменены сабтаски");
        System.out.println(manager.getEpicSubtasks(epic));
        System.out.println(manager.getListOfTasks());

        System.out.println("Получаем таски по id");
        System.out.println("Таск1:");
        System.out.println(manager.getTaskById(task1.taskId));
        System.out.println("Сабтаски эпика1:");
        System.out.println(manager.getTaskById(sub1epic1.taskId));
        System.out.println(manager.getTaskById(sub2epic1.taskId));
        System.out.println("Эпик1:");
        System.out.println(manager.getTaskById(epic.getTaskId()));

        System.out.println("Начинаем удаление");
        manager.removeTasksById(task1.getTaskId());
        System.out.println("Удален Таск1 по id");
        System.out.println(manager.getListOfTasks());
        manager.removeSubtasks();
        System.out.println("Удалены сабы");
        System.out.println(manager.getListOfTasks());
        manager.removeEpicsById(epic.getTaskId());
        System.out.println("Удален Эпик");
        System.out.println(manager.getListOfTasks());
    }
}
