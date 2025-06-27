import { useRef } from "react";
type props = {
  setChatHistory: React.Dispatch<
    React.SetStateAction<{ rol: String; message: String }[]>
  >;
  chatHistory: { rol: String; message: String }[];
  generateBotResponse: (history: { rol: String; message: String }[]) => void;
};

const ChatForm = ({
  chatHistory,
  generateBotResponse,
  setChatHistory,
}: props) => {
  const inputRef = useRef(null as HTMLInputElement | null);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (inputRef.current === null) return;

    const message = inputRef.current.value.trim();

    setChatHistory((prev) => [...prev, { rol: "user", message: message }]);
    inputRef.current.value = "";

    setTimeout(() => {
      setChatHistory((prev) => [
        ...prev,
        {
          rol: "model",
          message: "...",
        },
      ]);

      generateBotResponse([...chatHistory, { rol: "user", message: message }]);
    }, 600);
  };

  return (
    <form
      action="#"
      className="chat-form flex items-center bg-[#fff] outline-1 outline outline-[#CCCCE5] rounded-[32px] shadow-[0_0_8px_rgba(0,0,0,0.1)] focus-within:outline-2 focus-within:outline-[#f77f7f] "
      onSubmit={handleSubmit}
    >
      <input
        type="text"
        placeholder="Mensaje..."
        className="message-input border-none outline-none bg-none h-[47px] w-[100%] p-[17px] text-[0.95rem] peer"
        required
        ref={inputRef}
      />
      <button
        type="submit"
        className="material-symbols-rounded h-[35px] w-[35px] border-none outline-none cursor-pointer text-[1.5rem] text-[#fff] flex-shrink-0 mr-[6px] rounded-[50%] bg-[#f77f7f] hover:bg-[#fc6262] hidden peer-valid:block transition-all duration-200 ease-out "
      >
        arrow_upward
      </button>
    </form>
  );
};

export default ChatForm;
