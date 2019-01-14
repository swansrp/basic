package com.srct.service.config.actuator;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.srct.service.utils.log.Log;

/**
 * <p>@Endpoint 是构建 rest 的唯一路径 </p>
 * 不同请求的操作，调用时缺少必需参数，或者使用无法转换为所需类型的参数，则不会调用操作方法，响应状态将为400（错误请求）
 * <P>@ReadOperation = GET 响应状态为 200 如果没有返回值响应 404（资源未找到） </P>
 * <P>@WriteOperation = POST 响应状态为 200 如果没有返回值响应 204（无响应内容） </P>
 * <P>@DeleteOperation = DELETE 响应状态为 200 如果没有返回值响应 204（无响应内容） </P>
 * Access Path : actuator/*
 * @author ruopeng.sha
 * @since 2018/5/24 
 */

@Component
@Endpoint(id = "reload", enableByDefault = false)
public class ReloadEndpoint implements ApplicationContextAware {
 
	private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections
			.unmodifiableMap(
					Collections.singletonMap("message", "No context to shutdown."));

	private static final Map<String, String> RELOADING_MESSAGE = Collections
			.unmodifiableMap(
					Collections.singletonMap("message", "Reloading..."));

	private ConfigurableApplicationContext context;
	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.context = (ConfigurableApplicationContext) applicationContext;
	}
	
	@ReadOperation
	public Map<String, String> shutdown() {
		if (this.context == null) {
			return NO_CONTEXT_MESSAGE;
		}
		try {
			return RELOADING_MESSAGE;
		}
		finally {
			Thread thread = new Thread(this::performReload);
			thread.setContextClassLoader(getClass().getClassLoader());
			thread.start();
		}
	}

	private void performReload() {
		try {
			Thread.sleep(500L);
		}
		catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		this.context.stop();
		this.context.start();
	}
	
}
