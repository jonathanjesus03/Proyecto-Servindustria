import React from "react";
import iconChatbot from "../../assets/Images/icons/filled/icon_chatbot.svg";
type Props = {};

const ChatBot = (props: Props) => {
  return (
    <div className="container">
      <div className="chatbot-popup w-[420px] overflow-hidden bg-[#fff] rounded-[15px] shadow-[0_0_128px_0_rgba(0,0,0,0.1),0_32px_64px_-48px_rgba(0,0,0,0.5)]">
        {/*CHATBOT HEADER*/}
        <div className="chat-header flex px-[15px] py-[22px] justify-between bg-[#6D4FC2]">
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
          <button className="material-symbols-rounded h-[40px] w-[40px] border-none outline-none font-[#fff] cursor-pointer text-[1.9rem] pt-[2px] mr-[-10px] bg-none rounded-[50%]">
            keyboard_arrow_down
          </button>
        </div>

        {/*CHATBOT BODY*/}
        <div className="chat-body">
          <div className="message bot-message">
            <img src={iconChatbot} alt="icon-Chatbot" />
            <p>
              ¡Hola! Soy ChatServis 👏 <br /> ¿En qué puedo ayudarte hoy?
            </p>
          </div>
          <div className="message user-message">
            <p className="meesage-text">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Sed.
            </p>
          </div>
        </div>

        {/*CHATBOT FOOTER*/}
        <div className="chat-footer">
          <form action="#" className="chat-form">
            <input
              type="text"
              placeholder="Mensaje..."
              className="message-input"
              required
            />
            <button type="submit" className="material-symbols-rounded">
              arrow_upward
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
export default ChatBot;
