import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {

        //默认没有参数
        HashMap<String, Throwable> res = new HashMap<String, Throwable>();
        Object instance = new Object();
        try {
            Class clazz = Class.forName(name);
            try {
//                Constructor constructor =clazz.getConstructor();
                try {
                    instance = clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Method[] methods = clazz.getDeclaredMethods();

            ArrayList<Method> testMethods = new ArrayList<>();
            ArrayList<Method> beforeMethods = new ArrayList<>();
            ArrayList<Method> beforeClassMethods = new ArrayList<>();
            ArrayList<Method> afterMethods = new ArrayList<>();
            ArrayList<Method> afterClassMethods = new ArrayList<>();

            //Get all methods
            for(Method m :methods){
                Annotation[] annotations = m.getAnnotations();
                if (annotations.length >1){
                    throw new UnsupportedOperationException();
                }
                if (m.isAnnotationPresent(Test.class)) {
                    testMethods.add(m);
                }
                if(m.isAnnotationPresent(Before.class)){
                    beforeMethods.add(m);
                }
                if(m.isAnnotationPresent(BeforeClass.class)){
                    if(Modifier.isStatic(m.getModifiers())){
                        beforeClassMethods.add(m);
                    }
                    else {
                        throw new UnsupportedOperationException();
                    }
                }
                if(m.isAnnotationPresent(After.class)){
                    afterMethods.add(m);
                }
                if(m.isAnnotationPresent(AfterClass.class)){
                    if(Modifier.isStatic(m.getModifiers())){
                        afterClassMethods.add(m);
                    }
                    else{
                        throw new UnsupportedOperationException();

                    }
                }
            }

            //排序
            Comparator cmp =new QuickTestComparator();

            //Before

            if(!beforeMethods.isEmpty() && !testMethods.isEmpty()){
                beforeMethods.sort(cmp);
                for(Method m : beforeMethods){
                    m.invoke(instance);
                }
            }

            if(!testMethods.isEmpty()){
                testMethods.sort(cmp);
                for(Method m : testMethods){
                    //check beforeClass
                    if(!beforeClassMethods.isEmpty()){
                        beforeClassMethods.sort(cmp);
                        for(Method n :beforeClassMethods){
                            n.invoke(clazz);
                        }
                    }
                    try {
                        m.invoke(instance);
                        res.put(m.getName(),null);
                    } catch (InvocationTargetException e) {
                        Throwable t = e.getTargetException();
                        res.put(m.getName(),t);
                    }catch (Exception e) {
                        Throwable t = e.getCause();
                        res.put(m.getName(),t);
                    }
                    if(!afterClassMethods.isEmpty()){
                        afterClassMethods.sort(cmp);
                        for(Method o :afterClassMethods){
                            o.invoke(clazz);
                        }
                    }
                }
            }


            if(testMethods.isEmpty()){
                if(!beforeClassMethods.isEmpty()){
                    beforeClassMethods.sort(cmp);
                    for(Method n :beforeClassMethods){
                        n.invoke(clazz);
                    }
                }

                if(!afterClassMethods.isEmpty()){
                    afterClassMethods.sort(cmp);
                    for(Method o :afterClassMethods){
                        o.invoke(clazz);
                    }
                }

            }

            if(!afterMethods.isEmpty()&&!testMethods.isEmpty()){
                afterMethods.sort(cmp);
                for(Method m : afterMethods){
                    m.invoke(instance);
                }
            }



        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
        }
        return res;
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        try {
            ClassObject classObject = ClassUtil.classInfo(name);
            QuickCheckProcess quickCheckProcess = new QuickCheckProcess(classObject);
            return quickCheckProcess.execute(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}