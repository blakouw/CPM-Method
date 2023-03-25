import java.util.HashSet;
import java.util.Set;

public class Node {
    private final String name;
    private final int duration;
    private final Set<Node> dependencies;
    private final Set<Node> dependents;

    public Node(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.dependencies = new HashSet<>();
        this.dependents = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public Set<Node> getDependencies() {
        return dependencies;
    }

    public void addDependency(Node dependency) {
        dependencies.add(dependency);
        dependency.addDependent(this);
    }

    public void removeDependency(Node dependency) {
        dependencies.remove(dependency);
        dependency.removeDependent(this);
    }

    public Set<Node> getDependents() {
        return dependents;
    }

    private void addDependent(Node dependent) {
        dependents.add(dependent);
    }

    private void removeDependent(Node dependent) {
        dependents.remove(dependent);
    }
}
