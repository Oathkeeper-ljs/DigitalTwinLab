package com.mobinets.digitaltwinlab.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
//@Service
public interface WebSocketService {
    /**
     * 连接建立时调用的方法
     * @param session
     * @param clientType 客户端类型(webClient/gatewayClient)
     * @param userId 创建的用户Id
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("clientType") String clientType, @PathParam("userId") String userId);
    /**
     * 连接关闭时调用的方法
     */
    @OnClose
    public void onClose();
    /**
     * 有消息到达时调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session);
    /**
     * 发生错误时调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error);
    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException;
    /**
     * 向所有浏览器用户发送Info(真实世界控制器所更新的物理信息)
     * @param message 消息内容
     */
    public void sendInfoAll(String message);

    /**
     * 向Id为toUserId的用户发送消息
     * @param toUserId 目标用户Id
     * @param message 消息内容
     */
    public void sendToId(String toUserId, String message);

    /**
     * 向网关发送信息
     * @param message
     */
    public void sendToGateway(String message);

    public String getClientType();
    public String getUserId();

}
