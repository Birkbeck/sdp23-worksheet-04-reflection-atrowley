package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DescribeClass {


    public static String getModifiersAsString(Class<?> klass){
        int mods = klass.getModifiers();
        return Modifier.toString(mods);
    }

    public static String getClassOrInterface(Class<?> klass){
        return (klass.isInterface()) ? "interface" : "class";

    }

    public static String getObjectName(Class<?> klass){
        return klass.getSimpleName();
    }

    public static String getSuperClass(Class<?> klass){
        if (klass.getSuperclass() != null) {
            return "extends " + klass.getSuperclass().getSimpleName() + " ";
        }
        return null;
    }

    public static String getObjInterfaces(Class<?> klass){
        if(klass.getInterfaces().length > 0) {
            return "implements " +
                    Stream.of(klass.getInterfaces())
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "));
        }
        return null;
    }

    public static String getObjectFields(Class<?> klass){
        if(klass.getDeclaredFields().length == 0) return "No field";

        return Stream.of(klass.getDeclaredFields())
                .map(f -> Modifier.toString(f.getModifiers()) + " "
                        + f.getType().getSimpleName() + " "
                        + f.getName())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining("\n"));
    }

    public static String getObjectConstructors(Class<?> klass){
        if(klass.getConstructors().length == 0) return "No constructors";

        return Stream.of(klass.getConstructors())
                .map(Constructor::toString)
                .collect(Collectors.joining(";\n"))
                .replaceAll("java.lang.","");
    }

    public static String getObjectMethods(Class<?> klass){
        if(klass.getDeclaredMethods().length == 0) return "No methods";

        return Stream.of(klass.getDeclaredMethods())
                .map(Method::toString)
//                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining(";\n"))
                .replaceAll("java.lang.","");
    }


    public static String getFullClassDescription(Class<?> klass){
        return Stream.of(klass)
                .map(c -> getModifiersAsString(c) + " "
                        + getClassOrInterface(c) + " "
                        + getObjectName(c) + " "
                        + getSuperClass(c)
                        + getObjInterfaces(c)
                        + "\n\n"
                        + "// Field information:\n"
                        + getObjectFields(c)
                        + "\n\n"
                        + "// Constructor information:\n"
                        + getObjectConstructors(c)
                        + "\n\n"
                        + "// Method information:\n"
                        + getObjectMethods(c)
                )

                .collect(Collectors.joining(""));
    }

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Usage: ");
        // TODO: describe how to use the utility

        String nameOfObject = "java.lang.String";

        try {
            Class<?> klass = Class.forName(nameOfObject);
            System.out.println(getFullClassDescription(klass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
