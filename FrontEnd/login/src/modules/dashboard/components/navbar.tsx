import Size from "../styles/style";

type Props = {};

function navbar({}: Props) {
  return (
    <div className="h-11 bg-[#0A0A0A] max-w-full md:h-[48px] px-[100px] pt-4">
      <div className="flex h-full items-center justify-between">
        <p
          className={`${Size.MEDIUM_MUL} font-bold text-[#FFFFFF] cursor-default`}
        >
          Servindustria
        </p>
        <div className="flex gap-16">
          <p
            className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-pointer`}
          >
            Inicio
          </p>
          <p
            className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-pointer`}
          >
            Nosotros
          </p>
          <p
            className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-pointer`}
          >
            Servicios
          </p>
          <p
            className={`${Size.SMALL_MUL} font-normal text-[#A1B0BF] cursor-pointer`}
          >
            Contáctanos
          </p>
        </div>
      </div>
    </div>
  );
}

export default navbar;
