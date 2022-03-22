package lerrain.tool.formula;

/**
 * 取消传入factors：
 * 考虑是延后计算，其实本应在最开始就算好，为了提升速度才延后，那么factors就应该使用最初的，而不是生成值时候从外部传入
 * 如果是用后来传入的factors，还会有生成位置不同，factors不同计算结果不同的问题
 */
public interface AutoConstant
{
    Object run();
}
