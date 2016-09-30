package com.mark.socket.util;

import java.io.PrintStream;

import javafx.scene.control.TextArea;

public class MessagesUtil {

	public static final String connectSucc = "�ɹ����ӵ�������!IP:%s,�˿�:%s";
	public static final String connectTimout = "���ӷ�������ʱ";
	public static final String disconnectSucc = "socket�Ͽ����ӳɹ���";
	public static final String remoteDisconnectSucc = "Զ�������Ͽ����ӣ�";
	public static final String portErr = "�˿ڱ�ռ��%S";
	public static final String clientConnectMsg = "�յ�����IP:%s����������";
	public static final String createSucc = "socket�����������ɹ���";
	public static final String stopSucc = "�ر�socket��������";

	public static final String line = "---------------------------------------------------------------------";

	public static void log(TextArea msgTextArea,String format,String ...s){

		String msg;
		if (s == null || s.length == 0 ) {

			msgTextArea.setText(msgTextArea.getText()+"��ʾ: "+ format+"\n");
		} else {

			msgTextArea.setText(msgTextArea.getText()+"��ʾ: "+ String.format(format, s)+"\n");
		}

		msgTextArea.positionCaret(msgTextArea.getText().length());
		msgTextArea.setScrollTop(Double.MAX_VALUE);
	}

}
