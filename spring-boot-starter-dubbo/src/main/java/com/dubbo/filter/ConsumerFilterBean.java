package com.dubbo.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Activate(group = Constants.CONSUMER)
public class ConsumerFilterBean implements Filter {

	private static volatile List<ConsumerFilter> rpcFilters = new ArrayList<ConsumerFilter>();

	public static void setConsumerFilter(Map<String, ConsumerFilter> consumerFilters) {
		List<String> keys = new ArrayList<String>(consumerFilters.keySet());
		Collections.sort(keys); // 排序问题
		for (int index = 0; index < keys.size(); index++) {
			String key = keys.get(index);
			ConsumerFilter consumerFilter = consumerFilters.get(key);
			rpcFilters.add(consumerFilter);
		}
		log.debug("添加自定义ConsumerFilter：{}", JSON.toJSONString(keys));
	}

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		@SuppressWarnings({ "rawtypes", "unchecked" })
        JoinPoint<?> joinPoint = new ProceedingJoinPoint(invoker, invocation, rpcFilters);
		return joinPoint.proceed();
	}
}