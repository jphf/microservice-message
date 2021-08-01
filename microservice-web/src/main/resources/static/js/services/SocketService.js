'use strict';

var SECURED_CHAT_USER = '/secured/user';

var idHelper = function(context) {
	return document.getElementById(context);
};

function SocketService() {

	var that = this;

	var chooseUsername = null;
	/**
	 * Generic methods.
	 */

	that.connect = function(endpoint, opts, callback) {
		var socket = new SockJS(endpoint), stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			if (callback) {
				callback(frame);
			}
		});
		return stompClient;
	};

	that.disconnect = function(opts, stompClient) {
		if (stompClient !== null && stompClient !== undefined) { stompClient.disconnect(); }
	};

	that.sendMessage = function(opts, stompClient, endpoint) {
		var to = idHelper(opts.to).value;

		var msg = {
			'to': (to === undefined || to === null) ? "ALL" : to,
			'text': idHelper(opts.text).value
		};

		console.log(JSON.stringify(msg));
		stompClient.send(endpoint, {}, JSON.stringify(msg));
	};

	that.sendUser = function(username, stompClient, endpoint) {
		var msg = {
			'username': username
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

	that.userOut = function(msg, opts) {
		var r = idHelper(opts.user);

		if (Array.isArray(msg)) {
			msg.forEach(function(u) {
				var p = document.createElement('p');
				p.style.wordWrap = 'break-word';
				p.className = 'user';
				p.appendChild(document.createTextNode(u.username));
				p.addEventListener("click", function() {
					idHelper(opts.to).value = u.username;
					idHelper(opts.displayTo).innerHTML = u.username;

					if (chooseUsername != u.username) {
						idHelper(opts.response).innerHTML = "";
						that.sendUser(u.username, stompClient, "/app" + SECURED_CHAT_USER);
						chooseUsername = u.username;
					}

				});
				r.appendChild(p);
			});
		} else {
			var p = document.createElement('p');
			p.style.wordWrap = 'break-word';
			p.appendChild(document.createTextNode(msg.username));
			p.addEventListener("click", function() {
				idHelper(opts.to).value = u.username;
				idHelper(opts.displayTo).innerHTML = u.username;
			});
			r.appendChild(p);
		}
	}

	/**
	 * Broadcast to Specific User.
	 */

	that.subscribeToSpecific = function(client, url, opts) {
		client.subscribe(url, function(msgOut) {
			that.messageOut(JSON.parse(msgOut.body), opts);
		});
	};

	that.subscribeToSpecificFriends = function(client, url, opts) {
		client.subscribe(url, function(msgOut) {
			console.log("fffffffffffffff");
			console.log(msgOut.body);
			that.userOut(JSON.parse(msgOut.body), opts);
		});
	};
}