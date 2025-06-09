import { useRef } from "react";
import Navbar from "../components/navbar/Navbar";
import { useCart } from "./Cart/Hook/useCart";
type Props = {};

const Quote = ({}: Props) => {
  const formRef = useRef<HTMLFormElement>(null);
  const { cart, clearCart, addToCart } = useCart();

  const handleCreateQuote = async () => {
    try {
      if (cart.length === 0) {
        alert("Tu carrito está vacío. Agrega productos antes de cotizar.");
        return;
      }

      const quoteData = {
        products: cart.map((product) => ({
          id: product.id,
          price: product.price,
          idCategory: product.idCategory,
          type: product.type,
          amount: product.amount,
        })),
      };

      const response = await fetch("http://localhost:8080/api/quotes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(quoteData),
        credentials: "include",
      });
      if (!response.ok) {
        throw new Error(
          "Ocurrió un error en la creación de cotización en el servidor"
        );
      }
      alert("¡Cotización creada exitosamente!");
      clearCart();
    } catch (error) {
      console.error("Error en la solicitud:", error);
      alert("Hubo un problema al enviar la cotización. Inténtalo nuevamente.");
    }
  };

  return (
    <div id="quote" className="page">
      <Navbar />
      <div className="min-h-screen pt-5 px-10 ">
        <section className="flex flex-col items-center gap-4">
          <h1 className="text-4xl font-semibold text-[#FE3051]">
            Cotiza Ahora!
          </h1>
          <p className="max-w-[850px] font-light text-center text-[#8291a3]">
            Confirma tus productos y comenzaremos la cotización junto con el
            envio de tus productos por el medio mas fácil para ti.
            <br /> ! Escoge los productos que deseas cotizar en Catálogo !
          </p>
        </section>

        {/*Box*/}

        <section className="flex justify-center p-10">
          <div className=" w-full lg:w-5/6 p-8 rounded-[30px] bg-gradient-to-b from-[#FFFFFF] to-[#aaabac] shadow-2xl border border-gray-300">
            <h2 className="text-2xl font-bold text-[#f10028] mb-6 text-center">
              Solicita tu Cotización
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              {cart.map((product) => {
                return (
                  <div
                    key={product.id}
                    className="flex items-center justify-between mb-4"
                  >
                    <div className="flex items-center gap-4">
                      <img
                        src={product.imgItem}
                        alt={product.title}
                        className="w-28 h-28 object-cover rounded-lg"
                      />
                      <div>
                        <h3 className="text-lg font-semibold text-[#8291a3]">
                          {product.title}
                        </h3>
                        <div className="flex gap-6 items-center">
                          <p className="text-sm text-[#8291a3]">
                            Precio: ${product.price} | Cantidad:{" "}
                            {product.amount}
                          </p>
                          <button
                            className=" px-3 py-1 bg-[#ff5b5b] text-[#fbc1c1] font-bold rounded hover:bg-[#f98f8f] transition"
                            onClick={() => addToCart(product)}
                          >
                            +
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
            <button
              onClick={handleCreateQuote}
              className="bg-[#ff3455] text-white font-extrabold py-2 px-4 rounded-lg hover:bg-[#ff032d] transition-colors w-56 md:w-64 mx-auto block text-center"
            >
              Cotizar
            </button>
          </div>
        </section>
      </div>
    </div>
  );
};

export default Quote;
