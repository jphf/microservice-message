/**
 * 
 */

var SECURED_CHAT_ROOM = '/secured/room';
var SECURED_CHAT_SPECIFIC_USER = '/user/queue/message';
var SECURED_CHAT_SPECIFIC_USER_FRIENDS = '/user/queue/friend';

var sendEndpoint;
var stompClient;

var opts = {
	to: 'to',
	text: 'text',
	disconnect: 'disconnect',
	conversationDiv: 'conversationDiv',
	response: 'response',
	user: 'user',
	displayTo: 'displayTo'
};

var s = new SocketService();

window.onload = function() {
	sendEndpoint = SECURED_CHAT_ROOM;
	stompClient = s.connect(sendEndpoint, opts, function(frame) {
		s.subscribeToSpecific(stompClient, SECURED_CHAT_SPECIFIC_USER, opts);
		s.subscribeToSpecificFriends(stompClient, SECURED_CHAT_SPECIFIC_USER_FRIENDS, opts);
	});

	var docChatMessage = document.getElementById('text');

	document.getElementById("send").addEventListener("click", function(event) {
		event.preventDefault();

		console.log(docChatMessage.value);

		var to = document.getElementById(opts.to).value;
		if (!to) {
			alert("Must choose user first");
		}

		s.sendMessage(opts, stompClient, "/app" + sendEndpoint);
		docChatMessage.value = "";
	});

};
