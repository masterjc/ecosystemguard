package com.ecosystem.guard.engine.servlet;

import com.ecosystem.guard.domain.Result;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	private Result result;
	private Throwable e;

	public ServiceException(Result serviceResult) {
		this.result = serviceResult;
	}

	public ServiceException(Result serviceResult, Throwable t) {
		this.result = serviceResult;
		this.e = t;
	}

	public Result getResult() {
		return result;
	}

	public Throwable getThrowable() {
		return this.e;
	}
}
