import Navbar from "../components/navbar/Navbar";
import "../styles/styleHome.css";
import shield_icon from "../assets/Images/Icons/outline/shield_icon.png";
import three_lines_logo from "../assets/Images/logos/bars_three_white.png";
import circle_danger_logo from "../assets/Images/logos/circle_danger.png";
import { Button } from "@material-tailwind/react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      <div id="home" className="page relative min-h-screen overflow-hidden">
        <div className="relative z-30">
          <Navbar />
        </div>
        <section className="flex flex-col md:flex-row w-full h-auto md:h-[80vh] relative px-08 md:px-20 py-10">
          <div className="md:w-2/3 flex flex-col gap-10 lg:items-start items-center justify-center">
            <div className="flex flex-col">
              {/*Error con LARGE_MUL*/}

              <h1 className="text-3xl md:text-3xl lg:text-4xl font-semibold text-[#8291a3] leading-snug cursor-default z-20">
                Somos expertos en soluciones
              </h1>
              <h1 className="text-3xl md:text-3xl lg:text-4xl font-semibold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent cursor-default">
                industriales innovadoras.
              </h1>
            </div>
            <p className="MEDIUM_MUL font-light text-[#8291a3] cursor-default text-left max-w-[680px] px-16 sm:py-6 md:py-0 md:px-0">
              En Servindustria, nos especializamos en brindar soluciones
              integrales en seguridad industrial. Ofrecemos una amplia gama de
              productos y servicios diseñados para garantizar la protección y el
              bienestar en el entorno laboral.
            </p>
            <div className="flex flex-col sm:flex-row gap-4">
              <Link to={"/cotizar"}>
                <Button
                  id="buttonQuoted"
                  className="dashButton text-md bg-gradient-to-r from-[#ff7c7c] to-[#ff002b] font-semibold"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  Solicite su Cotización
                </Button>
              </Link>
              <Link to={"/catalogo"}>
                <Button
                  id="buttonService"
                  className="dashButton text-md bg-[#8291a3] font-semibold"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  Ver Nuestros Servicios
                </Button>
              </Link>
            </div>
          </div>
        </section>

        {/* Características */}
        <section className="w-full px-6 md:px-20 py-2">
          <div className="flex justify-center flex-wrap gap-6">
            {[1, 2, 3].map((_, i) => (
              <div
                key={i}
                className="flex justify-center items-center gap-3 w-full md:w-auto"
              >
                <img src={shield_icon} alt="icon" className="w-6 h-6" />
                <p className="SMALL_MUL font-normal text-[#8291a3]">
                  Venta y mantenimiento de extintores
                </p>
              </div>
            ))}
          </div>
        </section>

        {/* Background images */}
        <img
          className="hidden md:block absolute md:top-12 lg:top-8 xl:top-0 right-[0vh] w-[400px] md:w-[500px] md:translate-x-20 lg:w-[580px] xl:translate-x-[-30px]  xl:w-[680px] z-10"
          src={circle_danger_logo}
          alt="logo"
        />
        <img
          className=" absolute w-[150px] md:w-[220px] h-auto top-10 md:top-5 lg:top-10 right-3/4 md:right-1/2 md:translate-x-1/2 z-10"
          src={three_lines_logo}
          alt="logo"
        />
      </div>
    </div>
  );
};

export default Home;
