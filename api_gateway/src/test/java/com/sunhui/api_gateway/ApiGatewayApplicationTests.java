package com.sunhui.api_gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiGatewayApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hhhhh");
        //Mirror是个单例的，只构建了一个对象
        Mirror mirror = new Mirror("魔镜");

        //三个线程都在用这面镜子
        MirrorThread thread1 = new MirrorThread(mirror,"张三");
        MirrorThread thread2 = new MirrorThread(mirror,"李四");
        MirrorThread thread3 = new MirrorThread(mirror,"王二");

        thread1.start();
        thread2.start();
        thread3.start();
    }


    class MirrorThread extends Thread{
        private Mirror mirror;

        private String threadName;

        public MirrorThread(Mirror mirror, String threadName){
            this.mirror = mirror;
            this.threadName = threadName;
        }

        //照镜子
        public String lookMirror() {
            return threadName+" looks like "+ mirror.getNowLookLike().get();
        }

        //化妆
        public void makeup(String makeupString) {
            mirror.getNowLookLike().set(makeupString);
        }

        @Override
        public void run() {
            int i = 1;//阈值
            while(i<5) {
                try {
                    long nowFace = (long)(Math.random()*50);
                    sleep(nowFace);
                    StringBuffer sb = new StringBuffer();
                    sb.append("第"+i+"轮从");
                    sb.append(lookMirror());
                    makeup(String.valueOf(nowFace));
                    sb.append("变为");
                    sb.append(lookMirror());
                    System.out.println(sb);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }


    class Mirror{
        private String mirrorName;

        //每个人要看到自己的样子，所以这里要用ThreadLocal
        private ThreadLocal<String> nowLookLike;

        public Mirror(String mirrorName){
            this.mirrorName=mirrorName;
            nowLookLike = new ThreadLocal<String>();
        }

        public String getMirrorName() {
            return mirrorName;
        }

        public ThreadLocal<String> getNowLookLike() {
            return nowLookLike;
        }

    }

}
