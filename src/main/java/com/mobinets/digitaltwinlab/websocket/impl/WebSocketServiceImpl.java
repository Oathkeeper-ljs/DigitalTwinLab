package com.mobinets.digitaltwinlab.websocket.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mobinets.digitaltwinlab.websocket.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * clientType: webClient, gatewayClient
 */
//@ConditionalOnClass(value = WebSocketConfig.class)
@ServerEndpoint("/ws/{clientType}/{userId}") // 创建WebSocket服务端点，请求地址是"/ws"
@Service
public class WebSocketServiceImpl implements WebSocketService {
    /**
     * 线程安全的计数器
     * webClientCount.getAndIncrement() ++, webClientCount.get(), webClientCount.getAndDecrement() --
     */
    private static final AtomicInteger webClientCount = new AtomicInteger();
    private static final AtomicInteger gatewayClientCount = new AtomicInteger();
    /**
     * 线程安全的集合，存放Websocket用户对象
     */
    private static final CopyOnWriteArraySet<WebSocketService> webSocketClientSet = new CopyOnWriteArraySet<>();
    /**
     * 与某个客户端的连接会话，通过session来给客户端发送数据
     */
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    private String userId = "";
    private String clientType = "";
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("clientType") String clientType,
                       @PathParam("userId") String userId) {
        this.session = session;
        this.setClientType(clientType);
        this.setUserId(userId);
        webSocketClientSet.add(this);
        if (this.getClientType().equals("webClient")) {
            webClientCount.getAndIncrement(); // 在线数加一
            log.info("New webclient connected, onLine number: " + webClientCount.get());
            try {
                sendMessage("服务器连接成功，您的用户Id为：" + this.userId);
            } catch (IOException e) {
                log.error("IO error");
            }
        }else if (this.getClientType().equals("gatewayClient")){
            gatewayClientCount.getAndIncrement();
            log.info("New gatewayClient connected, gateway number: " + gatewayClientCount.get());
            try {
                sendMessage("gateway connected!");
            } catch (IOException e) {
                log.error("IO error");
            }
        }else {
            log.error("Wrong Client type connecting, rejected");
            try {
                sendMessage("Wrong Client type");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @OnClose
    public void onClose() {
        webSocketClientSet.remove(this);
        if (this.clientType.equals("webClient")) {
            webClientCount.getAndDecrement();
            log.info("Webclient disconnected, onLine number: " + webClientCount.get());
        } else if (this.clientType.equals("gatewayClient")){
            gatewayClientCount.getAndDecrement();
            log.info("GatewayClient disconnected, gateway number: " + gatewayClientCount.get());
        }

    }
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Message from client: " + this.userId + ", message: " + message);
        // 若是来自网关的消息，则转发给所有在线用户
        if (this.getClientType().equals("gatewayClient")){
            sendInfoAll(parseJSON(message));
            try {
                sendMessage("ACK"); // 已收到
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            String toClientType = JSON.parseObject(message).getString("toClientType");
            if (toClientType.equals("gatewayClient")) {
                sendToGateway(message);
            }else {
                String toUserId = JSON.parseObject(message).getString("toUserId");
                sendToId(toUserId, message);
            }

        }

    }
    @OnError
    public void onError(Session session, @NotNull Throwable error) {
        log.error("Error");
        error.printStackTrace();
    }
    public void sendInfoAll(String message) {
        for (WebSocketService user : webSocketClientSet) {
                try {
                    if (user.getClientType().equals("webClient")){
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    public void sendToId(String toUserId, String message) {
        boolean isFound = false;
        for (WebSocketService user : webSocketClientSet) {
            String userId = user.getUserId();
            if (userId.equals(toUserId)) {
                try {
                    user.sendMessage(parseJSON(message));
                    isFound = true;
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!isFound) {
            log.info("toUserId not online!");
            try {
                sendMessage("目标用户不在线！");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void sendToGateway(String message) {
        if (gatewayClientCount.get() != 0) {
            for (WebSocketService gateway : webSocketClientSet) {
                if (gateway.getClientType().equals("gatewayClient")) {
                    try {
                        gateway.sendMessage(parseJSON(message));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else {
            try {
                sendMessage("当前无网关在线");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 解析消息文本，并加入fromUserId信息
     * @param message 消息内容，字符串
     * @return JSON格式字符串
     */
    private String parseJSON(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        jsonObject.put("fromUserId", this.userId);
        return jsonObject.toJSONString();
    }
    public String getClientType() {
        return clientType;
    }

    private void setClientType(String clientType) {
        this.clientType = clientType;
    }
    public String getUserId() {
        return userId;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }
}