import { useContext } from "react";
import { CartContext } from "../Context/cartContext";

export const useCart = () => {
  const cartContext = useContext(CartContext);

  if (!cartContext) {
    throw new Error("useCart deberia estar dentro de CartProvider");
  }
  return cartContext;
};
