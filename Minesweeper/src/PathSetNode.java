import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class PathSetNode {
    private TreeMap<String, PathSetNode> children;
    private boolean isLast;

    public PathSetNode() {
        this.children = new TreeMap<String, PathSetNode>();
        this.isLast = false;
    }

    public boolean isEmpty() {
        return (!this.isLast && children.isEmpty());
    }

    public void add(List<String> path) {
        PathSetNode last = this;
        for (int i = 0; i < path.size(); i++) {
            String s = path.get(i);
            if (!last.children.containsKey(s)) {
                last.children.put(s, new PathSetNode());
            }
                last = last.children.get(s);
        }  
        last.isLast = true;
    }

    public boolean contains(List<String> path) {
        PathSetNode last = this;
        for (int i = 0; i < path.size(); i++) {
            String s = path.get(i);
            if (!last.children.containsKey(s)) {
                return false;
            }
            PathSetNode curr = last.children.get(s);
            last = curr;
        }
        return (last.isLast);
    }
  
    public List<List<String>> toListOfPaths() {
        int x = 0;
        LinkedList<List<String>> lls = new LinkedList<List<String>>();
        for (Map.Entry<String, PathSetNode> kv : children.entrySet()) {
            if (kv.getValue().isLast) {
                LinkedList<String> ll = new LinkedList<String>();
                ll.addFirst(kv.getKey());
                lls.add(ll);
            } 
            if (!children.isEmpty() && x == 0) {
                for (List<String> ls : kv.getValue().toListOfPaths()) {
                    ((LinkedList<String>) ls).addFirst(kv.getKey());
                    lls.add(ls);
                }
            }
            x++;
        }
        return lls;
    }
}
