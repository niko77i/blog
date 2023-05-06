package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.niko.blog.entiy.pojo.UserMessage;
import com.niko.blog.service.UserMessageService;
import com.niko.blog.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    /**
     * 即使向userId用户发送信息
     * @param toUserId
     */
    @Override
    public void sendMessCountToUser(Long toUserId) {
        int count = (int) userMessageService.count(new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getToUserId,toUserId)
                .eq(UserMessage::getStatus,0)
        );

        //websocket通知 (/user/toUserId/messCount)
        simpMessagingTemplate.convertAndSendToUser(toUserId.toString(),"/messCount",count);
    }
}
