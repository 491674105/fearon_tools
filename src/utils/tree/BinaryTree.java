package utils.tree;

import java.util.*;

/**
 * @description: 二叉树操作相关
 * @author: Fearon
 * @create: 2018/9/22 15:26
 **/
public class BinaryTree {
    /**
     * 以迭代的方式生成二叉树（根节点必须事先单独带入tree中）
     *
     * @param parent 当前父级节点
     * @param level  需要生成二叉树的层级
     * @param queue  生成树的原始数据
     */
    private LinkedList<LinkedList<Map<String, Object>>> createBTreeByIterator(Map<String, Object> parent, int level, Queue queue) {
        // 逻辑根节点必须存在
        if (null == parent || parent.isEmpty())
            return null;

        LinkedList<LinkedList<Map<String, Object>>> tree = new LinkedList<>();
        LinkedList<Map<String, Object>> tree_level = new LinkedList<>();
        Map<String, Object> node;
        tree_level.add(parent);
        tree.add(tree_level);

        long nodeCount;
        int i = 0;
        for (; i < level - 1; i++) {
            int j = 0;
            tree_level = new LinkedList<>();
            nodeCount = 2 << i; // 当前层完美的节点数
            for (; queue.poll() != null; j++) {
                // 此处为获取节点的方式，需重写，视情况而定
                node = new HashMap<>();
                if (j % 2 == 0) {
                    node.put("location", 0);
                } else {
                    node.put("location", 1);
                }
                tree_level.add(node);

                // 判断当前层是否已满
                if (nodeCount == j)
                    break;
            }
            tree.add(tree_level);
        }

        return tree;
    }
}
