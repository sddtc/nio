package com.sddtc.thread;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @author sddtc
 * 
 */
public class Client {

	public static void main(String[] args) {
		try {
			SocketAddress address = new InetSocketAddress("localhost", 8080);
			SocketChannel client = SocketChannel.open(address);
			client.configureBlocking(false);
			ByteBuffer buf = ByteBuffer.allocate(1024);

			while (true) {
				buf.clear();

				System.out.println("请输入发送包:");
				String a = new BufferedReader(new InputStreamReader(System.in))
						.readLine();
				if (a == null || a.equals("null")) {
					break;
				}

				DataPacket dp = new DataPacket();
				dp.setContent(a);
				dp.setSendTime(new Date());
				dp.setId(1);

				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bs);
				oos.writeObject(dp);
				oos.close();

				buf.put(bs.toByteArray());
				bs.close();
				buf.flip();
				client.write(buf);
				System.out.println("客户端--发送数据" + a);
				buf.clear();

				while (true) {
					int i = client.read(buf);
					if (i > 0) {
						buf.flip();
						byte[] b = new byte[buf.limit()];
						buf.get(b, buf.position(), buf.limit());
						System.out
								.println("服务端传来的数据：" + new String(b, "utf-8"));
						break;
					}
				}
			}
			client.close();
			System.out.println("连接关闭");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}