package lerrain.tool.script.warlock;

import java.util.Arrays;

public class IntList
{
    int[] elements = new int[4];

    int size = 0;

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int get(int index)
    {
        rangeCheck(index);

        return elements[index];
    }

    public int set(int index, int element)
    {
        rangeCheck(index);

        int oldValue = elements[index];
        elements[index] = element;
        return oldValue;
    }

    private void ensureCapacityInternal(int minCapacity)
    {
        if (minCapacity - elements.length > 0)
        {
            int oldCapacity = elements.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0)
                newCapacity = minCapacity;

            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    public boolean add(int e)
    {
        ensureCapacityInternal(size + 1);

        elements[size++] = e;
        return true;
    }

    public void add(int index, int element)
    {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elements, index, elements, index + 1,
                size - index);
        elements[index] = element;
        size++;
    }

    public int remove(int index)
    {
        rangeCheck(index);

        int oldValue = elements[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elements, index + 1, elements, index,
                    numMoved);
        --size;

        return oldValue;
    }

    public void clear()
    {
        size = 0;
    }

    private void rangeCheck(int index)
    {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index)
    {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size;
    }
}
