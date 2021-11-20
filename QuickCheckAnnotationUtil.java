import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class QuickCheckAnnotationUtil {
    private static final Random RANDOM = new Random();

    //
    public static Object generateValue(ClassObject classObject, ClassMethod classMethod, Annotation paramAnnotation, int index, Object instance)
            throws InvocationTargetException, IllegalAccessException {
        List<Object> list = new ArrayList<>();
        if (paramAnnotation instanceof IntRange) {
            list = intRange((IntRange) paramAnnotation);
        } else if (paramAnnotation instanceof StringSet) {
            list = stringSet((StringSet) paramAnnotation);
        } else if (paramAnnotation instanceof ListLength) {
            List<Annotation> listInnerAnnotations = AnnotationUtil.getListInnerAnnotations(classMethod, paramAnnotation, index);
            list = listLength(listInnerAnnotations, classObject, instance);
        } else if (paramAnnotation instanceof ForAll) {
            list = forAll((ForAll) paramAnnotation, classObject, instance);
        }

        return list;
    }

    private static List<Object> intRange(IntRange annotation) {
        List<Object> list = new ArrayList<>();
        for (int i = annotation.min(); i <= annotation.max(); i++) {
            list.add(i);
        }
        return list;
    }

    private static List<Object> stringSet(StringSet annotation) {
        return new ArrayList<>(Arrays.asList(annotation.strings()));
    }


    private static List listLength(List<Annotation> listInnerAnnotationList, ClassObject classObject, Object instance) throws InvocationTargetException, IllegalAccessException {
        Annotation annotation = listInnerAnnotationList.remove(listInnerAnnotationList.size() - 1);
        ListLength listLength = (ListLength) listInnerAnnotationList.remove(listInnerAnnotationList.size() - 1);
        List combines = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        if (annotation instanceof IntRange) {
            list = intRange((IntRange) annotation);
        } else if (annotation instanceof StringSet) {
            list = stringSet((StringSet) annotation);
        } else if (annotation instanceof ForAll) {
            list = forAll((ForAll) annotation, classObject, instance);
        }

        for (int length = listLength.min(); length <= listLength.max(); length++) {
            combines.addAll(combine(length, list));
        }

        return listLengthInner(listInnerAnnotationList, combines);

    }

    //annotation in the List
    private static List listLengthInner(List<Annotation> listInnerAnnotationList, List combines) {
        if (listInnerAnnotationList.size() == 0) {
            return combines;
        }

        List combinesNew = new ArrayList<>();
        Annotation annotation = listInnerAnnotationList.remove(listInnerAnnotationList.size() - 1);
        if (annotation instanceof ListLength) {
            ListLength listLength = (ListLength) annotation;
            for (int length = listLength.min(); length <= listLength.max(); length++) {
                combinesNew.addAll(combine(length, combines));
            }
        }

        return listLengthInner(new ArrayList<>(listInnerAnnotationList), combinesNew);
    }

    static List<List<Object>> combine(int length, List objectList) {
        List<List<Object>> preCombines = new ArrayList<>();
        preCombines.add(new ArrayList<>());
        preCombines = combineInner(length, objectList, preCombines, 0);
        return preCombines;
    }

    static List<List<Object>> combineInner(int length, List objectList, List<List<Object>> preCombines, int index) {
        if (index >= length) {
            return preCombines;
        }

        List<List<Object>> newPreCombines = new ArrayList<>();
        for (List<Object> preCombine : preCombines) {
            for (Object object : objectList) {
                List<Object> listTmp = new ArrayList<>(preCombine);
                listTmp.add(object);
                newPreCombines.add(listTmp);
            }
        }
        return combineInner(length, objectList, newPreCombines, ++index);
    }

    public static List<Object> forAll(ForAll forAll, ClassObject classObject, Object instance)
            throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
        ClassMethod classMethod = classObject.getEmptyParamMethod(forAll.name());
        if (classMethod == null) {
            throw new IllegalArgumentException("No empty param method named " + forAll.name());
        }

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < forAll.times(); i++) {
            list.add(classMethod.getMethod().invoke(instance, (Object[]) null));
        }
        return list;
    }

    private static int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

}