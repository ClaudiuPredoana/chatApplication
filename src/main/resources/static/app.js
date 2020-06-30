var stompClient = null;
var socket = null;
var shortName = "";


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#chatMessages").html("");
}

function connect() {
    // create the SockJS WebSocket-like object
	socket = new SockJS('/stomp-chat');

	// specify that we're using the STOMP protocol on the socket
    stompClient = Stomp.over(socket);

    // implement the behavior we want whenever the client connects to the server (-or- user connects to chat app client by joining a group)
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // subscribe to topic and create the callback function that handles updates from the server
        stompClient.subscribe("/topic/guestnames", function (greeting) {
            showJoinedName(JSON.parse(greeting.body).content);
        });

        stompClient.subscribe("/topic/guestchats", function (greeting) {
            showMessage(JSON.parse(greeting.body).content);
        });
        sendName();
    });

}

function disconnect() {
    if (stompClient !== null) {
    	$("#members").append("<tr><td>" + shortName + " just left</td></tr>");
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/guestjoin", {}, JSON.stringify({'message': $("#shortName").val()}));
}

function showJoinedName(message) {
	shortName = message;
    $("#members").append("<tr><td>" + message + " just joined</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#connect" ).click(function() { connect(); });

    $( "#disconnect" ).click(function() { disconnect(); });

});
