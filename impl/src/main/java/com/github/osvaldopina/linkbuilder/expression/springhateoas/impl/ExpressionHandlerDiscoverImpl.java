package com.github.osvaldopina.linkbuilder.expression.springhateoas.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.expression.springhateoas.ExpressionHandler;
import com.github.osvaldopina.linkbuilder.expression.springhateoas.ExpressionHandlerDiscover;
import org.springframework.context.ApplicationContext;

public class ExpressionHandlerDiscoverImpl implements ExpressionHandlerDiscover {

	private List<ExpressionHandler> expressionHandlers;

	private ExpressionHandler expressionHandler;

	public ExpressionHandlerDiscoverImpl(List<ExpressionHandler> expressionHandlers) {
		this.expressionHandlers = new ArrayList<>(expressionHandlers);
		Collections.sort(this.expressionHandlers);
	}

	@Override
	public ExpressionHandler getExpressionHandler(ApplicationContext applicationContext) {
		if (expressionHandler == null) {
			for (ExpressionHandler candidate : expressionHandlers) {
				if (candidate.isValid(applicationContext)) {
					expressionHandler = candidate;
					return expressionHandler;
				}
			}
			throw new LinkBuilderException("Could not find a Expression handler!");
		} else {
			return expressionHandler;
		}
	}
}
