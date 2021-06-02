package org.example.netty.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {
	
	public static void main(String[] args) throws Exception {
		// 8000 포트에서 ChatClient 시작. 
		new ChatClient("localhost", 8000).run();
	}
	
	private final String host;
	private final int port;

	// 생성
	public ChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	// 실행 메서
	public void run() throws Exception {
		// Netty 의 io이벤트 Loop그룹 객체 생성.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// Bootstrap 클래스 이용하여 채널 설정.
			Bootstrap bootstrap = new Bootstrap()
					// EventLoopGroup 이용.
					.group(group)
					// 새로운 소켓 사용하도록.
					.channel(NioSocketChannel.class)
					// ChatClientInitializer가 채널을 처리하도록.
					.handler(new ChatClientInitializer());
			
			// bootstrap객체에 지정된 서버에 대한 연결 생성하도록 요청.
			// 연결되면, 상호 작용에 사용할 수 있는 Channel 객체 요청.
			Channel channel = bootstrap.connect(host, port).sync().channel();
			// 콘솔에서 사용자 입력 캡처.
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			// 콘솔에서 사용자 입력을 받아 서버에 쓰는 while 루프.
			while(true) {
				channel.write(in.readLine() + "\r\n");
			}
		}finally {
			// 마지막으로, 어떤 이유로든 루프 종료할때 EventLoopGrou 종료하기.
			group.shutdownGracefully();
		}
	}
}
