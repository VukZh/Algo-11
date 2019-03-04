package optimalsearchtreeapp;

public class BArray<T> {

    Object[] _arr;
    int size = 0;

    BArray() {
        _arr = new Object[size];
    }

    T get(int index) {
        if (index <= size) {
            return (T) _arr[index];
        } else {
            return null;
        }
    }

    private void relocate(int newsize) {
        Object[] tmp = new Object[newsize];

        if (_arr != null) {
            for (int i = 0; i < _arr.length; i++) {
                tmp[i] = _arr[i];
            }
        }
        _arr = tmp;
    }

    void add(T element) {
        size++;
        if (_arr == null || _arr.length <= size) {
            relocate(size + size / 2); ////////////////// * 1.5 size
        }
        _arr[size - 1] = (Object) element;
    }

    void set(int index, T element) {
        _arr[index] = (Object) element;
    }

    int size() {
        return _arr.length;
    }
}
