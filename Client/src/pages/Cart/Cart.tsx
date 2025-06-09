import { useId } from "react";
import { CartIcon, ClearCartIcon } from "../../components/Icon";
import { useCart } from "./Hook/useCart";

type Props = {
  id: number;
  imgItem: string;
  price: number;
  title: string;
  amount: number;
  addToCart: () => void;
};

function CartItem({ imgItem, price, title, amount, addToCart }: Props) {
  return (
    <li className="border-b border-gray-300 pb-4">
      <img
        src={imgItem}
        alt={title}
        className="w-full aspect-[12/11] rounded-lg"
      />
      <div className="text-sm mt-2 text-[#8291a3]">
        <strong>{title}</strong> - ${price}
      </div>
      <footer className="flex items-center justify-center gap-2 mt-2">
        <small className="text-[#8291a3]">Qty: {amount}</small>
        <button
          onClick={addToCart}
          className="px-2 py-1 bg-[#f99a9a] text-[#8291a3] font-bold rounded hover:bg-[#f98f8f] transition"
        >
          +
        </button>
      </footer>
    </li>
  );
}

function Cart() {
  const cartCheckBoxId = useId();
  const { cart, addToCart, clearCart } = useCart();

  return (
    <div id="cart" className="relative">
      <label
        htmlFor={cartCheckBoxId}
        className="absolute lg:left-[42vh] lg:top-5 left-16 top-24 flex items-center justify-center w-10 h-10 bg-gradient-to-b from-[#FF6074] to-[#FE3051] text-white rounded-full cursor-pointer transition-transform duration-300 hover:scale-110 shadow-md"
      >
        <CartIcon />
      </label>
      <input id={cartCheckBoxId} type="checkbox" hidden className="peer" />

      <aside className="fixed z-40 top-0 right-0 w-[260px] h-full p-6 gap-4 bg-gradient-to-bl from-[#ffffff] to-[#fb8d8d] border-l border-[#e8b3b3] rounded-l-3xl shadow-xl transition-transform duration-300 translate-x-full peer-checked:translate-x-0 peer-checked:flex peer-checked:flex-col">
        <ul className="flex-1 space-y-4 overflow-y-auto pr-2 scrollbar-thin scrollbar-thumb-[#e8b3b3] scrollbar-track-[#f9fafb]">
          {cart.length === 0 ? (
            <li className="text-center text-[#A1B0BF] py-8 pt-[30vh]">
              <div className="text-lg font-semibold mb-2">
                Tu carrito está vacío
              </div>
              <div className="text-sm">Agrega productos para verlos aquí.</div>
            </li>
          ) : (
            cart.map((product) => (
              <CartItem
                key={product.id}
                id={product.id}
                amount={product.amount}
                imgItem={product.imgItem}
                price={product.price}
                title={product.title}
                addToCart={() => addToCart(product)}
              />
            ))
          )}
        </ul>

        <button
          className="mt-auto px-4 py-2 bg-gradient-to-b from-[#ff7171] to-[#ff002b] text-white font-semibold rounded hover:from-[#ff3939] hover:to-[#e23a56] transition"
          onClick={() => clearCart()}
        >
          <ClearCartIcon />
        </button>
      </aside>
    </div>
  );
}

export default Cart;
