import { useEffect, useRef, useState } from "react";
import iconChatbot from "../../assets/Images/icons/filled/icon_chatbot.svg";
import ChatForm from "./ChatForm";
import { companyInfo } from "./companyInfo";

const ChatBot = () => {
  const [chatHistory, setChatHistory] = useState<
    { hideInChat?: boolean; role: string; text: string; isError?: boolean }[]
  >([{ hideInChat: true, role: "model", text: companyInfo }]);

  const chatBodyRef = useRef(null as HTMLDivElement | null);

  // Generate Response from the API bot
  const generateBotResponse = async (
    history: { role: string; text: string }[]
  ) => {
    //Formatting the chat history to match the API request structure
    const historyRequest = history.map(({ role, text }) => ({
      role,
      parts: [{ text }],
    }));

    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        contents: historyRequest,
        generationConfig: { thinkingConfig: { thinkingBudget: 0 } },
      }),
    };

    // Update the chat history with the bot's response
    const updateHistory = (text: string, isError = false) => {
      setChatHistory((prev) => [
        ...prev.filter((msg) => msg.text !== "Analizando ...."),
        { role: "model", text, isError },
      ]);
    };

    // Fetching the API response
    try {
      const start = Date.now();
      const response = await fetch(
        import.meta.env.VITE_API_URL,
        requestOptions
      );
      console.log("API response time:", Date.now() - start, "ms");
      if (!response.ok) throw new Error("Conección con API GEMINIS fallida");
      const data = await response.json();
      const apiResponseText = data.candidates[0].content.parts[0].text
        .replace(/\*\*(.*?)\*\*/g, "$1")
        .trim();
      updateHistory(apiResponseText);
    } catch (error) {
      updateHistory(
        error instanceof Error ? error.message : "Ocurrió un error inesperado",
        true
      );
    }
  };

  // Scroll to the bottom of the chat body when chat history changes
  useEffect(() => {
    setTimeout(() => {
      chatBodyRef.current?.scrollTo({
        top: chatBodyRef.current.scrollHeight,
        behavior: "smooth",
      });
    }, 0);
  }, [chatHistory]);

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
        <div
          ref={chatBodyRef}
          className="chat-body flex flex-col gap-[20px] h-[350px] overflow-y-auto scrollbar-thin scrollbar-thumb-[#DDD3F9] scroll-smooth px-[25px] py-[22px] mb-[82px]"
        >
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
          {chatHistory.map(
            (chat, index) =>
              !chat.hideInChat && (
                <div
                  key={index}
                  className={`message ${
                    chat.role === "model"
                      ? "bot-message flex gap-[11px] items-center"
                      : "user-message flex gap-[11px] flex-col items-end"
                  }`}
                >
                  {chat.role === "model" && (
                    <img
                      src={iconChatbot}
                      alt="icon-Chatbot"
                      className="h-[73px] w-[73px] p-3 flex-shrink-0 self-end mb-[2px] rounded-[50%] bg-gradient-to-bl from-[#fccfcf] to-[#f77f7f]"
                    />
                  )}
                  <p
                    className={`${
                      chat.role === "model"
                        ? "bg-[#e9e9e9] rounded-r-[13px] rounded-tl-[13px] break-words whitespace-pre-line text-[0.95rem]"
                        : "text-white bg-gradient-to-bl from-[#fccfcf] to-[#f77f7f] rounded-l-[13px] rounded-tr-[13px]"
                    } 
                ${chat.isError ? "text-[#ff0000]" : ""}
                message-text px-[12px] py-[16px] max-w-[75%]`}
                  >
                    {chat.text}
                  </p>
                </div>
              )
          )}
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
