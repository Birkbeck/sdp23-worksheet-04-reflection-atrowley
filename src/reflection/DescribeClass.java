package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DescribeClass {

    Class<?> cls;


    public DescribeClass(String clsName) {
        try {
            cls = Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found for name provided.");
        }
    }


    private String joinArrayRemoveNulls(String[] strArray, String delimmiter){
        return Arrays.stream(strArray)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(delimmiter));
    }


    private String getClassDeclarationInfo(){
        String[] items = new String[]{
                Modifier.toString(cls.getModifiers()),
                getType(),
                cls.getSimpleName(),
                getExtend(),
                getInterfaces()
        };
        return joinArrayRemoveNulls(items," ");
    }


    private String getType(){
        return cls.isInterface()? null : "class";
    }


    private String getExtend(){

        if(cls.getSuperclass()!=null){
            return "extends " + cls.getSuperclass().getSimpleName();
        }
        return null;
    }


    private String getInterfaces(){
        if(cls.getInterfaces().length > 0){
            return "implements " + getInterfaceNames(cls.getInterfaces());
        }
        return null;
    }


    private String getInterfaceNames(Class<?>[] interfaces){
        return Arrays.stream(interfaces)
                .map(Class::getSimpleName)
                .collect(Collectors.joining(" "));
    }


    private String getFieldSummary(){
        if(cls.getDeclaredFields().length >= 1){
            return "// Field information\n" +
                    getDetailsEachField(cls.getDeclaredFields());
        }
        return "// Field information\n[N/A]";
    }

    private String getDetailsEachField(Field[] fields) {
        List<Field> revFld = Arrays.asList(fields);
        Collections.reverse(revFld);
        return revFld.stream()
                .map(f -> " " + Modifier.toString(f.getModifiers()) + " "
                        + f.getGenericType() + " "
                        + f.getName())
                .collect(Collectors.joining("\n"));
    }



    @Override
    public String toString() {

        return joinArrayRemoveNulls(new String[]{
                "/**",
                "* Interface created for "+cls.getCanonicalName(),
                "*/",
                getClassDeclarationInfo(),
                getFieldSummary()

        },"\n");

    }


}
