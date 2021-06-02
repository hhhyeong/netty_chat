package org.example.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

	private static final ChannelGroup channels = new DefaultChannelGroup();
	
	public void handlerAdded(ChannelHandlerContext ctx) {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.write("[SERVER] - " + incoming.remoteAddress() + "has joined!\n");
		}
		channels.add(ctx.channel());
	}
	
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.write("[SERVER] - " + incoming.remoteAddress() + "has left!\n");
		}
		channels.remove(ctx.channel());
	}
	
	public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.write("[" + incoming.remoteAddress() + "]" + msg + "\n");
			}
		}
	}

}
