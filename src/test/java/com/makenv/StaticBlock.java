package com.makenv;

public class StaticBlock {

    static {
        System.out.println("静态块");
    }

    {
        System.out.println("构造块，在类中定义");
    }

    public StaticBlock() {
        System.out.println("构造方法执行");
    }

    public static void main(String[] args) {
        /*
            静态块
            构造块，在类中定义
            构造方法执行
            构造块，在类中定义
            构造方法执行
        */
        new StaticBlock();
        new StaticBlock();
    }

}
