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
          className="px-2 py-1 bg-[#e5e7eb] text-[#8291a3] font-bold rounded hover:bg-[#d1d5db] transition"
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
        className="absolute lg:left-36 lg:top-5 left-16 top-10 flex items-center justify-center w-10 h-10 bg-[#f3f4f6] text-[#8291a3] rounded-full cursor-pointer transition-transform duration-300 hover:scale-110 z-[9999] shadow-md"
      >
        <CartIcon />
      </label>

      <input id={cartCheckBoxId} type="checkbox" hidden className="peer" />

      <aside className="fixed top-0 right-0 w-[240px] h-full p-6 gap-4 bg-[#f9fafb] border-l border-gray-300 rounded-l-3xl shadow-xl transition-transform duration-300 translate-x-full peer-checked:translate-x-0 peer-checked:flex peer-checked:flex-col">
        <ul className="flex-1 space-y-4 overflow-y-auto pr-2">
          {cart.map((product) => {
            return (
              <CartItem
                key={product.id}
                id={product.id}
                amount={product.amount}
                imgItem={product.imgItem}
                price={product.price}
                title={product.title}
                addToCart={() => addToCart(product)}
              />
            );
          })}
        </ul>

        <button
          className="mt-auto px-4 py-2 bg-[#e5e7eb] text-[#8291a3] font-semibold rounded hover:bg-[#d1d5db] transition"
          onClick={() => clearCart()}
        >
          <ClearCartIcon />
        </button>
      </aside>
    </div>
  );
}

export default Cart;
