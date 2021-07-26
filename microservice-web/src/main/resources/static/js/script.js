/**
 * 
 */

var SECURED_CHAT_ROOM = '/secured/room';

var sendEndpoint;
var stompClient;

var opts = {
	from: 'from',
	to: 'to',
	text: 'text',
	disconnect: 'disconnect',
	conversationDiv: 'conversationDiv',
	response: 'response'
};

var s = new SocketService();

window.onload = function() {
	sendEndpoint = SECURED_CHAT_ROOM;
	stompClient = s.connect(sendEndpoint, opts, false);

	var docChatMessage = document.getElementById('text');

	document.getElementById("send").addEventListener("click", function(event){
		event.preventDefault();
		
		console.log(docChatMessage.value);
		
		s.sendMessage(opts, stompClient, "/spring-security-mvc-socket/"+sendEndpoint);
		docChatMessage.value = "";
	});
	
};



function connectionSuccess() {
	stompClient.subscribe('/topic/javainuse', onMessageReceived);
	stompClient.send("/app/chat.newUser", {}, JSON.stringify({
		sender: name,
		type: 'newUser'
	}))
}