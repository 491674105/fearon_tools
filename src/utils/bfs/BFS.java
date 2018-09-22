package utils.bfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @description: 获取图型树所有节点及其步长
 * @author: Fearon
 * @create: 2018-04-10 14:15
 **/
public class BFS<T extends Object> {
    // 节点队列，用于读取节点和辅助计算路径
    private Queue<T> queue = null;

    // 节点缓存
    private T cacheNode = null;

    // 搜索节点步长
    private int step = 0;

    public int useBFS(HashMap<T, LinkedList<T>> graph, T root, HashMap<T, Integer> dist){
        // 以链表方式创建队列创建队列
        queue = new LinkedList();

        // 将当前根节点存入队列并保存路径
        queue.add(root);
        dist.put(root, step);

        // 若队列不为空，则读取根节点的关联表
        while(!queue.isEmpty()){
            cacheNode = queue.poll();
            // 步长开始运动
            step = dist.get(cacheNode) + 1;

            // 查找节点周边尚未访问的子节点
            for(T checkNode : graph.get(cacheNode)){
                if(!dist.containsKey(checkNode)){
                    // 保存根节点到当前节点的步长
                    dist.put(checkNode, step);
                    // 将下一个节点存入队列中
                    queue.add(checkNode);
                }
            }
        }

        return dist.size();
    }
}