import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QuickCheckProcess {
    protected ClassObject classObject;
    private final List<ClassMethod> propertyMethodList = new ArrayList<>();


    //check the return tyope of the method
    public QuickCheckProcess(ClassObject classObject) {
        this.classObject = classObject;
        for (ClassMethod classMethod : classObject.getSelfMethodList()) {
            Method method = classMethod.getMethod();
            if (AnnotationUtil.isMethodAnnotated(method, Property.class)) {
                if (!method.getReturnType().equals(Boolean.class) && !method.getReturnType().equals(boolean.class)) {
                    System.out.println("The return type of the property annotated method" + classMethod.getMethod()
                            + " should be boolean or Boolean ");
                    continue;
                }
                propertyMethodList.add(classMethod);
            }
        }
        Collections.sort(propertyMethodList);

    }

    public HashMap<String, Object[]> execute(int runCount) {
        HashMap<String, Object[]> methodExecuteResultMap = new HashMap<>();
        try {
            Object instance = classObject.getClazz().getDeclaredConstructor().newInstance();

            for (ClassMethod classMethod : propertyMethodList) {
                // 执行测试方法
                Object[] errorParams = runMethod(runCount, instance, classMethod);
                methodExecuteResultMap.put(classMethod.getName(), errorParams);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return methodExecuteResultMap;
    }


    private Object[] runMethod(int runCount, Object instance, ClassMethod classMethod) {
        List<Class<?>> paramList = classMethod.getParamList();
        List<Annotation> paraAnnotationList = AnnotationUtil.getAllParamsAnnotations(classMethod.getMethod());
        try {
            // 方法中每个参数的所有组合结果
            List<Object> paramCombineList = new ArrayList<>();
            for (int j = 0; j < paramList.size(); j++) {
                Annotation paraAnnotation = paraAnnotationList.get(j);
                paramCombineList.add(QuickCheckAnnotationUtil.generateValue(classObject, classMethod, paraAnnotation, j, instance));
            }

            runMethodCombine(instance, classMethod, paramCombineList, runCount, 0, new ArrayList<>());
        } catch (TerminateException e) {
            return e.paramValues;
        } catch (Exception e) {
           e.printStackTrace();
            return new Object[0];
        }
        return null;
    }

    private int runMethodCombine(Object instance, ClassMethod classMethod, List<Object> paramCombineList, int runCount, int index, List<Object> paramObjects) throws TerminateException {

        if (index >= paramCombineList.size()) {

            Object[] paramValues = null;
            if (paramObjects.size() > 0) {
                paramValues = paramObjects.toArray(new Object[0]);
            }
            try {
                // test run
                boolean runResult = (boolean) classMethod.getMethod().invoke(instance, paramValues);
                System.out.println(paramObjects);
                if (!runResult) {
                    // failed
                    throw new IllegalStateException();
                }
                paramValues = null;
            } catch (Exception e) {
                throw new TerminateException(paramValues);
            }
            runCount--;
            if (runCount <= 0) {
                throw new TerminateException(paramValues);
            }

            return runCount;
        }

        Object paramCombine = paramCombineList.get(index);
        if (paramCombine instanceof List) {
            for (Object param : (List) paramCombine) {
                paramObjects.add(param);
                runCount = runMethodCombine(instance, classMethod, paramCombineList, runCount, index + 1, paramObjects);
                paramObjects.remove(param);
            }
        } else {
            paramObjects.add(paramCombine);
            runCount = runMethodCombine(instance, classMethod, paramCombineList, runCount, index + 1, paramObjects);
            paramObjects.remove(paramCombine);
        }

        return runCount;

    }

    class TerminateException extends Exception {
        Object[] paramValues = null;

        public TerminateException(Object[] paramValues) {
            this.paramValues = paramValues;
        }
    }

}
