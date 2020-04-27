package lerrain.tool.script;

public interface PlaybackListener
{
    public boolean isDebugging();
    public Object popRecordHistory(String name);

    public Object prepare(String name);
    public void success(Object prepare, Object res);
    public void fail(Object prepare, Exception exc);
}
