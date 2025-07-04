import "../styles/styleCatalog.css";
import Navbar from "../components/navbar/Navbar";
import calendar_icon from "../assets/Images/Icons/outline/calendar-outline.svg";
import Cart from "./Cart/Cart";
import { Button } from "@material-tailwind/react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useCart } from "./Cart/Hook/useCart";
import { RemoveFromCartIcon } from "../components/Icon";

export async function fetchProducts() {
  const response = await fetch("http://localhost:8080/api/product");
  if (!response.ok) throw new Error("Error al cargar productos");
  return (await response.json()) as Product[];
}

export async function fetchCategory() {
  const response = await fetch("http://localhost:8080/api/category");
  if (!response.ok) throw new Error("Error al cargar categorias");
  return await response.json();
}

type Product = {
  id: number;
  cod: string;
  type: string;
  description: string;
  stock: number;
  price: number;
  idCategory: number;
  idInventory?: number;
  height?: string;
  length?: string;
  depth?: string;
  brand?: string;
  model?: string;
  img?: string;
  application?: string;
  effect?: string;
  content?: string;
  observations?: string;
};

type Category = {
  id: number;
  nombre: string;
};

function catalogPages() {
  const [filter, setFilter] = useState(0);
  const [search, setSearch] = useState("");
  const [isShowedCategories, setShowedCategories] = useState(false);
  const [products, setProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const { addToCart, productInCart, removeFromCart } = useCart();

  useEffect(() => {
    fetchProducts()
      .then((data) => setProducts(data))
      .catch((error) => console.error("Error con productos:", error));
  }, []);

  useEffect(() => {
    fetchCategory()
      .then((data) => setCategories(data))
      .catch((error) => console.error("Error con categorias:", error));
  }, []);

  const filterProducts = () => {
    return products.filter((product) => {
      const matchesCategory = filter === 0 || product.idCategory === filter;
      const matchesSearch =
        product.type.toLowerCase().includes(search.toLowerCase()) ||
        product.description.toLowerCase().includes(search.toLowerCase());
      return matchesCategory && matchesSearch;
    });
  };
  const filteredProducts = filterProducts();

  return (
    <div id="catalog" className="page">
      <Navbar />
      <Cart></Cart>
      <div className="min-h-screen py-5 px-4 space-y-12">
        <section className="flex flex-col w-full items-center gap-2 text-center">
          <div className="flex flex-wrap justify-center gap-2">
            <h1 className="EXTRA_LARGE_MUL font-medium text-[#8291a3]">
              Los mejores artículos
            </h1>
            <h1 className="EXTRA_LARGE_MUL font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
              de la Industria
            </h1>
          </div>
          <p className="max-w-[850px] MEDIUM_MUL font-light text-[#8291a3]">
            Explora contenido relevante sobre innovación, mantenimiento,
            seguridad y eficiencia en maquinaria y procesos industriales.
          </p>
        </section>

        <section className="flex flex-col lg:flex-row gap-10">
          {/* FILTROS */}
          <div className="w-full lg:w-[280px]">
            <div className="flex flex-col border border-gray-300 shadow-xl rounded-3xl gap-5 py-5 px-4 z-10">
              <h1 className="MEDIUM_MUL font-bold text-slate-800">
                Buscar Producto
              </h1>
              <div className="relative">
                <input
                  className="w-full h-11 rounded-xl border border-gray-400 bg-white text-[#6D6D6D] text-[14px] px-5 font-mulish focus:outline-none focus:border-[#FE3051] focus:shadow-md"
                  type="text"
                  placeholder="Buscar Producto..."
                  onChange={(e) => setSearch(e.target.value)}
                  value={search}
                />
                <svg
                  className="absolute w-5 right-3 top-1/2 -translate-y-1/2"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 512 512"
                >
                  <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z" />
                </svg>
              </div>

              <h2 className="LARGE_MUL font-extrabold bg-gradient-to-b from-[#FF6074] to-[#ff002b] bg-clip-text text-transparent">
                Filtros
              </h2>
              <div>
                <div
                  className="flex justify-between items-center cursor-pointer"
                  onClick={() => setShowedCategories(!isShowedCategories)}
                >
                  <h3 className="font-semibold text-gray-800">Categorías</h3>
                  <span className="text-gray-400">⌃</span>
                </div>
                {isShowedCategories && (
                  <ul className="mt-2 space-y-2">
                    {categories.map((category) => (
                      <li key={category.id}>
                        <button
                          onClick={() => setFilter(category.id)}
                          className={`w-full text-left px-4 py-2 rounded-md transition-all ${
                            filter === category.id
                              ? "bg-[#FE3051] text-white font-semibold"
                              : "hover:bg-gray-100 text-gray-700"
                          }`}
                        >
                          {category.nombre}
                        </button>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </div>
          </div>

          {/* PRODUCTOS */}
          <div className="flex-1">
            {filter !== 0 && (
              <h2 className="mb-4 text-center LARGE_MUL font-extrabold bg-gradient-to-b from-[#FF6074] to-[#ff002b] bg-clip-text text-transparent">
                {categories.find((category) => category.id === filter)
                  ?.nombre ?? ""}
              </h2>
            )}
            <div className="grid grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-6 z-10">
              {filteredProducts.map((item) => (
                <div
                  className="fContent_card flex justify-center items-center"
                  key={item.id}
                >
                  <div className="w-[240px] h-[335px] rounded-[20px] overflow-hidden bg-gradient-to-b from-[#ff7171] to-[#970921] flex flex-col justify-between">
                    <div className="h-[50%] w-full">
                      <img
                        className="w-full h-full object-cover"
                        src={`http://localhost:8080/img_products/${item.type}.png`}
                        alt="productImg"
                      />
                    </div>
                    <div className="flex flex-col h-[50%] gap-3 p-3 text-white text-xs">
                      <div className="flex items-center gap-1">
                        <img
                          className="w-4 h-4"
                          src={calendar_icon}
                          alt="icon"
                        />
                        <span className="text-[12px]">
                          22 de Septiembre del 2025
                        </span>
                      </div>
                      <h1 className="text-[16px] font-bold">{item.type}</h1>
                      <p className="text-[11px] min-h-5 h-[36px] line-clamp-2">
                        {item.description}
                      </p>
                      <div className="flex justify-center gap-2 mt-1">
                        <Button
                          className="text-base lg:text-sm rounded-full lg:px-2 px-1 py-1 lg:py-1 bg-gradient-to-b from-[#ec5d5d] to-[#FE3051]"
                          placeholder={undefined}
                          onClick={() => {
                            !productInCart(item.id)
                              ? addToCart({
                                  imgItem: `http://localhost:8080/img_products/${item.type}.png`,
                                  title: item.type,
                                  amount: 1,
                                  id: item.id,
                                  price: item.price,
                                  cod: item.cod,
                                  stock: item.stock,
                                  type: item.type,
                                  description: item.description,
                                  idCategory: item.idCategory,
                                  idInventory: item.idInventory,
                                })
                              : removeFromCart(item.id);
                          }}
                          onPointerEnterCapture={undefined}
                          onPointerLeaveCapture={undefined}
                        >
                          {productInCart(item.id) ? (
                            <RemoveFromCartIcon />
                          ) : (
                            <span>Llévatelo</span>
                          )}
                        </Button>
                        <Link to={`/productDetails/${item.id}`}>
                          <Button
                            className="text-base lg:text-sm rounded-full px-1 py-1 lg:px-2 lg:py-1 bg-gradient-to-b from-[#A1B0BF] to-[#5C7085]"
                            placeholder={undefined}
                            onPointerEnterCapture={undefined}
                            onPointerLeaveCapture={undefined}
                          >
                            Detalles
                          </Button>
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}

export default catalogPages;
