package com.labs.algorithm.dijkstra.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DijkstraClass {
    static Map<String, Integer> path = new HashMap<String, Integer>();//封装路径距离
    static Map<String, String> pathInfo = new HashMap<String, String>();//封装路径信息

    static Set<NumNode> open = new HashSet<NumNode>(); //未遍历

    static Set<NumNode> close = new HashSet<NumNode>(); //已遍历

    public static void main(String[] args){
        //test1();
        test2();
    }

    public static void test2(){
        NumNode numNodeF = new NumNode("F",null);
        NumNode numNodeE = new NumNode("E",new HashMap(){{
            put(numNodeF,2);
        }});
        NumNode numNodeD = new NumNode("D",new HashMap(){{
            put(numNodeE,1);
            put(numNodeF,6);
        }});
        NumNode numNodeC = new NumNode("C",new HashMap(){{
            put(numNodeD,3);
            put(numNodeE,5);
        }});
        NumNode numNodeB = new NumNode("B",new HashMap(){{
            put(numNodeC,2);
            put(numNodeD,5);
        }});

        NumNode numNodeA = new NumNode("A",new HashMap(){{
            put(numNodeB,1);
        }});
        init2();
        close.add(numNodeA);
        open.add(numNodeB);
        open.add(numNodeC);
        open.add(numNodeD);
        open.add(numNodeE);
        open.add(numNodeF);

        updatePath(numNodeA);
        printPathInfo();
        printPath();
    }



    public static void test1(){
         /*
            1 A
            2 B
            3 C
            4 D
            5 E
            6 F
         */
        NumNode numNodeF = new NumNode("F",null);
        NumNode numNodeE = new NumNode("E",new HashMap(){{
            put(numNodeF,4);
        }});

        NumNode numNodeC = new NumNode("C",new HashMap(){{
            put(numNodeE,5);
        }});

        NumNode numNodeD = new NumNode("D",new HashMap(){{
            put(numNodeE,13);
            put(numNodeF,15);
            put(numNodeC,4);
        }});
        NumNode numNodeB = new NumNode("B",new HashMap(){{
            put(numNodeD,3);
            put(numNodeC,9);
        }});

        NumNode numNodeA = new NumNode("A",new HashMap(){{
            put(numNodeB,1);
            put(numNodeC,12);
        }});

        //init(new String[]{"A","B","C","D","E","F"},"A");
        init1();
        System.out.println(path);
        System.out.println(pathInfo);
        close.add(numNodeA);
        open.add(numNodeB);
        open.add(numNodeC);
        open.add(numNodeD);
        open.add(numNodeE);
        open.add(numNodeF);

        updatePath(numNodeA);
        printPathInfo();
        printPath();
    }

    public static void init1(){
        path.put("A", 0);
        pathInfo.put("A", "A->A");

        path.put("B", 1);
        pathInfo.put("B", "A->B");

        path.put("C", 12);
        pathInfo.put("C", "A->C");

        path.put("D", Integer.MAX_VALUE);
        pathInfo.put("D", "A->D");

        path.put("E", Integer.MAX_VALUE);
        pathInfo.put("E", "A-E");

        path.put("F", Integer.MAX_VALUE);
        pathInfo.put("F", "A->F");
    }


    public static void init2(){
        path.put("A", 0);
        pathInfo.put("A", "A->A");

        path.put("B", 1);
        pathInfo.put("B", "A->B");

        path.put("C", Integer.MAX_VALUE);
        pathInfo.put("C", "A->C");

        path.put("D", Integer.MAX_VALUE);
        pathInfo.put("D", "A->D");

        path.put("E", Integer.MAX_VALUE);
        pathInfo.put("E", "A-E");

        path.put("F", Integer.MAX_VALUE);
        pathInfo.put("F", "A->F");
    }

    /**
     * 查询最近的节点
     * @param numNode
     */
    public static NumNode searchShortestNode(NumNode numNode){
         NumNode result = null;
         Map<NumNode,Integer> numNodeChild = numNode.getChild();
         int minDis = Integer.MAX_VALUE;
         if(numNodeChild!=null) {
            for (NumNode c : numNodeChild.keySet()) {
                if(open.contains(c)){
                    int distance = numNodeChild.get(c);
                    if(distance<minDis){
                        minDis = distance;
                        result = c;
                    }
                }
            }
        }
        return result;
    }


    public static void updatePath(NumNode start){
        //取距离start节点最近的子节点,放入close
        NumNode nearest = searchShortestNode(start);
        //System.out.println(nearest);
        if (nearest == null) {
            return;
        }
        close.add(nearest);     //已遍历的
        open.remove(nearest);   //未遍历的

        Map<NumNode, Integer> childs = nearest.getChild();//getChild()返回child 对象，

        //child中存储了各节点到相邻节点的距离·
        if(childs!=null && !childs.isEmpty()) {
            for (NumNode child : childs.keySet()) {//遍历最近的节点
                if (open.contains(child)) {//如果子节点在open中
                    int newCompute = path.get(nearest.getName()) + childs.get(child);
                    //Map接口的get()方法用来返回key所对应的value，
                    //此句是用来计算neareset这个节点到每个子节点的距离
                    if (newCompute < path.get(child.getName())) {
                        //新计算出来的距离小于之前设置的距离
                        path.put(child.getName(), newCompute);
                        pathInfo.put(child.getName(), pathInfo.get(nearest.getName()) + "->" + child.getName());
                    }
                }
            }
        }
        updatePath(nearest);//向外一层层递归,直至所有顶点被遍历
    }


    public static void printPathInfo() {
        //entrySet()返回此映射中包含的映射关系的set视图
        Set<Map.Entry<String, String>> pathInfos = pathInfo.entrySet();
        for (Map.Entry<String, String> pathInfo : pathInfos) {
            System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue());
        }
    }

    public static void printPath() {
        //entrySet()返回此映射中包含的映射关系的set视图
        Set<Map.Entry<String, Integer>> paths = path.entrySet();
        for (Map.Entry<String, Integer> path : paths) {
            System.out.println(path.getKey() + ":" + path.getValue());
        }
    }
}
