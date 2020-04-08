package utils.sort;

/**
 * @description: 快速排序及优化方法
 * @author: Fearon
 * @create: 2018-04-10 14:56
 **/
public class QuicklySort {
    public void quicklySortChoose(int [] source){
        int length = source.length;
        if(length <= Math.pow(10.0, 6))
            sort(source, 0, length);
        else
            sortUpgraded(source, 0, length);
    }

    /**
     * 常规快速排序
     * @param source
     * @param start
     * @param end
     */
    private void sort(int [] source, int start, int end){
        if(start >= end)
            return;

        int copyStart = start;
        int copyEnd = end;
        int baseData = source[start];

        while(copyStart < copyEnd){
            while(copyStart < copyEnd && source[copyEnd] >= baseData)
                copyEnd--;
            if(copyStart < copyEnd)
                source[copyStart++] = source[copyEnd];

            while(copyStart < copyEnd && source[copyStart] < baseData)
                copyStart++;
            if(copyStart < copyEnd)
                source[copyEnd--] = source[copyStart];
        }

        source[copyStart] = baseData;
        sort(source, start, copyStart - 1);
        sort(source, copyStart + 1, end);
    }

    /**
     * 三向切分排序
     * @param source
     * @param start
     * @param end
     */
    private void sortUpgraded(int [] source, int start, int end){
        if(start >= end)
            return;

        int left = start,
            mid = start + 1,
            right = end,
            baseData = source[start];

        int compareRes;

        while(mid < right){
            compareRes = compareWithMidlle(source[mid], baseData);
            if(compareRes > 0)
                swap(source, mid, right--);
            else if(compareRes < 0)
                swap(source, left++, mid++);
            else
                mid++;
        }
        sortUpgraded(source, start, left - 1);
        sortUpgraded(source, right + 1, end);
    }

    private int compareWithMidlle(int x, int y){
        if(x > y)
            return 1;
        else if(x < y)
            return -1;
        else
            return 0;
    }

    private void swap(int [] source, int x, int y){
        int cache = source[x];
        source[x] = source[y];
        source[y] = cache;
    }
}
