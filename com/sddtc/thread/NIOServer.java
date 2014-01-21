package com.sddtc.thread;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author sddtc
 *
 */
public class NIOServer {

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private ServerSocket serverSocket;
	private static int PORT = 8080;
	private static int BUFFER_SIZE = 48;
	private ByteBuffer buf;

	public NIOServer(int port, int bufferSize) {
		PORT = port;
		BUFFER_SIZE = bufferSize;
		this.buf = ByteBuffer.allocate(BUFFER_SIZE);
	}

	public void startListen() throws IOException, ClassNotFoundException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(PORT));
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("端口注册完毕！");

		Iterator<SelectionKey> iter = null;
		SelectionKey key = null;

		while (true) {
			selector.select();
			iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				key = iter.next();
				this.handleKey(key);
				iter.remove();
			}
		}
	}

	public void handleKey(SelectionKey key) throws IOException,
			ClassNotFoundException {
		if (key.isAcceptable()) {
			SocketChannel socketChannel = this.serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			System.out.println("有新连接");
		} else if (key.isReadable()) {
			SocketChannel socketChannel = (SocketChannel) key.channel();
			buf.clear();
			int a = socketChannel.read(buf);
			if (a > 0) {
				buf.flip();
				byte[] b = new byte[buf.limit()];
				buf.get(b, buf.position(), buf.limit());

				ByteArrayInputStream byteIn = new ByteArrayInputStream(b);
				ObjectInputStream objIn = new ObjectInputStream(byteIn);
				DataPacket dp = (DataPacket) objIn.readObject();
				objIn.close();
				byteIn.close();

				System.out.println("从客户端发送到服务端：" + dp.getContent());
				System.out.println("接受时间：" + dp.getSendTime());

				buf.flip();
				socketChannel.write(buf);
			} else {
				socketChannel.close();
			}
		} else if (key.isWritable()) {

		}
	}
}
