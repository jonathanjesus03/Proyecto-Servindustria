import { useState } from "react";
import iconChatbot from "../../assets/Images/icons/filled/icon_chatbot.svg";
import ChatForm from "./ChatForm";

const ChatBot = () => {
  const [chatHistory, setChatHistory] = useState<
    { rol: String; message: String }[]
  >([]);

  const generateBotResponse = (history: { rol: String; message: String }[]) => {
    console.log(history);
  };
  return (
    <div className="container">
      <div className="chatbot-popup relative w-[100%] overflow-hidden bg-[#fff] rounded-[15px] shadow-[0_0_128px_0_rgba(0,0,0,0.1),0_32px_64px_-48px_rgba(0,0,0,0.5)]">
        {/*CHATBOT HEADER*/}
        <div className="chat-header flex px-[15px] py-[22px] justify-between bg-[rgb(255,138,138)]">
          <div className="header-info flex gap-[10px] items-center">
            <img
              className="h-[35px] w-[35px] p-[6px] flex-shrink-0 bg-white rounded-[50%]"
              src={iconChatbot}
              alt="Chatbot Icon"
            />
            <h2 className="font-[#fff] text-[1.31rem] font-[600px]">
              ChatServis
            </h2>
          </div>
          <button className="material-symbols-rounded h-[40px] w-[40px] border-none outline-none font-[#fff] cursor-pointer text-[1.9rem] pt-[2px] mr-[-10px] bg-none hover:bg-[#ff4b4b] rounded-[50%] transition-ease-dugation-300">
            keyboard_arrow_down
          </button>
        </div>

        {/*CHATBOT BODY*/}
        <div className="chat-body flex flex-col gap-[20px] h-[350px] overflow-y-auto scrollbar-thin scrollbar-thumb-[#DDD3F9] px-[25px] py-[22px] mb-[82px]">
          <div className="message bot-message flex gap-[11px] items-center">
            <img
              src={iconChatbot}
              alt="icon-Chatbot"
              className="h-[73px] w-[73px] p-3 flex-shrink-0 self-end mb-[2px] rounded-[50%] bg-gradient-to-bl from-[#fccfcf] to-[#f77f7f]"
            />
            <p className="message-text bg-[#e9e9e9] px-[12px] py-[16px] max-w-[75%] rounded-r-[13px] rounded-tl-[13px] word-break whitespace-pre-line text-[0.95rem]">
              ¡Hola! Soy ChatServis 👏 <br /> ¿En qué puedo ayudarte hoy?
            </p>
          </div>

          {/* CHAT HISTORY */}
          {chatHistory.map((chat, index) => (
            <div
              key={index}
              className={`message ${
                chat.rol === "model"
                  ? "bot-message flex gap-[11px] items-center"
                  : "user-message flex gap-[11px] flex-col items-end"
              }`}
            >
              {chat.rol === "model" && (
                <img
                  src={iconChatbot}
                  alt="icon-Chatbot"
                  className="h-[73px] w-[73px] p-3 flex-shrink-0 self-end mb-[2px] rounded-[50%] bg-gradient-to-bl from-[#fccfcf] to-[#f77f7f]"
                />
              )}
              <p
                className={`${
                  chat.rol === "model"
                    ? "bg-[#e9e9e9] rounded-r-[13px] rounded-tl-[13px] word-break whitespace-pre-line text-[0.95rem]"
                    : "text-white bg-gradient-to-bl from-[#fccfcf] to-[#f77f7f] rounded-l-[13px] rounded-tr-[13px]"
                } message-text px-[12px] py-[16px] max-w-[75%]`}
              >
                {chat.message}
              </p>
            </div>
          ))}
        </div>

        {/*CHATBOT FOOTER*/}
        <div className="chat-footer absolute bottom-0 w-[100%] bg-[#fff] pt-[15px] px-[22px] pb-[20px]">
          <ChatForm
            chatHistory={chatHistory}
            generateBotResponse={generateBotResponse}
            setChatHistory={setChatHistory}
          ></ChatForm>
        </div>
      </div>
    </div>
  );
};
export default ChatBot;
