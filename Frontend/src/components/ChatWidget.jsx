import React, { useState, useRef, useEffect } from "react";
import axios from "axios";
export default function ChatWidget() {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState([
    {
      sender: "ai",
      text: "👋 Hi! I'm ShopAssist. How can I help you today? You can ask me to track an order, cancel an order, or change your shipping address!",
    },
  ]);
  const [input, setInput] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  // Generate a persistent session ID for the user's browser session
  const [sessionId] = useState(() => {
    let id = localStorage.getItem("chat_session_id");
    if (!id) {
      id = "user_" + Math.random().toString(36).substring(2, 9);
      localStorage.setItem("chat_session_id", id);
    }
    return id;
  });
  const messagesEndRef = useRef(null);
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };
  useEffect(() => {
    scrollToBottom();
  }, [messages, isLoading]);
  const handleSend = async (textToSend) => {
    const message = textToSend || input;
    if (!message.trim() || isLoading) return;
    // Add user message
    setMessages((prev) => [...prev, { sender: "user", text: message }]);
    setInput("");
    setIsLoading(true);
    const aiBaseUrl = (import.meta.env.VITE_AI_SERVICE_URL || "http://localhost:8000").replace(/\/$/, "");
    try {
      const res = await axios.post(`${aiBaseUrl}/api/chat`, {
        message: message,
        session_id: sessionId,
      });
      setMessages((prev) => [
        ...prev,
        { sender: "ai", text: res.data.response },
      ]);
    } catch (err) {
      setMessages((prev) => [
        ...prev,
        {
          sender: "ai",
          text: "⚠️ Sorry, I'm having trouble connecting to the store server. Please check if AI-Service is running.",
        },
      ]);
    } finally {
      setIsLoading(false);
    }
  };
  return (
    <div className="fixed bottom-6 right-6 z-50 font-primary">
      {/* Floating Toggle Button */}
      {!isOpen && (
        <button
          onClick={() => setIsOpen(true)}
          className="bg-primary hover:bg-dark text-white rounded-full p-4 shadow-xl flex items-center justify-center transition-all duration-300 transform hover:scale-105"
          title="Open ShopAssist AI"
        >
          <svg
            className="w-7 h-7"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z"
            />
          </svg>
        </button>
      )}
      {/* Chat Drawer */}
      {isOpen && (
        <div className="bg-white rounded-2xl shadow-2xl w-96 max-w-[90vw] h-[520px] flex flex-col border border-gray-200 overflow-hidden animate-in fade-in slide-in-from-bottom-6">
          {/* Header */}
          <div className="bg-primary text-white px-5 py-4 flex items-center justify-between shadow-sm">
            <div className="flex items-center space-x-2">
              <div className="w-3 h-3 bg-green-400 rounded-full animate-pulse"></div>
              <div>
                <h3 className="font-bold text-base leading-tight">ShopAssist AI</h3>
                <p className="text-xs text-gray-200">EazyStore Smart Copilot</p>
              </div>
            </div>
            <button
              onClick={() => setIsOpen(false)}
              className="text-white hover:text-gray-200 text-2xl font-bold leading-none"
            >
              &times;
            </button>
          </div>
          {/* Quick Action Pills */}
          <div className="bg-gray-50 px-3 py-2 border-b border-gray-200 flex gap-2 overflow-x-auto text-xs whitespace-nowrap">
            <button
              onClick={() => handleSend("Where is my order #1?")}
              className="bg-white border border-gray-300 hover:border-primary text-gray-700 px-2.5 py-1 rounded-full transition"
            >
              📦 Track Order #1
            </button>
            <button
              onClick={() => handleSend("I want to cancel order #1")}
              className="bg-white border border-gray-300 hover:border-red-500 text-gray-700 px-2.5 py-1 rounded-full transition"
            >
              ❌ Cancel Order #1
            </button>
          </div>
          {/* Messages Container */}
          <div className="flex-1 p-4 overflow-y-auto space-y-3 bg-gray-50 text-sm">
            {messages.map((m, idx) => (
              <div
                key={idx}
                className={`flex ${
                  m.sender === "user" ? "justify-end" : "justify-start"
                }`}
              >
                <div
                  className={`max-w-[82%] px-4 py-2.5 rounded-2xl whitespace-pre-wrap leading-relaxed ${
                    m.sender === "user"
                      ? "bg-primary text-white rounded-br-none shadow-sm"
                      : "bg-white text-gray-800 border border-gray-200 rounded-bl-none shadow-sm"
                  }`}
                >
                  {m.text}
                </div>
              </div>
            ))}
            {isLoading && (
              <div className="flex justify-start">
                <div className="bg-white border border-gray-200 text-gray-500 px-4 py-2 rounded-2xl rounded-bl-none flex items-center space-x-1.5 shadow-sm">
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                  <div
                    className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
                    style={{ animationDelay: "0.2s" }}
                  ></div>
                  <div
                    className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
                    style={{ animationDelay: "0.4s" }}
                  ></div>
                </div>
              </div>
            )}
            <div ref={messagesEndRef} />
          </div>
          {/* Input Box */}
          <form
            onSubmit={(e) => {
              e.preventDefault();
              handleSend();
            }}
            className="p-3 bg-white border-t border-gray-200 flex items-center gap-2"
          >
            <input
              type="text"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              placeholder="Ask about orders, returns, stickers..."
              className="flex-1 border border-gray-300 rounded-full px-4 py-2 text-sm focus:outline-none focus:border-primary"
            />
            <button
              type="submit"
              disabled={isLoading || !input.trim()}
              className="bg-primary hover:bg-dark disabled:opacity-50 text-white rounded-full p-2.5 transition"
            >
              <svg
                className="w-4 h-4 transform rotate-90"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z" />
              </svg>
            </button>
          </form>
        </div>
      )}
    </div>
  );
}