package com.niko.blog.search.mq;

import com.niko.blog.config.RabbitConfig;
import com.niko.blog.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.ES_QUEUE)
public class MqMessageHandler {
    @Autowired
    SearchService searchService;

    @RabbitHandler
    public void handler(PostMqIndexMessage message){

        log.info("mq 收到一条消息：{}",message.toString());

        switch (message.getType()){
            case PostMqIndexMessage.CREATE_OR_UPDATE:
                searchService.createOrUpdate(message);
                break;
            case PostMqIndexMessage.REMOVE:
                searchService.removeIndex(message);
                break;
            default:
                log.error("没找到对应的消息类型，请注意!!! --> {}",message.toString());
                break;
        }

    }

}
