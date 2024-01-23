public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task = new Task("Учеба", "Выполнить спринт");
        //manager.taskInProgress(task);
        manager.createTask(task);
        System.out.println(task.toString());

        Epic epic = new Epic("Работа", "Выполнить работу");
        manager.createEpic(epic);
        Task epicTask = new Task("Погрузить", "Погрузить контейнер");
        manager.putTaskInEpic(epic,epicTask);
        System.out.println(epic.toString());

        System.out.println(manager.getListOfTasks());
    }
}
