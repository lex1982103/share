package lerrain.tool;

public class Test
{
    public static void main(String[] s)
    {
        System.out.println("2017/6/9".replaceAll("[\\\\/]", "-"));
        System.out.println(Common.dateOf("2017-6-9"));
        System.out.println(Common.dateOf("2017/6/9"));
    }
}
