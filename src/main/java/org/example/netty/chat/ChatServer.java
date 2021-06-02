package org.example.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
	
	// ChatServer에 들어오는 연결을 8000 포트로 시작하는 메인 메서드.
	public static void main(String[] args) throws Exception {
		new ChatServer(8000).run();
	}
	
	private final int port;
	
	// 수신 연결을 수신할 포트.
	public ChatServer(int port) {
		this.port = port;
	}
	// 들어오는 연결을 수신하고, 전달하는 실행 메서드.
	public void run() throws Exception {
		// IO EventLoopGroup #1) 들어오는 연결을 수락하는 역할.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// IO EventLoopGroup #2) 연결이 도착하고, 처리과정에서 작업자 그룹에 전달.
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			// Bootstrap클래스 이용하여 채널 설정(서버가 수신을 처리하는 방법 정의).
			ServerBootstrap bootstrap = new ServerBootstrap()
					// 보스그룹, 작업자 그룹 설정.
					.group(bossGroup, workerGroup)
					// 통신을 위해, 새로운 IO ServerSocket 사용.
					.channel(NioServerSocketChannel.class)
					// 다음으로 들어오는 메시지를 처리할 클래스 정의.
					.childHandler(new ChatServerInitializer());
			// ServerBootstrap 객체에 지정된 포트에 바인딩하도록 지시.
			// 들어오는 연결에 대하여 수신대기 시작.
			bootstrap.bind(port).sync().channel().closeFuture().sync();
		}
		finally {
		 	// run메서드 종료하기 전에, EventLoopGroup 정리.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
