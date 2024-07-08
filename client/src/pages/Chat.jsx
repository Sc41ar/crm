"use client";
import Button from "../components/Button";
import Input from "../components/Input";
import { React, useState, useEffect } from "react";

let socket;
const crypto = require("crypto");
const secretKey = crypto.randomBytes(32);

function connectToChat(chatName) {
  // Получаем имя пользователя из cookie
  const cookies = document.cookie.split(";");
  let name = "";
  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i].trim();
    if (cookie.startsWith("name=")) {
      name = cookie.substring("name=".length);
      break;
    }
  }

  console.log(name);

  if (!socket || socket.readyState === WebSocket.CLOSED) {
    socket = new WebSocket(
      `ws://127.0.0.1:8787/ws/joinChat/${encodeURIComponent(
        chatName
      )}?username=${encodeURIComponent(name)}`
    );
  }

  // Обрабатываем события WebSocket
  socket.onopen = () => {
    console.log("Connected to chat");
  };

  // Получения сообщения из вебсокета и вывод его
  socket.onmessage = function (event) {
    const messageData = JSON.parse(event.data);
    const messageContainer = document.createElement("div");
    messageContainer.className =
      "flex justify-end" +
      (messageData.sender_username !== name ? " flex-row-reverse" : "");
    console.log(messageData.sender_username);

    const messageElement = document.createElement("div");
    messageElement.className =
      "max-w-[75%] rounded-lg bg-white p-3 text-sm shadow-sm dark:bg-gray-900 dark:text-gray-50" +
      (messageData.sender_username !== name
        ? " bg-gray-200 dark:bg-gray-700"
        : "");

    const messageUsername = document.createElement("p");
    messageUsername.className = "font-medium";
    messageUsername.style.fontFamily = "Roboto";
    messageUsername.textContent = messageData.sender_username;

    const messageText = document.createElement("p");
    messageText.textContent = messageData.text;

    const messageTime = document.createElement("div");
    messageTime.className = "mt-2 text-xs text-gray-500 dark:text-gray-400";

    const timestampWithOffset =
      messageData.timestamp * 1000 - new Date().getTimezoneOffset() * 60000;
    const timeValue = new Date(timestampWithOffset);

    if (isNaN(timeValue.getTime())) {
      messageTime.textContent = "Invalid time value";
    } else {
      messageTime.textContent = timeValue
        .toISOString()
        .slice(0, 19)
        .replace("T", " ");
    }

    messageElement.appendChild(messageUsername);
    messageElement.appendChild(messageText);
    messageElement.appendChild(messageTime);
    messageContainer.appendChild(messageElement);
    document
      .querySelector(".flex-1.overflow-auto.p-4")
      .appendChild(messageContainer);
  };
  socket.onclose = () => {
    console.log("Disconnected from chat");
  };
}

export default function Component() {
  const [messageText, setMessageText] = useState("");
  const handleInputChange = (event) => {
    setMessageText(event.target.value);
  };
  const handleSendMessage = () => {
    if (socket) {
      if (messageText !== "") {
        console.log(messageText);
        // const encryptedMessage = encryptMessage(messageText, secretKey);
        // console.warn(encryptedMessage);
        socket.send(messageText);
        setMessageText("");
      }
    }
  };

  useEffect(() => {
    const cookieValue = Cookies.get("jwt");
    if (cookieValue) {
      const chatname = Cookies.get("chatname");
      if (chatname) {
        connectToChat(chatname);
      }
    } else {
      console.log("net");
      window.location.href = "/login";
    }
  }, []);

  return (
    <div className="flex h-screen w-full flex-col">
      <header className="flex h-14 items-center justify-between border-b bg-gray-100 px-4 dark:border-gray-800 dark:bg-gray-950">
        <div className="text-lg font-medium">Chat</div>
        <div className="flex items-center gap-3">
          <Button className="h-8 w-8" size="icon" variant="ghost">
            <SearchIcon className="h-4 w-4" />
          </Button>
          <Button className="h-8 w-8" size="icon" variant="ghost">
            <MoveVerticalIcon className="h-4 w-4" />
          </Button>
        </div>
      </header>
      <div className="flex-1 overflow-auto p-4">
        <div className="grid gap-4">
          <div className="flex justify-end"></div>
          <div className="flex"></div>
          <div className="flex justify-end"></div>
        </div>
      </div>
      <div className="flex items-center border-t bg-gray-100 px-4 py-2 dark:border-gray-800 dark:bg-gray-950">
        <Input
          className="flex-1 bg-transparent focus:outline-none"
          placeholder="Type your message..."
          type="text"
          value={messageText}
          onChange={handleInputChange}
        />
        <Button
          className="h8 w-8"
          size="icon"
          variant="ghost"
          onClick={handleSendMessage}
        >
          <SendIcon className="h-4 w-4" />
        </Button>
      </div>
    </div>
  );
}

function MoveVerticalIcon(props) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <polyline points="8 18 12 22 16 18" />
      <polyline points="8 6 12 2 16 6" />
      <line x1="12" x2="12" y1="2" y2="22" />
    </svg>
  );
}

function SearchIcon(props) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <circle cx="11" cy="11" r="8" />
      <path d="m21 21-4.3-4.3" />
    </svg>
  );
}

function SendIcon(props) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="m22 2-7 20-4-9-9-4Z" />
      <path d="M22 2 11 13" />
    </svg>
  );
}
