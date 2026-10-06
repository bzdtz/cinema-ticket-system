package com.saodi.listener;

import com.saodi.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Component
public class KeyExpirationMessageListener implements MessageListener {



    @Autowired
    IOrderService orderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println( message);
        String s = new String(message.getBody());
        System.out.println(s);
        System.out.println(message.getChannel());
        try {
            // 创建一个 ByteArrayInputStream 对象，用于读取序列化字符串
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(message.getBody());

            // 创建一个 ObjectInputStream 对象，用于反序列化对象
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);

            // 读取反序列化的对象
            Object deserializedObject = objectInputStream.readObject();

            // 关闭输入流
            objectInputStream.close();

            // 打印反序列化的对象
            System.out.println(deserializedObject);

//            boolean set = orderController.set(deserializedObject);
//            System.out.println(set);
        } catch (Exception  e) {
            e.printStackTrace();
        }

    }
}