package com.mark.socket.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.mark.socket.MainApp;
import com.mark.socket.util.MessagesUtil;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SocketViewController {


	@FXML
	private TextField ipTextField;

	@FXML
	private TextField portTextField;

	@FXML
	private TextArea msgTextArea;

	@FXML
	private TextArea sndMsgTextArea;

	@FXML
	private Button connectButton;

	@FXML
	private Button disconnectButton;

	@FXML
	private Button createButton;

	@FXML
	private Button stopButton;

	@FXML
	private RadioButton clientRadioButton;

	@FXML
	private RadioButton serverRadioButton;

//	@FXML
//	private ToggleGroup group;

	@FXML
	private RadioButton inRadioButton;

	@FXML
	private RadioButton outRadioButton;

	@FXML
	private TextField inIpTextField;

	@FXML
	private TextField inPortTextField;

	@FXML
	private TextField inRstTextField;

	@FXML
	private ComboBox<String> inFromComboBox;

	@FXML
	private ComboBox<String> inToComboBox;

	@FXML
	private ComboBox<String> inFromLanComboBox;

	@FXML
	private TextField inCrgNoTextField;

	@FXML
	private TextField inBodyNoTextField;

	@FXML
	private TextField outPortTextField;

	@FXML
	private TextField outRstTextField;

	@FXML
	private Button outOpenButton;

	@FXML
	private Button outCloseButton;

	@FXML
	private Button inEnterButton;

	@FXML
	private Button stkClearButton;

	@FXML
	private TextArea stkMsgTextArea;

	@FXML
	private Group outGroup;

	@FXML
	private Group inGroup;

	@FXML
	private ToggleGroup yy;


	@FXML
	private ToggleGroup group;

	@FXML
	private ToggleGroup group1;

	//
	private MainApp mainApp;

	//
	private Socket _socket = null;

	private ServerSocket _serverSocket = null;

	private OutputStream _os;

	private InputStream _is;


	private String regex = "[0-9]*";

	private Thread _thread;

	private Thread _stkInThread;

	private Thread _stkOutThread;

    private String rexp =
    		   "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	private Socket _inSocket = null;

	private ServerSocket _inServerSocket = null;

	private OutputStream _inOs;

	private InputStream _inIs;

	private Thread _inThread;

    public SocketViewController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	msgTextArea.setText("提示: 欢淫使用socket通讯工具\n");

    	stkMsgTextArea.setText("提示: 欢淫使用平台通讯工具\n");

		connectButton.setDisable(false);

		disconnectButton.setDisable(true);

		createButton.setDisable(true);

		stopButton.setDisable(true);

		//

		outGroup.setDisable(true);

        inFromComboBox.getItems().addAll("EWLWBS71","EWLPBS71","        ");
        inFromComboBox.setValue("EWLWBS71");

        inToComboBox.getItems().addAll("T2R501  ","T2R502  ","        ");
        inToComboBox.setValue("T2R501  ");

        inFromLanComboBox.getItems().addAll(" ","A","B");
        inFromLanComboBox.setValue(" ");

		newthread();

    }

    public void newthread(){
    	_thread = new Thread(()->{
			try{
				while(true) {
					if (_socket == null || !_socket.isConnected()
							|| _socket.isClosed()) {
						continue;
					}
					_is = _socket.getInputStream();
					byte[] buf = new byte[9999];
					int receiveMsg = _is.read(buf);


					if (receiveMsg == -1)
					{
						if (this.clientRadioButton.isSelected()) {

							connectButton.setDisable(false);

							disconnectButton.setDisable(true);
						}
						MessagesUtil.log(msgTextArea,MessagesUtil.remoteDisconnectSucc);
						_socket.close();
						break;

					}

			        String recvMsg = new String(buf, 0, receiveMsg, "UTF-8");
			        MessagesUtil.log(msgTextArea, MessagesUtil.line);
		    		MessagesUtil.log(msgTextArea,"收到电文: "+recvMsg);
				}
			} catch(SocketException se){
				if (!_socket.isClosed()) {
					try {
						_socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			}
		);
    }

    public void newOutStkThread(){
    	_stkOutThread = new Thread(()->{
			try{
				while(true) {
					if (_inSocket == null || !_inSocket.isConnected()
							|| _inSocket.isClosed()) {
						continue;
					}
					_inIs = _inSocket.getInputStream();
					byte[] buf = new byte[9999];
					int receiveMsg = _inIs.read(buf);


					if (receiveMsg == -1)
					{
						MessagesUtil.log(stkMsgTextArea,MessagesUtil.remoteDisconnectSucc);
						_inSocket.close();
						break;

					}

			        String recvMsg = new String(buf, 0, receiveMsg, "UTF-8");
			        MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
		    		MessagesUtil.log(stkMsgTextArea,"收到电文: "+recvMsg);
				}
			} catch(SocketException se){
				if (!_socket.isClosed()) {
					try {
						_socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		);
    }

    public void newInStkthread(){
    	_stkInThread = new Thread(()->{
			try{
				if (_inSocket == null || !_inSocket.isConnected()
						|| _inSocket.isClosed()) {
					return;
				}
				_inIs = _inSocket.getInputStream();
				byte[] buf = new byte[9999];
				int receiveMsg = _inIs.read(buf);


				if (receiveMsg == -1)
				{
					MessagesUtil.log(stkMsgTextArea,MessagesUtil.remoteDisconnectSucc);
					_inSocket.close();
					return;
				}

		        String recvMsg = new String(buf, 0, receiveMsg, "UTF-8");
		        MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
	    		MessagesUtil.log(stkMsgTextArea,"收到电文: "+recvMsg);
	    		if (recvMsg.length() <141) {
	    			return;
	    		}
	    		StringBuffer stemp = new StringBuffer();
				if ("00".equals(recvMsg.substring(28,30 )) ) {
					 stemp.append(recvMsg.substring(8,16 ));
					 stemp.append(recvMsg.substring(0,8 ));
					 stemp.append(recvMsg.substring(16,20 ));
					 stemp.append(recvMsg.substring(20,25 ));
					 stemp.append("0");
					 stemp.append(recvMsg.substring(26,28 ));
					 stemp.append(inRstTextField.getText());
					 stemp.append(recvMsg.substring(30));
					 _inOs.write(stemp.toString().getBytes("UTF-8"));
				}
		        MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
	    		MessagesUtil.log(stkMsgTextArea,"应答电文: "+stemp.toString());

			} catch(SocketException se){
				if (!_inSocket.isClosed()) {
					try {
						_inSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try{
			        if ((_inOs != null) && (!_inOs.equals(""))) {
			        	_inOs.close();
			        }

			        if ((_inIs != null) && (!_inIs.equals(""))) {
			        	_inIs.close();
			        }


			        if (!_inSocket.isClosed()) {
			        	_inSocket.close();
			        }
				} catch (IOException e) {
					e.printStackTrace();
				}

		        MessagesUtil.log(stkMsgTextArea,MessagesUtil.disconnectSucc);
			}

			}
		);
    }

    @FXML
    private void handleClear() {

    	msgTextArea.setText("");

    }

    @FXML
    private void handleClearStk() {

    	stkMsgTextArea.setText("");

    }

    @FXML
    private void handleStopServer() {
    	try {
	        if ((_os != null) && (!_os.equals(""))) {
				_os.close();
	        }

	        if ((_is != null) && (!_is.equals(""))) {
	        	_is.close();
	        }


	        if (!_serverSocket.isClosed()) {
	        	_serverSocket.close();
	        }
	        MessagesUtil.log(msgTextArea,MessagesUtil.stopSucc);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    private void handleStartStkServer(){
       	if (outPortTextField.getText() == null
    			|| this.outPortTextField.getText().trim().equals("") ) {
    		return;
    	}
    	if (!outPortTextField.getText().matches(regex)) {
    		return;
    	}

    	int port = Integer.parseInt(outPortTextField.getText());

    	if (port<=0||port>65535) {

    		return;
    	}

    	if (outRstTextField.getText() == null || outRstTextField.getText().trim().equals("")) {
    		return;
    	}

    	if (!outRstTextField.getText().matches(regex)) {
    		return;
    	}

    	if (outRstTextField.getText().length() !=2) {
    		return;
    	}

    	try {
			_inServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			MessagesUtil.log(stkMsgTextArea, MessagesUtil.portErr,
					new String[]{outPortTextField.getText()});
			return;
		}

		MessagesUtil.log(stkMsgTextArea, MessagesUtil.createSucc);
    	Thread t = new Thread(
    		()->{
    			while(true){
    				try {
						_inSocket = _inServerSocket.accept();
						MessagesUtil.log(stkMsgTextArea, MessagesUtil.clientConnectMsg,
								_inSocket.getInetAddress().getHostAddress());

						_inIs = _inSocket.getInputStream();
						_inOs = _inSocket.getOutputStream();

						byte[] buf = new byte[9999];
						int receiveMsg = _inIs.read(buf);


						if (receiveMsg == -1)
						{

							MessagesUtil.log(stkMsgTextArea,MessagesUtil.remoteDisconnectSucc);

							continue;

						}

				        String recvMsg = new String(buf, 0, receiveMsg, "UTF-8");
				        MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
			    		MessagesUtil.log(stkMsgTextArea,"收到电文: "+recvMsg);

						//返回应答电文
						StringBuffer stemp = new StringBuffer();
						stemp.append(recvMsg.substring(8,16 ));
						stemp.append(recvMsg.substring(0,8 ));
						stemp.append(recvMsg.substring(16,20 ));
						stemp.append(recvMsg.substring(20,25 ));
						stemp.append("0");
						stemp.append(recvMsg.substring(26,28 ));
						stemp.append(outRstTextField.getText());
						stemp.append(recvMsg.substring(30));
						_inOs.write(stemp.toString().getBytes("UTF-8"));

				        MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
			    		MessagesUtil.log(stkMsgTextArea,"应答电文: "+stemp.toString());


					} catch(SocketException se){
						return;

					} catch (IOException e) {
						e.printStackTrace();
					}


    			}

    		}
    	);

    	t.setDaemon(true);
    	t.start();

    	outPortTextField.setDisable(true);
    	outOpenButton.setDisable(true);
    	outCloseButton.setDisable(false);
    }


    @FXML
    private void handleCloseStkServer() {

    	try {
	        if ((_inOs != null) && (!_inOs.equals(""))) {
	        	_inOs.close();
	        }

	        if ((_inIs != null) && (!_inIs.equals(""))) {
	        	_inIs.close();
	        }


	        if (!_inServerSocket.isClosed()) {
	        	_inServerSocket.close();
	        }
	        MessagesUtil.log(stkMsgTextArea,MessagesUtil.stopSucc);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	outPortTextField.setDisable(false);
    	outOpenButton.setDisable(false);
    	outCloseButton.setDisable(true);
    }

    @FXML
    private void handleCreateServer() {

    	if (this.portTextField.getText() == null
    			|| this.portTextField.getText().equals("") ) {
    		return;
    	}
    	if (!this.portTextField.getText().matches(regex)) {
    		return;
    	}

    	int port = Integer.parseInt(this.portTextField.getText());

    	if (port<=0||port>65535) {

    		return;
    	}

    	try {
			_serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			MessagesUtil.log(msgTextArea, MessagesUtil.portErr,
					new String[]{this.portTextField.getText()});
			return;
		}

		MessagesUtil.log(msgTextArea, MessagesUtil.createSucc);
    	Thread t = new Thread(
    		()->{
    			while(true){
    				try {
						_socket = _serverSocket.accept();
						MessagesUtil.log(msgTextArea, MessagesUtil.clientConnectMsg,
								_socket.getInetAddress().getHostAddress());

						newthread();
						_thread.setDaemon(true);
						_thread.start();
					} catch(SocketException se){
				    	this.createButton.setDisable(false);
				    	this.stopButton.setDisable(true);
						return;

					} catch (IOException e) {
						e.printStackTrace();
					}


    			}

    		}
    	);

    	t.setDaemon(true);
    	t.start();

    	this.createButton.setDisable(true);
    	this.stopButton.setDisable(false);



    }

    @FXML
    private void handlerSelect() {

    	if (serverRadioButton.isSelected()) {

    		if (!disconnectButton.isDisable()) {
    			serverRadioButton.setSelected(false);
    			clientRadioButton.setSelected(true);
    			return;
    		}

    		ipTextField.setDisable(true);
    		connectButton.setDisable(true);
    		disconnectButton.setDisable(true);
    		createButton.setDisable(false);
    		stopButton.setDisable(true);


    	} else if (clientRadioButton.isSelected()){

    		if (!stopButton.isDisable()) {
    			serverRadioButton.setSelected(true);
    			clientRadioButton.setSelected(false);
    			return;
    		}
    		ipTextField.setDisable(false);
    		connectButton.setDisable(false);
    		disconnectButton.setDisable(true);
    		createButton.setDisable(true);
    		stopButton.setDisable(true);
    	}
    }


    @FXML
    private void handlerStkSelect() {


		if (outRadioButton.isSelected()) {
			if (_inSocket!=null && !_inSocket.isClosed()) {
	    		outRadioButton.setSelected(false);
	    		inRadioButton.setSelected(true);
				return;
			}

    		this.inGroup.setDisable(true);

    		this.outGroup.setDisable(false);

    		outCloseButton.setDisable(true);

		} else if (inRadioButton.isSelected()){

	    	if (outOpenButton.isDisable()
	    			&& !outCloseButton.isDisable()) {

	    		outRadioButton.setSelected(true);
	    		inRadioButton.setSelected(false);
	    		return;
	    	}

    		this.inGroup.setDisable(false);

    		this.outGroup.setDisable(true);
		}

    }

    @FXML
    private void handlerEnter() {

    	if (!checkIn()) {

    		return;
    	}

    	_inSocket = new Socket();

    	SocketAddress endpoint = new InetSocketAddress(inIpTextField.getText(),
    			Integer.parseInt(inPortTextField.getText()));
    	try {

    		_inSocket.connect(endpoint, 100);

    		MessagesUtil.log(stkMsgTextArea,MessagesUtil.connectSucc,
    				new String[]{inIpTextField.getText(), inPortTextField.getText()});

			_inOs = _inSocket.getOutputStream();



			StringBuffer strb = new StringBuffer();
			strb.append(inFromComboBox.getSelectionModel().getSelectedItem());
			strb.append(inToComboBox.getSelectionModel().getSelectedItem());
			strb.append("0001");
			strb.append("00013");
			strb.append("2");
			strb.append("05");
			strb.append("  ");
			strb.append("                                                                                                  ");
			strb.append(inFromLanComboBox.getSelectionModel().getSelectedItem());
			strb.append(" ");
			strb.append(inCrgNoTextField.getText());
			strb.append(inBodyNoTextField.getText());

			_inOs.write(strb.toString().getBytes("UTF-8"));


			MessagesUtil.log(stkMsgTextArea, MessagesUtil.line);
			MessagesUtil.log(stkMsgTextArea, "发送电文: "+strb.toString());

			inCrgNoTextField.setText(String.format("%03d", Integer.parseInt(inCrgNoTextField.getText())+1));


			newInStkthread();

			_stkInThread.setDaemon(true);
			_stkInThread.start();
    	}catch(SocketTimeoutException ste){
    		MessagesUtil.log(stkMsgTextArea,MessagesUtil.connectTimout);
			try {
				if (_inSocket != null&& !_inSocket.isClosed()) {

					_inSocket.close();
				}
			} catch (IOException ie) {
				ie.printStackTrace();
			}
    		return ;
    	} catch (Exception e) {

    		e.printStackTrace();
			try {
				if (_inSocket != null&& !_inSocket.isClosed()) {

					_inSocket.close();
				}
			} catch (IOException ie) {
				ie.printStackTrace();
			}
    		return ;
		}


    }

    @FXML
    private void handleConnect() {

    	if (null == ipTextField.getText()
    			|| "".equals(ipTextField.getText())) {

    		return;
    	}

    	if (null == portTextField.getText()
    			|| "".equals(portTextField.getText())) {
    		return;
    	}

    	_socket = new Socket();

    	SocketAddress endpoint = new InetSocketAddress(ipTextField.getText(),
    			Integer.parseInt(portTextField.getText()));
    	try {
    		_socket.connect(endpoint, 100);


    		MessagesUtil.log(msgTextArea,MessagesUtil.connectSucc,
    				new String[]{ipTextField.getText(), portTextField.getText()});

    		connectButton.setDisable(true);

    		disconnectButton.setDisable(false);


		} catch(SocketTimeoutException ste){
    		MessagesUtil.log(msgTextArea,MessagesUtil.connectTimout);
			return;
		}
    	catch (Exception e) {
			e.printStackTrace();
		}

    	newthread();

    	_thread.setDaemon(true);
    	_thread.start();
    }

    @FXML
    private void handleSndMsg() {

    	if (sndMsgTextArea.getText() == null
    			|| "".equals(sndMsgTextArea.getText())) {
    		return;

    	}

    	if (_socket == null || !_socket.isConnected() || _socket.isClosed()) {

    		return;
    	}


    	try {
			_os = _socket.getOutputStream();

			_os.write(sndMsgTextArea.getText().getBytes("UTF-8"));

			MessagesUtil.log(msgTextArea, MessagesUtil.line);
			MessagesUtil.log(msgTextArea, "发送电文: "+sndMsgTextArea.getText());
			sndMsgTextArea.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    private void handleDisconnect() {



    	if (!_socket.isClosed()) {
    		try {
				_socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
    	}

    	try {
	        if ((_os != null) && (!_os.equals(""))) {
				_os.close();
	        }

	        if ((_is != null) && (!_is.equals(""))) {
	        	_is.close();
	        }


	        if (!_socket.isClosed()) {
	        	 _socket.close();
	        }
	        MessagesUtil.log(msgTextArea,MessagesUtil.disconnectSucc);
		} catch (IOException e) {
			e.printStackTrace();
		}


		connectButton.setDisable(false);

		disconnectButton.setDisable(true);


    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        ipTextField.setText(this.mainApp.getSocketBean().getSocketIp());

        portTextField.setText(String.valueOf(this.mainApp.getSocketBean().getSocketPort()));

    }

    public boolean checkIn(){

    	if (inIpTextField.getText() == null || inIpTextField.getText().trim().equals("")) {

    		return false;
    	}

    	if (!inIpTextField.getText().matches(rexp)) {
    		return false;
    	}

    	if (inPortTextField.getText() == null || inPortTextField.getText().trim().equals("")) {
    		return false;
    	}

    	if (!inPortTextField.getText().matches(regex)) {
    		return false;
    	}

    	if (inRstTextField.getText() == null || inRstTextField.getText().trim().equals("")) {
    		return false;
    	}

    	if (!inRstTextField.getText().matches(regex)) {
    		return false;
    	}

    	if (inRstTextField.getText().length() !=2) {
    		return false;
    	}

    	if (inCrgNoTextField.getText() == null || inCrgNoTextField.getText().trim().equals("")) {
    		return false;
    	}

    	if (!inCrgNoTextField.getText().matches(regex)) {
    		return false;
    	}

    	if (inCrgNoTextField.getText().length() !=3) {
    		return false;
    	}

    	if (inBodyNoTextField.getText() == null || inBodyNoTextField.getText().equals("")) {
    		return false;
    	}

//    	if (!inBodyNoTextField.getText().matches(regex)) {
//    		return false;
//    	}

    	if (inBodyNoTextField.getText().length() !=8) {
    		return false;
    	}


    	return true;
    }

}
