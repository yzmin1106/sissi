package com.sissi.looper.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class MyLooper implements Runnable, Looper {

	private final static Log LOG = LogFactory.getLog(MyLooper.class);

	private final AtomicBoolean state;

	private final AtomicInteger counter;

	private Runner runner;

	private Future<?> future;

	private Feeder feeder;

	private Interval interval;

	public MyLooper(Runner runner, Interval interval, Future<?> future, Feeder feeder) {
		super();
		this.state = new AtomicBoolean();
		this.counter = new AtomicInteger();
		this.runner = runner;
		this.future = future;
		this.feeder = feeder;
		this.interval = interval;
	}

	@Override
	public void run() {
		this.counter.incrementAndGet();
		while (true) {
			try {
				if (this.prepareStop()) {
					LOG.debug("MyLooper is stopping");
					break;
				}
				this.getAndFeed();
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		this.counter.decrementAndGet();
	}

	private void getAndFeed() throws InterruptedException, ExecutionException, TimeoutException {
		Protocol protocol = (Protocol) future.get(interval.getInterval(), interval.getUnit());
		if (protocol != null) {
			LOG.debug("Get Protocol: " + protocol);
			this.feeder.feed(protocol);
		}
	}

	private boolean prepareStop() {
		return this.state.get() == false;
	}

	@Override
	public void start() {
		this.state.set(true);
		this.runner.executor(this);
		LOG.debug("MyLooper is running");
	}

	@Override
	public void stop() {
		this.state.set(false);
	}

	public static class Interval {

		private Long interval;

		private TimeUnit unit;

		public Interval(Long interval, TimeUnit unit) {
			super();
			this.interval = interval;
			this.unit = unit;
		}

		public Long getInterval() {
			return interval;
		}

		public TimeUnit getUnit() {
			return unit;
		}
	}

	static class Runner {

		private Executor executor;

		private Integer threadNum;

		public Runner(Executor executor, Integer threadNum) {
			super();
			this.executor = executor;
			this.threadNum = threadNum;
		}

		public Executor getExecutor() {
			return executor;
		}

		public Integer getThreadNum() {
			return threadNum;
		}

		public void executor(Runnable runnable) {
			LOG.debug("ThreadConnector will open " + threadNum + " threads");
			for (int num = 0; num < threadNum; num++) {
				this.executor.execute(runnable);
			}
		}
	}
}