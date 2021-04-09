var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
}

function connect() {
	ws = new WebSocket('wss://dadmin.sphor.us/chat');
	ws.onmessage = function(data) {
		helloWorld(data.data);
	}
	setConnected(true);
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Websocket is in disconnected state");
}

function sendData() {
	var data = JSON.stringify({
	    'userId' : $("#userId").val(),
		'input' : $("#input").val()
	})
	ws.send(data);
}

function helloWorld(message) {
    var data = JSON.parse(message);
	$("#helloworldmessage").append("<tr><td> " + message + "</td></tr>");
	$("#userId").val = data.userId;
    console.log(data);
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendData();
	});
});
