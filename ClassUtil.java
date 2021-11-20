import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtil {

    public static ClassObject classInfo(String classPath) throws ClassNotFoundException {
        return classInfo(Class.forName(classPath));
    }

    public static ClassObject classInfo(Class<?> clazz) {
        ClassObject classObject = new ClassObject();
        classObject.setClazz(clazz);
        classObject.setFieldList(getClassFields(clazz.getDeclaredFields()));
        classObject.setSelfMethodList(getClassMethod(clazz.getDeclaredMethods()));
        classObject.setInheritMethodList(getClassMethod(clazz.getSuperclass().getDeclaredMethods()));
        return classObject;
    }

    private static List<ClassField> getClassFields(Field[] declaredFields) {
        ArrayList<ClassField> fieldList = new ArrayList<>();
        for (Field field : declaredFields) {
            ClassField classField = new ClassField();
            classField.setField(field);
            classField.setModifier(modifier(field.getModifiers()));
            classField.setType(field.getType());
            classField.setName(field.getName());
            fieldList.add(classField);
        }
        return fieldList;
    }

    private static List<ClassMethod> getClassMethod(Method[] declaredMethods) {
        List<ClassMethod> methodList = new ArrayList<>();
        for (Method method : declaredMethods) {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethod(method);
            classMethod.setModifier(ClassUtil.modifier(method.getModifiers()));
            classMethod.setReturnType(method.getReturnType());
            classMethod.setName(method.getName());
            classMethod.setParamList(Arrays.asList(method.getParameterTypes()));
            methodList.add(classMethod);
        }
        return methodList;
    }

    private static String modifier(int modifier) {
        String auth = "private";
        if (Modifier.isPublic(modifier)) {
            auth = "public";
        }
        if (Modifier.isProtected(modifier)) {
            auth = "protect";
        }

        String modify = Modifier.isStatic(modifier) ? " static " : "";
        modify += Modifier.isFinal(modifier) ? " final " : "";

        return (auth + modify).trim();
    }


}
