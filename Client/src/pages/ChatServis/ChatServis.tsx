import "../../styles/styleChatServis.css";
import Navbar from "../../components/navbar/Navbar";
import icon_chechMark from "../../assets/Images/Icons/outline/checkmark-circle.png";
import ChatBot from "./ChatBot";

type Props = {};

const chatbotPage = ({}: Props) => {
  return (
    <div id="chatServis" className="page">
      <Navbar />
      <div className="min-h-screen py-4 px-4 sm:px-6 lg:px-12 space-y-6">
        {/* Sección de título */}
        <section className="flex flex-col items-center text-center space-y-3">
          <div className="flex flex-col md:flex-row md:gap-3 items-center justify-center">
            <h1 className="EXTRA_LARGE_MUL font-semibold text-[#8291a3]">
              Solicita de más información con
            </h1>
            <h1 className="EXTRA_LARGE_MUL font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
              ChatServis
            </h1>
          </div>
          <p className="max-w-3xl MEDIUM_MUL font-light text-[#8291a3] text-center">
            Bienvenido a Servindustria. Te ofrecemos soluciones en seguridad
            industrial, adaptadas a tu empresa. ¿Tienes dudas o necesitas ayuda?
            Nuestro chat está listo para asistirte.
          </p>
        </section>

        {/* Sección principal */}
        <section className="flex flex-col lg:flex-row justify-center items-center gap-10">
          {/* Ventajas */}
          <div className="flex flex-col gap-8 max-w-md w-full">
            {[
              "Responde consultas frecuentes de clientes, vendedores y administradores en cualquier momento del día, sin necesidad de intervención humana.",
              "Analiza el comportamiento y preferencias del usuario para ofrecer respuestas más precisas y sugerencias útiles con el tiempo.",
              "Interactúa de forma segura según el rol del usuario (cliente, vendedor o administrador), garantizando que solo se acceda a información permitida.",
            ].map((text, index) => (
              <div
                key={index}
                className="flex gap-4 items-start sm:items-center justify-start"
              >
                <img
                  className="w-10 h-10 sm:w-12 sm:h-12"
                  src={icon_chechMark}
                  alt="icon"
                />
                <p className="MEDIUM_MUL font-light text-[#111111]">{text}</p>
              </div>
            ))}
          </div>

          {/* Chatbox */}
          <div className="w-full max-w-2xl">
            <div className="flex justify-center items-center w-full bg-gradient-to-b from-[#ffcdcd] to-[#fe7575] rounded-[25px] p-6">
              <ChatBot></ChatBot>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};

export default chatbotPage;
