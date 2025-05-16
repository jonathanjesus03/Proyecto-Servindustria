import "../styles/styleChatServis.css";
import Navbar from "../components/navbar/Navbar";
import icon_chechMark from "../assets/Images/Icons/outline/checkmark-circle.png";
import icon_chatb from "../assets/Images/Icons/filled/icon_chatbot.svg";
import icon_person from "../assets/Images/Icons/outline/person-circle-outline.svg";
import { Button } from "@material-tailwind/react";

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
            En Servindustria, ofrecemos soluciones personalizadas en seguridad
            industrial, adaptadas a las necesidades de cada empresa. Brindamos
            productos y servicios de alta calidad para garantizar un entorno
            seguro y protegido.
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
          <div className="w-full max-w-2xl p-2">
            <div className="w-full bg-gradient-to-b from-[#30353B] to-[#1A1B1F] rounded-[25px]">
              <div className="flex flex-col-reverse h-[400px] sm:h-[460px] justify-start p-5 gap-5 overflow-y-auto">
                <div className="flex items-center gap-3 justify-end text-white font-bold">
                  <img src={icon_person} alt="icon" className="w-11 h-11" />
                  <p className="MEDIUM_MUL md:LARGE_MUL">Mensaje enviado</p>
                </div>
                <div className="flex items-center gap-3 justify-start text-white font-bold">
                  <img src={icon_chatb} alt="icon" className="w-11 h-11" />
                  <p className="MEDIUM_MUL md:LARGE_MUL">
                    Respuesta ChatServis
                  </p>
                </div>
                <div className="flex items-center gap-3 justify-end text-white font-bold">
                  <img src={icon_person} alt="icon" className="w-11 h-11" />
                  <p className="MEDIUM_MUL ">Mensaje enviado</p>
                </div>
                <div className="flex items-center gap-3 justify-start text-white font-bold">
                  <img src={icon_chatb} alt="icon" className="w-11 h-11" />
                  <p className="MEDIUM_MUL">Respuesta ChatServis</p>
                </div>
              </div>
              <div className="flex gap-2 bg-[#0A0A0A] justify-center items-center rounded-b-[25px] p-2">
                <input
                  className="flex-1 h-[60px] bg-transparent placeholder-white text-white font-bold px-3 focus:outline-none"
                  placeholder="Enviar mensaje a ChatServis"
                  type="text"
                />
                <Button
                  type="button"
                  className="chatButton bg-gradient-to-b from-[#FF6074] to-[#FE3051] text-white px-6 py-3 rounded-xl"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  Enviar
                </Button>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};

export default chatbotPage;
