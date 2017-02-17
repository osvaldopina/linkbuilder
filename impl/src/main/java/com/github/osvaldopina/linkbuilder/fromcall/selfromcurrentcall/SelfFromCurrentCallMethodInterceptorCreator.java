package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;

public class SelfFromCurrentCallMethodInterceptorCreator {

	public static final SelfFromCurrentCallMethodInterceptorCreator INSTANCE
			= new SelfFromCurrentCallMethodInterceptorCreator();

	SelfFromCurrentCallMethodInterceptorCreator() {
}

	public Object addInterceptorToMethods(Object object, MethodCallUriGenerator methodCallUriGenerator,
										  IntrospectionUtils introspectionUtils) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new SelfFromCurrentCallMethodInterceptor(methodCallUriGenerator, introspectionUtils));
		factory.setTarget(object);
		return factory.getProxy();
	}

}
