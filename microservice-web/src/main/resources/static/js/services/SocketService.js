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
		let r = idHelper(opts.response), p = document.createElement('p');
		let from = idHelper(opts.from).value;

		p.style.wordWrap = 'break-word';
		if (msg.from) {
			if (msg.from != from) {
				p.appendChild(document.createTextNode('(' + msg.time + ') ' + msg.text + " :" + msg.from));

				p.classList.add('receive');
			} else {
				p.appendChild(document.createTextNode(msg.from + ": " + msg.text + ' (' + msg.time + ')'));
			}
		} else {
			p.appendChild(document.createTextNode(msg.text));
		}
		r.appendChild(p);
	};

	that.userOut = function(msg, opts) {
		let r = idHelper(opts.user);

		if (!Array.isArray(msg)) {
			return;
		}

		msg.forEach(function(u) {
			var p = document.createElement('p');
			p.style.wordWrap = 'break-word';
			p.className = 'username';
			p.appendChild(document.createTextNode(u.username));
			p.addEventListener("click", function() {
				idHelper(opts.to).value = u.username;

				if (chooseUsername != u.username) {
					idHelper(opts.response).innerHTML = "";
					that.sendUser(u.username, stompClient, "/app" + SECURED_CHAT_USER);
					chooseUsername = u.username;

					var elements = document.getElementsByClassName('username');
					for (var i = 0; i < elements.length; i++) {
						var element = elements[i];
						element.classList.remove("userChosed");
					}
					this.classList.add("userChosed");
				}

			});
			r.appendChild(p);
		});

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