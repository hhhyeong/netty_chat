package org.example.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

// String 메시지 처리하도록 변경.
public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

	// ChannelGroup 객체에서 채널 추적 가능.
	private static final ChannelGroup channels = new DefaultChannelGroup();
	
	// 활성 채널을 추적하기 위해, 재정의한 함수.
	// 새 ChatClient가 ChatServer에 연결될 때 호출됨.
	// => 다른 ChatClient에 새 클라이언트가 참여했음을 알릴 수 있음.
	public void handlerAdded(ChannelHandlerContext ctx) {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.write("[SERVER] - " + incoming.remoteAddress() + "has joined!\n");
		}
		// ChannelGroup 객체에 추가.
		channels.add(ctx.channel());
	}
	
	// ChatClient가 언제 ChatServer에서 연결이 끊어지는지 추적해야함.
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.write("[SERVER] - " + incoming.remoteAddress() + "has left!\n");
		}
		// ChannelGroup 객체에서 제거.
		channels.remove(ctx.channel());
	}
	
	public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		// 메시지를 보낸 사람을 식별하기 위해 Channel 객체를 요청.
		Channel incoming = ctx.channel();
		// 다른 모든 사람에게 메시지 보내기.
		// 알려진 채널을 선택하고, 각 채널에 메시지 작성.
		for (Channel channel : channels) {
			// 서버에 메시지를 보낸 채널을 제외한 모든 채널에만 메시지 보내야함.
			if (channel != incoming) {
				channel.write("[" + incoming.remoteAddress() + "]" + msg + "\n");
			}
		}
	}

}
