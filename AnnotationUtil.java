
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class AnnotationUtil {

    public static <T extends Annotation> boolean isMethodAnnotated(Method method, Class<T> annotation) {
        return getTargetAnnotation(method, annotation) != null;
    }

    public static <T extends Annotation> T getTargetAnnotation(Method method, Class<T> annotation) {
        Annotation[] annotations = method.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotationTmp = annotations[i];
            if (annotationTmp.annotationType() == annotation) {
                return (T) annotationTmp;
            }
        }
        return null;
    }

    public static List<Annotation> getAllParamsAnnotations(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<Annotation> resultList = new ArrayList<>();
        for (Annotation[] annotations : parameterAnnotations) {
            if (annotations == null || annotations.length == 0) {
                resultList.add(null);
            } else {
                resultList.add(annotations[0]);
            }
        }
        return resultList;
    }

    public static List<Annotation> getListInnerAnnotations(ClassMethod classMethod, Annotation paramAnnotation, int index) {
        AnnotatedType annotatedType = classMethod.getMethod().getAnnotatedParameterTypes()[index];
        List<Annotation> list = new ArrayList<>();
        while (true) {
            Annotation annotation = annotatedType.getAnnotations()[0];
            if (list.contains(annotation) ) {
                break;
            }
            list.add(annotation);
            if (!(annotatedType instanceof AnnotatedParameterizedType)) {
                break;
            }
            annotatedType = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments()[0];
        }

//        ClassObject classObject = ClassUtil.classInfo(annotatedType.getClass());
//        ClassMethod getTypeAnnotationsMethod = classObject.getEmptyParamMethod("getTypeAnnotations");
//        getTypeAnnotationsMethod.getMethod().setAccessible(true);
//        Object[] getTypeAnnotationsMethodResults = ((Object[]) getTypeAnnotationsMethod.getMethod().invoke(annotatedType, (Object[]) null));
//
//        for (Object result : getTypeAnnotationsMethodResults) {
//            ClassObject classObjectTmp = ClassUtil.classInfo(result.getClass());
//            for (ClassField classField : classObjectTmp.getFieldList()) {
//                if (classField.getName().endsWith("annotation")) {
//                    classField.getField().setAccessible(true);
//                    Annotation annotation = (Annotation) classField.getField().get(result);
//                    list.add(annotation);
//                }
//            }
//        }
//        list.remove(paramAnnotation);
//        list.add(0, paramAnnotation);

        return list;
    }
}