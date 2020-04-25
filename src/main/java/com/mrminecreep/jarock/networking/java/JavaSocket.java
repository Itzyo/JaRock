package com.mrminecreep.jarock.networking.java;
    
import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.networking.ClientRegistry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
    

public class JavaSocket {
	
    public static final ChannelGroup all = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
    private int port;
    
    public JavaSocket(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
     				ClientRegistry.registerClient(ch.remoteAddress().toString(), ch);
     				Logger.log_info("Got connection to %s.", ch.remoteAddress().toString());
     				ch.pipeline().addLast(new PacketSplitter());
                    ch.pipeline().addLast(new Decoder());
                    ch.pipeline().addLast(new Encoder());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128);
    
            
            ChannelFuture f = b.bind(port).sync(); 
    
            
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}