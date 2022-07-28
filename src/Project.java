import java.util.List;

public class Project {
    private final String name;
    private final List<Task> tasks;

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        // TODO: YOUR CODE HERE
        Digraph g = new Digraph(tasks.size());
        for (int i = 0; i < tasks.size(); i++){
            for (int j = 0; j < tasks.get(i).getDependencies().size(); j++){
                int currentDep = tasks.get(i).getDependencies().get(j);
                g.addEdge(currentDep,i);
            }
        }
        int[] topological = g.topologicalSort();
        int[] schedule = new int[tasks.size()];
        for (int i : topological){
            int max = 0;
            for (int j = 0; j < tasks.get(i).getDependencies().size(); j++){
                int currentDep = tasks.get(i).getDependencies().get(j);
                int waitingTime = schedule[currentDep]+tasks.get(currentDep).getDuration();
                if (max < waitingTime){
                    max = waitingTime;
                }
            }
            schedule[i] = max;
        }
        return schedule;
    }


    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        // TODO: YOUR CODE HERE
        return tasks.get(getEarliestSchedule().length - 1).getDuration() + getEarliestSchedule()[getEarliestSchedule().length - 1];
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
