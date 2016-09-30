package com.mark.socket.util;

import java.io.PrintStream;

import javafx.scene.control.TextArea;

public class MessagesUtil {

	public static final String connectSucc = "成功连接到服务器!IP:%s,端口:%s";
	public static final String connectTimout = "连接服务器超时";
	public static final String disconnectSucc = "socket断开连接成功！";
	public static final String remoteDisconnectSucc = "远程主动断开连接！";
	public static final String portErr = "端口被占用%S";
	public static final String clientConnectMsg = "收到来自IP:%s的连接请求！";
	public static final String createSucc = "socket服务器创建成功！";
	public static final String stopSucc = "关闭socket服务器！";

	public static final String line = "---------------------------------------------------------------------";

	public static void log(TextArea msgTextArea,String format,String ...s){

		String msg;
		if (s == null || s.length == 0 ) {

			msgTextArea.setText(msgTextArea.getText()+"提示: "+ format+"\n");
		} else {

			msgTextArea.setText(msgTextArea.getText()+"提示: "+ String.format(format, s)+"\n");
		}

		msgTextArea.positionCaret(msgTextArea.getText().length());
		msgTextArea.setScrollTop(Double.MAX_VALUE);
	}

}
