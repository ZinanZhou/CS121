import java.util.*;

public class QuickCheckTest {
    private final Random random = new Random();

    public static void main(String[] args) {
        HashMap<String, Object[]> result = Unit.quickCheckClass("QuickCheckTest");
        for (String key : result.keySet()) {
            System.out.println(key + " ======>> " + Arrays.toString(result.get(key)));
        }

        HashMap<String, Throwable> res = Unit.testClass("QuickCheckTest");
    }



//    @Property
//    public boolean testIntRange(@IntRange(min = -8, max = 10) Integer number) {
//        System.out.println("IntRange...");
//        return Math.abs(number) >= 4;
//    }
//
//    @Property
//    public boolean testStringSet(@StringSet(strings = {"s1", "s2", "s3", "s4", "s5"}) String s) {
//        System.out.println("s");
//
//        return !s.equals("s3");
//    }
////
//    @Property
//    public boolean testListLength(@ListLength(min = 1, max = 3) List<@IntRange(min = 2, max = 4) Integer> list, @StringSet(strings ={"s1","s2"}) String s) {
//        System.out.println(list);
//        System.out.println(s);
//        return list.size() <= 2;
//    }

    @Property
    public boolean testListList(@ListLength(min = 1, max = 3) List<@ListLength(min = 2, max = 4) List<@IntRange(min = 100, max = 102) Integer>> list) {
        System.out.println(list);
        return list.size() < 1;
    }
    @Property
    public boolean testListOBJ(@ListLength(min = 1, max = 3) List<@ForAll(name="genIntSet",times = 3) Object> list) {
        System.out.println(list);
        return list.size() <= 2;
    }

//    @Property
//    public boolean testForAll(@ForAll(name = "genIntSet", times = 10) Object o) {
//       Set s = (Set) o;
//       s.add("foo");
//       return s.contains("foo");
//    }
//
    int count = 0;

    public Object genIntSet() {
        Set s = new HashSet();
        for (int i = 0; i < count; i++) {
            s.add(i);
        }
        count++;
        return s;
    }

}