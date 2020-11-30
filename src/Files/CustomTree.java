package Files;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/

public class CustomTree  extends AbstractList<String>  implements Cloneable, Serializable  {
    Entry <String> root;
    private int entryCount;

    CustomTree (){
        this.root = new Entry<>("ROOT");
        entryCount = 0;     // при добавлении Root это всегда элемент N1
        this.root.setLineNumber(1);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
//        return super.addAll(index, c);
    }

    @Override
        public int size() {

        return entryCount;
        }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
//        return super.subList(fromIndex, toIndex);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String ))            // Проверим, а стоку ли мы получили
            throw new UnsupportedOperationException();
        boolean response = false;
        String target = (String) o;
        // начнём обход в поисках нужного элемента
        Queue<Entry> queueEntry = new ArrayDeque<Entry>();
        if (!root.availableToAddLeftChildren)
            queueEntry.add(this.root.leftChild);            // Положим левый элемент в очередь
        if (!root.availableToAddRightChildren)
            queueEntry.add(this.root.rightChild);           // Положим правый элемент в очередь
        boolean bFound = false;
        while ((!bFound) && queueEntry.size() > 0) {
            Entry testEntry = queueEntry.poll();        // Посмотрим, что у нас в очереди лежит
            if (testEntry.elementName.equals(target)) {
                int lineId = testEntry.getLineNumber()-1;
                DeleteTree( testEntry);
                ChekLine(lineId);
                bFound = true;
                response = true;
                break;
            }
            if (!testEntry.availableToAddLeftChildren)
                if (testEntry.leftChild != null)   // А вдруг ранее уже удаляли
                    queueEntry.add(testEntry.leftChild);            // Положим левый элемент в очередь
            if (!testEntry.availableToAddRightChildren)
                if (testEntry.rightChild != null)   // А вдруг ранее уже удаляли
                    queueEntry.add(testEntry.rightChild);           // Положим правый элемент в очередь
        }
        return response;
    }

    private void DeleteTree(Entry testEntry) { // В этом методе я обойду всех детей у элемента и удалю им связи, а так же посчитаю их количество
        entryCount --;
        // Узнаем левый ребёнок или правый
        boolean bDel = false;
        if (testEntry.parent.leftChild != null) {
            if (testEntry.parent.leftChild.elementName.equals(testEntry.elementName)) {
                if (testEntry.parent.leftChild == null)
                    testEntry.parent = null;                // Правого нет, поэтому сейчас и отрежем!
                else
                    testEntry.parent.leftChild = null;  //Левый
                bDel = true;
            }
        }
        if (testEntry.parent.rightChild != null && !bDel) {     // только одного ребёнка за раз :)
            if (testEntry.parent.rightChild.elementName.equals(testEntry.elementName)) {
                testEntry.parent.rightChild = null;  //Правый
                testEntry.parent = null;            // Тут по любому зарываем т.к. больше не булдет.
            }
        }
        if ((!testEntry.availableToAddLeftChildren) && testEntry.leftChild != null)   // Если потомок есть, то его грохаем
            DeleteTree(testEntry.leftChild);
        if ((!testEntry.availableToAddRightChildren) && testEntry.rightChild != null)  //Если потомок есть, то его грохаем
            DeleteTree(testEntry.rightChild);
    }

    private void ChekLine(int lineId) {
// Теперь задача проверить, а есть ли у насна указанной линии ещё бойцы, которые могут иметь потомков
        boolean canNeedClear = true;
        Queue<Entry> queueEntry = new ArrayDeque<Entry>();
        if (!root.availableToAddLeftChildren)
            queueEntry.add(this.root.leftChild);            // Положим левый элемент в очередь
        if (!root.availableToAddRightChildren)
            queueEntry.add(this.root.rightChild);           // Положим правый элемент в очередь
        while (queueEntry.size() > 0) {
            Entry testEntry = queueEntry.poll();        // Посмотрим, что у нас в очереди лежит
            if (testEntry.getLineNumber() == lineId) {
                if ( (testEntry.availableToAddLeftChildren) || (testEntry.leftChild != null)) {
                    canNeedClear = false;               // Если у нас стоит признак, что левого ребёнка можно записать или он физически есть, то
                    break;
                }
                if ( (testEntry.availableToAddRightChildren) || (testEntry.rightChild != null)) {
                    canNeedClear = false;               // Если у нас стоит признак, что правого ребёнка можно записать или он физически есть, то
                    break;
                }
            } else {
                if (!testEntry.availableToAddLeftChildren)
                    queueEntry.add(testEntry.leftChild);            // Положим левый элемент в очередь
                if (!testEntry.availableToAddRightChildren)
                    queueEntry.add(testEntry.rightChild);           // Положим правый элемент в очередь
            }
        }
        // Тут мы обощли все элементы
        if (canNeedClear) {  // Всё-таки нужно чистить!, повторяем операцию :)
            if (!root.availableToAddLeftChildren)
                queueEntry.add(this.root.leftChild);            // Положим левый элемент в очередь
            if (!root.availableToAddRightChildren)
                queueEntry.add(this.root.rightChild);           // Положим правый элемент в очередь

            while ( queueEntry.size() > 0) {
                Entry testEntry = queueEntry.poll();        // Посмотрим, что у нас в очереди лежит
                if (testEntry.getLineNumber() == lineId) {
                    testEntry.availableToAddRightChildren = true;
                    testEntry.availableToAddLeftChildren = true;
                }
            }
        }
    }

    public String getParent(String s) {
        String result = "";
        if (root.elementName.equals(s))
            result = "";
        else {
            Queue<Entry> queueEntry = new ArrayDeque<Entry>();
            if (!root.availableToAddLeftChildren)
                queueEntry.add(this.root.leftChild);            // Положим левый элемент в очередь
            if (!root.availableToAddRightChildren)
                queueEntry.add(this.root.rightChild);           // Положим правый элемент в очередь
            boolean bFound = false;
            while ((!bFound) && queueEntry.size() > 0) {
                Entry testEntry = queueEntry.poll();        // Посмотрим, что у нас в очереди лежит
                if (testEntry.elementName.equals(s)) {
                    result = testEntry.parent.elementName;
                    bFound = true;
                    break;
                }
                if (!testEntry.availableToAddLeftChildren && testEntry.leftChild != null)
                    queueEntry.add(testEntry.leftChild);            // Положим левый элемент в очередь
                if (!testEntry.availableToAddRightChildren && testEntry.rightChild != null)
                    queueEntry.add(testEntry.rightChild);           // Положим правый элемент в очередь

            }
            if (!bFound)
                result = "";
        }
        return result;
    }

    @Override
    public boolean add(String s) {
        if (!root.isAvailableToAddChildren()) {
            Queue<Entry> queueEntry = new ArrayDeque<Entry>();
            queueEntry.add(this.root.leftChild);            // Положим правый и левый элемент в очередь
            queueEntry.add(this.root.rightChild);
            boolean bFound = false;
            while (!bFound) {
                Entry testEntry = queueEntry.poll();        // Посмотрим, что у нас в очереди лежит
                if (!testEntry.isAvailableToAddChildren()) {   //Если в левом элементе нет ничего, то
                    if (testEntry.leftChild !=null)   // Проверка нужна т.к. мы могли что-то удалять!
                        queueEntry.add(testEntry.leftChild);        // положим детей так же в очередь
                    if (testEntry.rightChild !=null)   // Проверка нужна т.к. мы могли что-то удалять!
                        queueEntry.add(testEntry.rightChild);
                } else {                //Видать что-то пустое нашли
                    bFound =true;
                    if (testEntry.availableToAddLeftChildren)
                        AddChilde(testEntry, s, true);
                    else
                        AddChilde(testEntry, s, false);
                }
            }
        } else {
            if (root.availableToAddLeftChildren)
                AddChilde(root, s, true);
            else
                AddChilde(root, s, false);
        }
        entryCount ++;

        return true;
    }

    private void AddChilde (Entry parent, String value, boolean bLeft){
        Entry <String> childe = new Entry<>(value);
        if (bLeft) {
            parent.availableToAddLeftChildren = false;
            parent.leftChild = childe;
        }
        else {
            parent.availableToAddRightChildren = false;
            parent.rightChild = childe;
        }
        childe.setLineNumber(parent.getLineNumber() + 1);
        childe.parent = parent;
    }

    @Override
        public String get(int index) {
            throw new UnsupportedOperationException();
//        return null;
        }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
        //        return super.set(index, element);
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
//        super.add(index, element);
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
//        return super.remove(index);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
//        super.removeRange(fromIndex, toIndex);
    }

     static class Entry<T> implements Serializable {

        String elementName;
        int lineNumber;
        public int getLineNumber() {
            return lineNumber;
        }
         public void setLineNumber(int lineNumber) {
             this.lineNumber = lineNumber;
         }
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

         public Entry (String name) {
            this.elementName = name;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        boolean isAvailableToAddChildren(){
            return this.availableToAddLeftChildren || this.availableToAddRightChildren;
        }
    }
}

