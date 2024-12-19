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
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
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

    function subscribeToChats(chatId, ) {
        // Создаем новый контейнер для этого чата
        const chatContainer = $(`<div class="chat-window" id="chat-${chatId}">
            <h4>Chat ID: ${chatId}</h4>
            <table class="table table-striped">
                <thead><tr><th>Messages</th></tr></thead>
                <tbody id="messages-${chatId}"></tbody>
            </table>
        </div>`);
        $("#chatContainers").append(chatContainer); // Добавляем контейнер в основное окно
        const name = $("#name").val();
        stompClient.subscribe(`/topic/chats/${name}/${chatId}`, function (message) {
              showMessage(chatId, message);
        });
        console.log(`subscribe to /${name}/topic/chats/${chatId}`);
    }

function showMessage(chatId, message) {
    const parsedMessage = JSON.parse(message.body);
    const senderName = parsedMessage.sender.member.username;
    const content = parsedMessage.content;

    // Добавляем имя отправителя и текст сообщения в таблицу
    $(`#messages-${chatId}`).append("<tr><td><strong>" + senderName + ":</strong> " + content + "</td></tr>");
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

            $("#sendMessageForm").on('submit', function (e) {
                e.preventDefault();
                const chatId = $("#chatId").val();
                const name = $("#name").val();
                const messageContent = $("#messageContent").val();

                const message = {
                    content: messageContent,
                    username: name
                };

                stompClient.publish({
                    destination: "/app/chat/" + chatId,
                    body: JSON.stringify(message)
                });
                $("#messageContent").val("");
            });
});