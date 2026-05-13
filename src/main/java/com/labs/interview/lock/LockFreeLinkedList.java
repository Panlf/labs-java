package com.labs.interview.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无锁线程安全的单链表
 * @author panlf
 * @date 2026/5/13
 */
public class LockFreeLinkedList<T>{
    private final AtomicReference<Node<T>> head = new AtomicReference<>(null);

    private static class Node<T> {
        final T item;
        final AtomicReference<Node<T>> next;

        Node(T item,Node<T> next){
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    //使用CAS机制来确保数据修改的安全性
    public void add(T item){
        Node<T> newHead = new Node<>(item,null);
        Node<T> oldHead;
        do {
            oldHead = head.get();
            newHead.next.set(oldHead);
        } while (!head.compareAndSet(oldHead,newHead));
    }

    public void printList(){
        Node<T> current = head.get();
        while (current != null){
            System.out.print(current.item + " -> ");
            current = current.next.get();
        }
        System.out.println("null");
    }
}

