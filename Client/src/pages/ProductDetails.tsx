import "../styles/styleProductDetail.css";
import Navbar from "../components/navbar/Navbar";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button } from "@material-tailwind/react";

type Product = {
  id: number;
  cod: string;
  tipo: string;
  descripcion: string;
  stock: number;
  precio: number;
  idInventario?: number;
  idCategoria?: number;
  alto?: string;
  largo?: string;
  fondo?: string;
  marca?: string;
  modelo?: string;
  aplicacion?: string;
  efecto?: string;
  contenido?: string;
  observaciones?: string;
};

type Props = {};

function productViewPage({}: Props) {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<Product | null>(null);

  async function fetchProducts() {
    const response = await fetch(`http://localhost:8080/api/product/${id}`);
    if (!response.ok) throw new Error("Error al obtener los datos de la bd");
    return await response.json();
  }

  useEffect(() => {
    fetchProducts()
      .then((data) => {
        setProduct(data);
      })
      .catch((error) => {
        console.error("Error: " + error);
      });
  }, []);

  if (!product) return <div className="text-center mt-10">Cargando...</div>;

  return (
    <div id="productDetail" className="page">
      <Navbar />

      <div className="flex flex-col min-h-screen items-center pt-6 bg-gray-100">
        <section className="w-full max-w-7xl px-4 md:px-8 space-y-8">
          {/* Título */}
          <div className="text-center">
            <h1 className="text-3xl font-bold text-[#d13131] mb-2">
              {product.descripcion}
            </h1>
            <p className="text-gray-600">Código: {product.cod}</p>
          </div>

          {/* Contenido principal */}
          <div className="flex flex-col md:flex-row md:justify-center md:gap-20 gap-6">
            {/* Imagen */}
            <div className="flex justify-center">
              <img
                className="w-[300px] md:w-[500px] rounded-[45px] border-4 border-[#6f1e25] shadow-xl shadow-slate-600"
                src={`http://localhost:8080/img_products/${product.tipo}.png`}
                alt={product.tipo}
              />
            </div>

            {/* Detalles */}
            <div className="space-y-4 text-[#333]">
              <h2 className="text-xl font-semibold text-[#E63946]">
                Detalles del producto
              </h2>
              <ul className="space-y-1 text-sm">
                {product.tipo && (
                  <li>
                    <b>Descripción:</b> {product.descripcion}
                  </li>
                )}
                {product.marca && (
                  <li>
                    <b>Marca:</b> {product.marca}
                  </li>
                )}
                {product.modelo && (
                  <li>
                    <b>Modelo:</b> {product.modelo}
                  </li>
                )}
                {product.aplicacion && (
                  <li>
                    <b>Aplicación:</b> {product.aplicacion}
                  </li>
                )}
                {product.efecto && (
                  <li>
                    <b>Efecto:</b> {product.efecto}
                  </li>
                )}
                {product.contenido && (
                  <li>
                    <b>Contenido:</b> {product.contenido}
                  </li>
                )}
                {product.observaciones && (
                  <li>
                    <b>Observaciones:</b> {product.observaciones}
                  </li>
                )}
              </ul>

              <h2 className="text-xl font-semibold text-[#E63946] pt-4">
                Dimensiones
              </h2>
              <ul className="space-y-1 text-sm">
                {product.alto && (
                  <li>
                    <b>Alto:</b> {product.alto}
                  </li>
                )}
                {product.largo && (
                  <li>
                    <b>Largo:</b> {product.largo}
                  </li>
                )}
                {product.fondo && (
                  <li>
                    <b>Fondo:</b> {product.fondo}
                  </li>
                )}
              </ul>

              <div className="pt-6">
                <p className="text-lg font-bold text-[#d13131]">
                  Precio: S/. {product.precio.toFixed(2)}
                </p>
                <p className="text-sm text-gray-600">
                  Stosck disponible: {product.stock}
                </p>
              </div>

              <div className="flex justify-start mt-4">
                <Button
                  type="submit"
                  className="dashButton bg-gradient-to-b from-[#FE3051] to-[#FF6074] text-white text-xl px-4 py-2 rounded-md"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  Obtener
                </Button>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}

export default productViewPage;
