package lerrain.tool.script;

public interface PlaybackListener
{
    public boolean isDebugging();

    public Object popRecordHistory(String name);
}
