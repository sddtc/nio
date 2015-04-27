package com.sddtc.thread;

/**
 * @author sddtc
 *
 */
public class Test {
	public static void main(String[] args) {
		try {
			NIOServer nioServer = new NIOServer(8080, 1024);
			nioServer.startListen();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
