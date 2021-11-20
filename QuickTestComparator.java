import java.lang.reflect.Method;
import java.util.Comparator;

public class QuickTestComparator implements Comparator<Method> {
    @Override
    public int compare(Method o1, Method o2) {
        return o1.getName().compareTo(o2.getName());
    }
}


