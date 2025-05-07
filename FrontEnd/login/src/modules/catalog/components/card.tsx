import calendar_icon from "../../../assets/Images/Icons/outline/calendar-outline.svg";
import Button2 from "./button3";
import Size from "../styles/style";
type Props = {
  date: string;
  nameProduct: string;
  img: string;
};

const card = ({ date, nameProduct, img }: Props) => {
  return (
    <div className="Content_card">
      <div className="md:w-[380px] md:h-[460px] rounded-[25px] overflow-hidden">
        <div>
          <img
            className="bg-cover bg-center bg-no-repeat"
            src={img}
            alt="product1"
          />
        </div>
        <div className="flex flex-col gap-2 p-5 bg-[#0f0f0f] rounded-b-[25px]">
          <div className="flex gap-2">
            <img src={calendar_icon} alt="icon" />
            <span
              className={`${Size.EXTRA_SMALL_MUL} font-normal text-[#A1B0BF] leading-7`}
            >
              {date}
            </span>
          </div>
          <h1 className={`${Size.LARGE_MUL} text-[#FFFFFF]`}>{nameProduct}</h1>
          <p className={`${Size.EXTRA_SMALL_MUL} font-normal text-[#A1B0BF]`}>
            Publicado por Priscilla Arboleda
          </p>
          <div className="flex justify-center md:gap-3 gap-1">
            <Button2
              id="buton_card"
              type="button"
              color="bg-gradient-to-b from-[#FF6074] to-[#FE3051]"
              value="Llévatelo"
            ></Button2>
            <Button2
              id="buton_card"
              type="button"
              color="bg-[#222222]"
              value="Ver Detalles"
            ></Button2>
          </div>
        </div>
      </div>
    </div>
  );
};

export default card;
