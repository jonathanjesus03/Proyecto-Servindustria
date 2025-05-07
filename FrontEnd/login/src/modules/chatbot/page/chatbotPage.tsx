import Size from "../style/style";
import Chatbot from "../components/chatbot";
import icon_chechMark from "../../../assets/Images/Icons/outline/checkmark-circle.png";
type Props = {};

const chatbotPage = ({}: Props) => {
  return (
    <div className="min-h-[100vh] min-w-[100vh] p-5">
      <section className="flex flex-col w-full items-center">
        <div className="flex md:flex-row flex-col md:gap-4 text-center">
          <h1
            className={`${Size.EXTRA_LARGE_MUL} font-light text-[#FFFFFF] cursor-default `}
          >
            Solicita de más información con
          </h1>
          <h1
            className={`${Size.EXTRA_LARGE_MUL} font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent cursor-default`}
          >
            ChatServis
          </h1>
        </div>
        <p
          className={`max-w-[700px] ${Size.MEDIUM_MUL} font-light text-[#A1B0BF] text-center`}
        >
          En Servindustria, ofrecemos soluciones personalizadas en seguridad
          industrial, adaptadas a las necesidades de cada empresa. Brindamos
          productos y servicios de alta calidad para garantizar un entorno
          seguro y protegido.
        </p>
      </section>
      <section className="w-full h-full flex justify-center space-x-10 pt-2">
        <div className="flex flex-col justify-center gap-10">
          <div className="flex gap-4 items-center justify-center">
            <img className="w-[80] h-[65px]" src={icon_chechMark} alt="icon" />
            <p
              className={`md:w-[365px] ${Size.MEDIUM_MUL} font-light text-[#FFFFFF]`}
            >
              Responde consultas frecuentes de clientes, vendedores y
              administradores en cualquier momento del día, sin necesidad de
              intervención humana.
            </p>
          </div>
          <div className="flex gap-4 items-center justify-center">
            <img className="w-[80] h-[65px]" src={icon_chechMark} alt="icon" />
            <p
              className={`md:w-[365px] ${Size.MEDIUM_MUL} font-light text-[#FFFFFF]`}
            >
              Analiza el comportamiento y preferencias del usuario para ofrecer
              respuestas más precisas y sugerencias útiles con el tiempo.
            </p>
          </div>
          <div className="flex gap-4 items-center justify-center">
            <img className="w-[80] h-[65px]" src={icon_chechMark} alt="icon" />
            <p
              className={`md:w-[365px] ${Size.MEDIUM_MUL} font-light text-[#FFFFFF]`}
            >
              Interactúa de forma segura según el rol del usuario (cliente,
              vendedor o administrador), garantizando que solo se acceda a
              información permitida.
            </p>
          </div>
        </div>
        <Chatbot></Chatbot>
      </section>
    </div>
  );
};

export default chatbotPage;
