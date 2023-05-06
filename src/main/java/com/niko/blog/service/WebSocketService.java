package com.niko.blog.service;

public interface WebSocketService {
    /**
     * 即使向userId用户发送信息
     * @param toUserId
     */
    void sendMessCountToUser(Long toUserId);
}
