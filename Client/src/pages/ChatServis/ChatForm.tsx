import { useRef, useState } from "react";
type props = {
  setChatHistory: React.Dispatch<
    React.SetStateAction<{ role: string; text: string }[]>
  >;
  chatHistory: { role: string; text: string }[];
  generateBotResponse: (history: { role: string; text: string }[]) => void;
};

const ChatForm = ({
  chatHistory,
  generateBotResponse,
  setChatHistory,
}: props) => {
  const inputRef = useRef(null as HTMLInputElement | null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (inputRef.current === null) return;
    if (!inputRef.current.value.trim() || isLoading) return;

    setIsLoading(true);
    const message = inputRef.current.value.trim();

    setChatHistory((prev) => [...prev, { role: "user", text: message }]);
    inputRef.current.value = "";

    setTimeout(() => {
      setChatHistory((prev) => [
        ...prev,
        {
          role: "model",
          text: "Analizando ....",
        },
      ]);

      generateBotResponse([
        ...chatHistory,
        {
          role: "user",
          text: `Usando los detalles dados anteriormente arriba, por favor resuelve esta consulta como asistente virtual de forma ordenada, legible y dinámica: ${message}`,
        },
      ]);

      setIsLoading(false);
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
