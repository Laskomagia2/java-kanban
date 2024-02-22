public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Помыть", "Помыть машину");
        manager.createTask(task1);

        System.out.println(task1);
        System.out.println(manager.getListOfTasks());
        System.out.println(manager.getNewTasks());
        manager.setTaskIsDone(task1);
        System.out.println(manager.getDoneTasks());

        Epic epic1 = new Epic("Сделать домашку", "Необходимо сделать домашку по математике и русскому");
        manager.createEpic(epic1);

        Subtask sub1Epic1 = new Subtask("Домашка по математике", "Сделай домашку по математике", epic1);
        Subtask sub2Epic1 = new Subtask("Дз по русскому", "Сделай дз по русскому", epic1);

        System.out.println("Первый эпик: " + epic1);
        System.out.println("Лист таксков" + manager.getListOfTasks());
        System.out.println("Лист новых тасков" + manager.getNewTasks());

        manager.setSubtaskIsDone(sub1Epic1);
        manager.setSubtaskIsDone(sub2Epic1);

        System.out.println("Лист готовых тасков" + manager.getDoneTasks());
    }
}
