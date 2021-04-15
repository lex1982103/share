package lerrain.tool.script.warlock;

public interface Optimized
{
    //无参time等函数虽然是会变化的，在考虑速度的前提下，是可以简化成固定值的
    public static final int TIME = 0x0001; //简化无参time。比如select中
    public static final int RANDOM = 0x0002; //简化随机数。比如where条件中使用
    public static final int VARIABLE = 0x0004; //简化无参variable。

    boolean isFixed(int mode);
}
