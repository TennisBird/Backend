const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#conversation2").show();
    }
    else {
        $("#conversation").hide();
        $("#conversation2").hide();
    }
    $("#greetings").html("");
    $("#messegings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

 function subscribeToChats(chatId) {
        stompClient.subscribe(`/topic/chats/`+chatId, function (message) {
            showMessage(message);
        });
    }

 function showMessage(message) {
        const parsedMessage = JSON.parse(message.body);
        $("#messegings").append("<tr><td>" + parsedMessage.content + "</td></tr>");
 }

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
    // Обработчик для формы присоединения к чату
            $("#joinChatForm").on('submit', function (e) {
                e.preventDefault();
                const chatId = $("#chatId").val();
                console.log("Joined chat: " + chatId);
                subscribeToChats(chatId); // Подписываемся на чат
            });

            // Обработчик для формы отправки сообщений
            $("#sendMessageForm").on('submit', function (e) {
                 e.preventDefault();
                 const chatId = $("#chatId").val();
                 const messageContent = $("#messageContent").val();

                 const message = {
                      content: messageContent
                 };
                 stompClient.publish({
                      destination: "/app/chat/"+chatId,
                      body: JSON.stringify(message)
                 });
                 $("#messageContent").val(""); // Очищаем поле ввода
             });
});