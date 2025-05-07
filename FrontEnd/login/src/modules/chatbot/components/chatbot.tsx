import Button2 from "../components/button2";
import Size from "../style/style";
import icon_chatb from "../../../assets/Images/Icons/filled/icon_chatbot.svg";
import icon_person from "../../../assets/Images/Icons/outline/person-circle-outline.svg";

type Props = {};

const chatbot = ({}: Props) => {
  return (
    <div className="p-2">
      <div className="md:w-[680px] md:h-[480px] bg-gradient-to-b from-[#30353B] to-[#1A1B1F] rounded-[25px]">
        <div className="flex flex-col-reverse md:w-full md:h-[405px] justify-start p-5 gap-5">
          <div
            className={`flex items-center gap-5 ${Size.MEDIUM_MUL} font-bold text-[#FFFFFF] justify-end`}
          >
            <img src={icon_person} alt="icon" />
            <p>Message Input</p>
          </div>
          <div
            className={`flex items-center gap-5 ${Size.MEDIUM_MUL} font-bold text-[#FFFFFF] justify-start`}
          >
            <img src={icon_chatb} alt="icon" />
            <p>Message Response</p>
          </div>
          <div
            className={`flex items-center gap-5 ${Size.MEDIUM_MUL} font-bold text-[#FFFFFF] justify-end`}
          >
            <img src={icon_person} alt="icon" />
            <p>Message Input</p>
          </div>
          <div
            className={`flex items-center gap-5 ${Size.MEDIUM_MUL} font-bold text-[#FFFFFF] justify-start`}
          >
            <img src={icon_chatb} alt="icon" />
            <p>Message Response</p>
          </div>
        </div>
        <div className="flex gap-1 bg-[#0A0A0A] justify-center items-center rounded-b-[25px]">
          <input
            className={`w-2/3 h-[75px] bg-transparent placeholder:${Size.MEDIUM_MUL} text-[#FFFFFF] placeholder:text-[#FFFFFF] font-bold focus:outline-none`}
            placeholder="Enviar mensaje a ChatServis"
            type="text"
          />
          <Button2
            id="ButtonMessge"
            type="button"
            value="Enviar"
            color="bg-gradient-to-b from-[#FF6074] to-[#FE3051]"
          ></Button2>
        </div>
      </div>
    </div>
  );
};

export default chatbot;
