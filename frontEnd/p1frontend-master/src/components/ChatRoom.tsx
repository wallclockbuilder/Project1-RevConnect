import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import config from '../config';
import { useAuth } from '../context/AuthContext';
import { Chat } from '../interface/types';
import '../css/chatroom.css';

const ChatRoom = () => {
    const { userId } = useParams<{ userId: string }>();
    const [messages, setMessages] = useState<Chat[]>([]);
    const [newMessage, setNewMessage] = useState('');
    const { user, token } = useAuth();

    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await fetch(`${config.BASE_URL}/api/chats/users/${user?.userId}/${userId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                    credentials: 'include',
                });
                if (response.ok) {
                    const data = await response.json();
                    setMessages(data);
                } else {
                    console.error('Failed to fetch messages');
                }
            } catch (error) {
                console.error('Error fetching messages:', error);
            }
        };

        if (user) {
            fetchMessages();
        }
    }, [user, userId, token]);

    const handleSendMessage = async () => {
        try {
            const response = await fetch(`${config.BASE_URL}/api/chats`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    message: newMessage,
                    sender: { userId: user?.userId },
                    receiver: { userId: Number(userId) },
                }),
            });

            if (response.ok) {
                const sentMessage = await response.json();
                setMessages((prevMessages) => [...prevMessages, sentMessage]);
                setNewMessage('');
            } else {
                console.error('Failed to send message');
            }
        } catch (error) {
            console.error('Error sending message:', error);
        }
    };

    return (
        <div className="chat-room">
            <h1>Chat Room</h1>
            <div className="chat-messages">
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.senderId === user?.userId ? 'sent' : 'received'}`}
                    >
                        <p className="username">
                            {msg.senderId === user?.userId ? user?.username : msg.senderUsername}
                        </p>
                        <p className="text">{msg.message}</p>
                    </div>
                ))}
            </div>
            <div className="message-input">
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    placeholder="Type your message..."
                />
                <button onClick={handleSendMessage}>Send</button>
            </div>
        </div>
    );
};

export default ChatRoom;
