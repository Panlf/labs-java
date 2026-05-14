package com.labs.algorithm.dijkstra.classes;

import java.util.Map;

/**
 * @author panlf
 * @date 2021/9/9
 */
public class NumNode {

    private String name;

    private Map<NumNode,Integer> child;

    public NumNode(){}

    public NumNode(String name,Map<NumNode,Integer> child){
        this.name=name;
        this.child=child;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<NumNode, Integer> getChild() {
        return child;
    }

    public void setChild(Map<NumNode, Integer> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "NumNode{" +
                "name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
