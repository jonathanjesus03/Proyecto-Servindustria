import Size from "../styles/style";
import Card from "../components/card";
import product1_img from "../../../assets/Images/img_prudcts/product1.png";
import product2_img from "../../../assets/Images/img_prudcts/product2.png";
import product3_img from "../../../assets/Images/img_prudcts/product3.png";

type Props = {};

function catalogPages({}: Props) {
  return (
    <div className="min-h-[100vh] min-w-[100vh] py-9">
      <section className="flex flex-col w-full items-center">
        <div className="flex gap-4">
          <h1
            className={`${Size.EXTRA_LARGE_MUL} font-light text-[#FFFFFF] cursor-default`}
          >
            Los mejores acticulos
          </h1>
          <h1
            className={`${Size.EXTRA_LARGE_MUL} font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent cursor-default`}
          >
            de la Industria
          </h1>
        </div>
        <p
          className={`max-w-[850px] ${Size.MEDIUM_MUL} font-light text-[#A1B0BF] text-center`}
        >
          Explora contenido relevante sobre innovación, mantenimiento, seguridad
          y eficiencia en maquinaria y procesos industriales. Mantente informado
          y actualizado con los temas que mueven la industria.
        </p>
      </section>
      <section className="w-full min-h-max justify-normal items-center pt-9">
        <div className="flex grid-cols-3 flex-wrap gap-11 items-center justify-center">
          <Card
            date="2 de enero del 2025"
            nameProduct="🔥 Extintores multipropósito ABC"
            img={product1_img}
          ></Card>
          <Card
            date="22 de Enero de 2025"
            nameProduct="📛 Señalética de seguridad industrial"
            img={product2_img}
          ></Card>
          <Card
            date="22 de Diciembre de 2024"
            nameProduct="🧯 Sistema de detección y supresión de incendios"
            img={product3_img}
          ></Card>
          <Card
            date="2 de enero del 2025"
            nameProduct="🔥 Extintores multipropósito ABC"
            img={product1_img}
          ></Card>
          <Card
            date="22 de Enero de 2025"
            nameProduct="📛 Señalética de seguridad industrial"
            img={product2_img}
          ></Card>
          <Card
            date="22 de Diciembre de 2024"
            nameProduct="🧯 Sistema de detección y supresión de incendios"
            img={product3_img}
          ></Card>
        </div>
      </section>
    </div>
  );
}

export default catalogPages;
