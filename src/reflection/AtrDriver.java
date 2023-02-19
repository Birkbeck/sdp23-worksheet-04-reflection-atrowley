package reflection;

public class AtrDriver {

    public static void main(String[] args) {
        if (args.length > 0){
            System.out.println(new DescribeClass(args[0]));
        }
    }
}
