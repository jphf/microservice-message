'use strict';

var SECURED_CHAT_SPECIFIC_USER = '/user/queue/specific-user';
var SECURED_CHAT_SPECIFIC_USER_FRIENDS = '/user/queue/specific-user-friends';

var idHelper = function(context) {
	return document.getElementById(context);
};

function SocketService() {

	var that = this;

	that.sessionId = '';

	/**
	 * Generic methods.
	 */

	that.connect = function(endpoint, opts, isBroadcastAll) {
		var socket = new SockJS(endpoint), stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {

			if (!isBroadcastAll) {
				var url = stompClient.ws._transport.url;
				console.log(stompClient.ws._transport.url);
				url = /\/([^\/]+)\/websocket/.exec(url)[1]
				console.log("Your current session is: " + url);
				that.sessionId = url;
			}
			that.subscribeToSpecific(stompClient, SECURED_CHAT_SPECIFIC_USER, opts);
			that.subscribeToSpecificFriends(stompClient, SECURED_CHAT_SPECIFIC_USER_FRIENDS, opts);
			that.subscribeToSpecificFriends(stompClient, "/user/queue/test", opts);
		});
		return stompClient;
	};

	that.disconnect = function(opts, stompClient) {
		if (stompClient !== null && stompClient !== undefined) { stompClient.disconnect(); }
	};

	that.sendMessage = function(opts, stompClient, endpoint) {
		var to = idHelper(opts.to).value;
		var from = idHelper(opts.from).value;

		var msg = {
			'from': (from === undefined || from === null) ? to : from,
			'to': (to === undefined || to === null) ? "ALL" : to,
			'text': idHelper(opts.text).value
		};

		console.log(JSON.stringify(msg));
		stompClient.send(endpoint, {}, JSON.stringify(msg));
	};

	that.messageOut = function(msg, opts) {
		var r = idHelper(opts.response), p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.appendChild(document.createTextNode(msg.from + ': ' + msg.text + ' (' + msg.time + ')'));
		r.appendChild(p);
	};

	/**
	 * Broadcast to All Users.
	 */

	that.subscribeToAll = function(client, url, opts) {
		idHelper('subscribeAll').disabled = true;
		idHelper('subscribeSpecific').disabled = true;
		client.subscribe(url, function(msgOut) {
			that.messageOut(JSON.parse(msgOut.body), opts);
		});
	};

	/**
	 * Broadcast to Specific User.
	 */

	that.subscribeToSpecific = function(client, url, opts) {
		//		client.subscribe(url + "-user" + that.sessionId, function(msgOut) {
		client.subscribe(url, function(msgOut) {
			that.messageOut(JSON.parse(msgOut.body), opts);
		});
	};

	that.subscribeToSpecificFriends = function(client, url, opts) {
		client.subscribe(url, function(msgOut) {
			console.log("fffffffffffffff");
			console.log(msgOut.body);
			//			that.messageOut(JSON.parse(msgOut.body), opts);
		});
	};
}