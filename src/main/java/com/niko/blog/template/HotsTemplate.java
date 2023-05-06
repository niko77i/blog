package com.niko.blog.template;

import com.niko.blog.common.templates.DirectiveHandler;
import com.niko.blog.common.templates.TemplateDirective;
import com.niko.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HotsTemplate extends TemplateDirective {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String key = "week:rank";

        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(key, 0, 6);

        List<Map> hotsPosts = new ArrayList<>();

        for (ZSetOperations.TypedTuple typedTuple:typedTuples){
            Map<String,Object> map = new HashMap<>();

            String hKey = "rank:post:"+typedTuple.getValue();

            map.put("id",typedTuple.getValue());
            map.put("title",redisUtil.hget(hKey,"post:title"));
            map.put("commentCount",typedTuple.getScore());

            hotsPosts.add(map);
        }

        handler.put(RESULTS,hotsPosts).render();

    }
}
