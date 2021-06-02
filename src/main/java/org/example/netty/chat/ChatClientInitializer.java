package org.example.netty.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

	public static void main(String[] args) {
	}

	// SocketChannel 초기
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 파이프라인(소통하는 방식) 정의.
		ChannelPipeline pipeline = ch.pipeline();
		
		// Netty에게 최대 8192개의 프임 예상. 각 프레임은 lineDelimiter()로 끝남.
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		// 서버-클라이언트 간에 문자열만 교환하기 때문에, StringDecoder 사용.
		// 수신된 바이트를 문자열로 디코딩함.
		pipeline.addLast("decode", new StringDecoder());
		// 문자열을 바이트로 인코딩 후, 서버에 전달.
		pipeline.addLast("encoder", new StringEncoder());
		// 디코된 모든 수신 문자열을 처리할 클래스 정의.
		pipeline.addLast("handler", new ChatClientHandler());
	}

}
