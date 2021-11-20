import java.util.ArrayList;
import java.util.List;

public class ClassObject {
    private Class<?> clazz;
    private List<ClassField> fieldList;
    private List<ClassMethod> selfMethodList;
    private List<ClassMethod> inheritMethodList;

    public ClassMethod getEmptyParamMethod(String methodName) {
        List<ClassMethod> list = new ArrayList<>(selfMethodList);
        list.addAll(inheritMethodList);
        for (ClassMethod classMethod : list) {
            if (classMethod.emptyParams() && classMethod.getName().equals(methodName)) {
                return classMethod;
            }
        }
        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<ClassMethod> getSelfMethodList() {
        return selfMethodList;
    }

    public void setSelfMethodList(List<ClassMethod> selfMethodList) {
        this.selfMethodList = selfMethodList;
    }

    public List<ClassField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ClassField> fieldList) {
        this.fieldList = fieldList;
    }

    public List<ClassMethod> getInheritMethodList() {
        return inheritMethodList;
    }

    public void setInheritMethodList(List<ClassMethod> inheritMethodList) {
        this.inheritMethodList = inheritMethodList;
    }
}
