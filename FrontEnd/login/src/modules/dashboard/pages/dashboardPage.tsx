import Navbar from "../components/navbar";
import Button2 from "../components/button2";
import Size from "../styles/style";
import shield_icon from "../../../assets/Images/Icons/outline/shield_icon.png";
import three_lines_logo from "../../../assets/Images/logos/three_lines.png";
import circle_danger_logo from "../../../assets/Images/logos/circle_danger.png";

type Props = {};

const dashboardPage = ({}: Props) => {
  return (
    <div className="relative min-h-[100vh] min-w-[100vh]">
      <div className="relative z-20">
        <Navbar></Navbar>
      </div>

      <section className="flex w-full h-full relative">
        <div className="h-[80vh] w-2/3 flex flex-col gap-8 items-start justify-center px-[100px]">
          <div className="flex flex-col">
            <h1
              className={`${Size.LARGE_MUL} font-light text-[#FFFFFF] leading-[48px] cursor-default`}
            >
              Somos expertos en soluciones
            </h1>
            <h1
              className={`${Size.LARGE_MUL} font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent cursor-default`}
            >
              industriales innovadoras.
            </h1>
          </div>
          <p
            className={`${Size.MEDIUM_MUL} w-[680px] font-light text-[#A1B0BF] cursor-default text-left`}
          >
            En Servindustria, nos especializamos en brindar soluciones
            integrales en seguridad industrial. Ofrecemos una amplia gama de
            productos y servicios diseñados para garantizar la protección y el
            bienestar en el entorno laboral.
          </p>
          <div className="flex w-full gap-16 justify-start">
            <Button2
              id="soli_cotizac  ion"
              color="bg-gradient-to-r from-[#FF6074] to-[#FE3051]"
              type="button"
              value="Solicite su Cotización"
            ></Button2>
            <Button2
              id="soli_cotizacion"
              color="bg-[#222222]"
              type="button"
              value="Ver Nuestros Servicios"
            ></Button2>
          </div>
        </div>
      </section>

      <section className="flex w-full">
        <div className="flex gap-10 pl-[100px]">
          <div className="flex gap-3">
            <img
              className="max-w-[30px] h-[30px]"
              src={shield_icon}
              alt="icon"
            />
            <p
              className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-default`}
            >
              Venta y mantenimiento de extintores
            </p>
          </div>
          <div className="flex gap-3">
            <img
              className="max-w-[30px] h-[30px]"
              src={shield_icon}
              alt="icon"
            />
            <p
              className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-default`}
            >
              Venta y mantenimiento de extintores
            </p>
          </div>
          <div className="flex gap-3">
            <img
              className="max-w-[30px] h-[30px]"
              src={shield_icon}
              alt="icon"
            />
            <p
              className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-default`}
            >
              Venta y mantenimiento de extintores
            </p>
          </div>
        </div>
      </section>
      <img
        className="absolute top-0 right-0 w-[684px] h-[661px] z-10"
        src={circle_danger_logo}
        alt="logo"
      />
      <img
        className="absolute w-[220px] h-[150px] top-12 right-[31%] z-10"
        src={three_lines_logo}
        alt="logo"
      />
    </div>
  );
};

export default dashboardPage;
