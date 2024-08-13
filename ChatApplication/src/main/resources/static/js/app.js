document.addEventListener('DOMContentLoaded', function () {
    var loginSection = document.getElementById('login');
    var chatroomsSection = document.getElementById('chatrooms');
    var chatSection = document.getElementById('chat');
    var chatBox = document.getElementById('chat-box');
    var messageInput = document.getElementById('message');
    var sendButton = document.getElementById('send');
    var loginButton = document.getElementById('login-button');
    var usernameInput = document.getElementById('username');
    var chatroomList = document.getElementById('chatroom-list');
    var createChatroomButton = document.getElementById('create-chatroom');
    var currentRoomNameElement = document.getElementById('current-room-name');
    var backToChatroomsButton = document.getElementById('back-to-chatrooms');

    var currentRoom = null;
    var username = null;
    var socket = new SockJS('/chat');
    var stompClient = Stomp.over(socket);

    function connect() {
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
        });
    }

    function loadChatrooms() {
        fetch('/api/chatrooms')
            .then(response => response.json())
            .then(data => {
                chatroomList.innerHTML = '';
                data.forEach(room => {
                    var listItem = document.createElement('li');
                    listItem.className = 'list-group-item list-group-item-action';
                    listItem.textContent = room.name;
                    listItem.addEventListener('click', function () {
                        joinChatroom(room.name);
                    });
                    chatroomList.appendChild(listItem);
                });
            });
    }

    function joinChatroom(roomName) {
        if (currentRoom) {
            stompClient.unsubscribe(currentRoom);
        }
        currentRoom = roomName;
        currentRoomNameElement.textContent = roomName;
        chatroomsSection.style.display = 'none';
        chatSection.style.display = 'block';
        chatBox.innerHTML = '';

        stompClient.subscribe('/topic/' + roomName, function (message) {
            var msg = JSON.parse(message.body);
            var messageElement = document.createElement('div');
            messageElement.className = 'message';
            messageElement.textContent = msg.sender + ": " + msg.content;
            chatBox.appendChild(messageElement);
            chatBox.scrollTop = chatBox.scrollHeight; // Scroll to the latest message
        });

        loadMessages(roomName);
    }

    function loadMessages(roomName) {
        fetch(`/api/messages?room=${roomName}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(msg => {
                    var messageElement = document.createElement('div');
                    messageElement.className = 'message';
                    messageElement.textContent = msg.sender + ": " + msg.content;
                    chatBox.appendChild(messageElement);
                });
                chatBox.scrollTop = chatBox.scrollHeight; // Scroll to the latest message
            });
    }

    loginButton.addEventListener('click', function () {
        username = usernameInput.value;
        if (username.trim() === "") {
            alert("Please enter a username");
            return;
        }
        loginSection.style.display = 'none';
        chatroomsSection.style.display = 'block';
        loadChatrooms();
    });

    sendButton.addEventListener('click', function () {
        var messageContent = messageInput.value;
        if (currentRoom && messageContent.trim() !== '') {
            stompClient.send(`/app/send/${currentRoom}`, {}, JSON.stringify({
                content: messageContent,
                sender: username,
                timestamp: new Date().toISOString()
            }));
            messageInput.value = '';
        }
    });

    createChatroomButton.addEventListener('click', function () {
        var roomName = prompt("Enter chatroom name:");
        if (roomName && roomName.trim() !== '') {
            fetch('/api/chatrooms', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: roomName })
            }).then(response => {
                if (response.ok) {
                    loadChatrooms();
                } else {
                    alert("Failed to create chatroom. Please try again.");
                }
            });
        }
    });

    backToChatroomsButton.addEventListener('click', function () {
        chatSection.style.display = 'none';
        chatroomsSection.style.display = 'block';
        loadChatrooms();
    });

    connect();
});
