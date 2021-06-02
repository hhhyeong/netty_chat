package org.example.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

// String 객체를 처리하기 위해 수정. 
public class ChatClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

	public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		// 서버에서 받은 문자열 메시지를 콘솔로 출력하기.
		System.out.println(msg);
	}

}
