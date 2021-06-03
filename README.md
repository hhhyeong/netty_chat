# netty_chat
netty 라이브러리를 이용한 간단한 채팅 프로그램

# 참고영상
https://youtu.be/tsz-assb1X8

# 참고 사이트
https://woowabros.github.io/experience/2020/06/19/chat-app.html


# 구현 순서

1. ChatClient.java
    1. ChatClient 생성자
    2. run() 실행 메서드
        - Netty의 io이벤트 Loop그룹 객체 생성.
        - Bootstrap 클래스 이용하여 채널 설정.
        - Bootstrap 객체에 지정된 서버에 대한 연결(Channel 객체) 생성 요청
            - bootstrap.connect(host, port).sync().channel()
            - ChatClientInitializer가 채널을 처리하도록 설정
            - .handler(new ChatClientInitializer());
	3. main() 함수에서 ChatClient 시작.

2. ChannelInitializer<SocketChannel> 확장한 ChatClientInitializer 클래스
    1. SocketChannel 초기화 : initChannel(SocketChannel ch)
       - ChannelPipeline pipeline = ch.pipeline();
       - pipeline.addLast(“framer”, new DelimiterBasedFrameDecoder(8192, …));
       - pipeline.addLast(“handler”, new ChatClientHandler());

3. ChannelInboundMessageHandlerAdapter 확장한 ChatClientHandler 클래스
    1. messageReceived(ChannelHandlerContext cox, String msg)


