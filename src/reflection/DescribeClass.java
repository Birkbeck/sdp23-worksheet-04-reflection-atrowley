package reflection;

import java.lang.reflect.Modifier;

public class DescribeClass {
    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Usage: "); // TODO: describe how to use the utility
            System.out.println(args[0]);
        try {
            Class<?> myClass = Class.forName(args[0]);
            System.out.println(myClass.getName());
            System.out.println(Modifier.toString(myClass.getModifiers()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // TODO: implement the functionality for Question 1
    }
}
