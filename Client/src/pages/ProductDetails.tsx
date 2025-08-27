import "../styles/styleProductDetail.css";
import Navbar from "../components/navbar/Navbar";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button } from "@material-tailwind/react";
import { useCart } from "./Cart/Hook/useCart";
import { RemoveFromCartIcon } from "../components/Icon";

type Product = {
  id: number;
  cod: string;
  type: string;
  description: string;
  stock: number;
  price: number;
  idInventory?: number;
  idCategory: number;
  height?: string;
  lenght?: string;
  depth?: string;
  brand?: string;
  model?: string;
  aplication?: string;
  effect?: string;
  content?: string;
  observarions?: string;
};

type Props = {};

function productViewPage({}: Props) {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<Product | null>(null);
  const { addToCart, productInCart, removeFromCart } = useCart();

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
              {product.description}
            </h1>
            <p className="text-gray-600">Código: {product.cod}</p>
          </div>

          {/* content principal */}
          <div className="flex flex-col md:flex-row md:justify-center md:gap-20 gap-6">
            {/* Imagen */}
            <div className="flex justify-center">
              <img
                className="w-[300px] md:w-[500px] rounded-[45px] border-4 border-[#6f1e25] shadow-xl shadow-slate-600"
                src={`http://localhost:8080/img_products/${product.type}.png`}
                alt={product.type}
              />
            </div>

            {/* Detalles */}
            <div className="space-y-4 text-[#333]">
              <h2 className="text-xl font-semibold text-[#E63946]">
                Detalles del producto
              </h2>
              <ul className="space-y-1 text-sm">
                {product.type && (
                  <li>
                    <b>Descripción:</b> {product.description}
                  </li>
                )}
                {product.brand && (
                  <li>
                    <b>brand:</b> {product.brand}
                  </li>
                )}
                {product.model && (
                  <li>
                    <b>model:</b> {product.model}
                  </li>
                )}
                {product.aplication && (
                  <li>
                    <b>Aplicación:</b> {product.aplication}
                  </li>
                )}
                {product.effect && (
                  <li>
                    <b>effect:</b> {product.effect}
                  </li>
                )}
                {product.content && (
                  <li>
                    <b>content:</b> {product.content}
                  </li>
                )}
                {product.observarions && (
                  <li>
                    <b>observarions:</b> {product.observarions}
                  </li>
                )}
              </ul>

              <h2 className="text-xl font-semibold text-[#E63946] pt-4">
                Dimensiones
              </h2>
              <ul className="space-y-1 text-sm">
                {product.height && (
                  <li>
                    <b>height:</b> {product.height}
                  </li>
                )}
                {product.lenght && (
                  <li>
                    <b>lenght:</b> {product.lenght}
                  </li>
                )}
                {product.depth && (
                  <li>
                    <b>depth:</b> {product.depth}
                  </li>
                )}
              </ul>

              <div className="pt-6">
                <p className="text-lg font-bold text-[#d13131]">
                  Cotizalo ahora y obtén más información
                </p>
                <p className="text-sm text-gray-600">
                  Stosck disponible: {product.stock}
                </p>
              </div>

              <div className="flex justify-start mt-4">
                <Button
                  type="submit"
                  className="flex items-center w-70 h-10 bg-gradient-to-b from-[#FE3051] to-[#FF6074] text-white text-xl px-4 py-2 rounded-md"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                  onClick={() => {
                    !productInCart(product.id)
                      ? addToCart({
                          imgItem: `http://localhost:8080/img_products/${product.type}.png`,
                          title: product.type,
                          amount: 1,
                          id: product.id,
                          price: product.price,
                          cod: product.cod,
                          stock: product.stock,
                          type: product.type,
                          description: product.description,
                          idCategory: product.idCategory,
                          idInventory: product.idInventory,
                        })
                      : removeFromCart(product.id);
                  }}
                >
                  {productInCart(product.id) ? (
                    <span className="relative flex text-nowrap w-64 gap-3">
                      Equipado en el carrito !
                      <RemoveFromCartIcon />
                    </span>
                  ) : (
                    <span>Llévatelo</span>
                  )}
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
