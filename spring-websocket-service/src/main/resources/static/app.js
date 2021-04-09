var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
}

function connect() {
	ws = new WebSocket('wss://dadmin.sphor.us/chat');
	ws.onmessage = function(data) {
		response(data.data);
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

function response(message) {
    var data = JSON.parse(message);
	$("#response").append("<tr><td> " + data.userId + "</td></tr>");
	$("#response").append("<tr><td> " + data.node + "</td></tr>");
	$("#response").append("<tr><td> " + data.options + "</td></tr>");
	$("#response").append("<tr><td> " + data.error + "</td></tr>");
	$("#response").append("<tr><td> </td></tr>");
	$("#userId").val(data.userId);
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
