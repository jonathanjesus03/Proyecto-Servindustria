import {
  createContext,
  useState,
  ReactNode,
  Dispatch,
  SetStateAction,
} from "react";

type Product = {
  id: number;
  cod: string;
  imgItem: string;
  stock: number;
  type: string;
  price: number;
  description: string;
  idInventory?: number;
  idCategory: number;
  title: string;
  amount: number;
};

type CartContextType = {
  cart: Product[];
  setCart: Dispatch<SetStateAction<Product[]>>;
  addToCart: (newProduct: Product) => void;
  clearCart: () => void;
};

export const CartContext = createContext<CartContextType | undefined>(
  undefined
);

export const CartProvider = ({ children }: { children: ReactNode }) => {
  const [cart, setCart] = useState<Product[]>([]);

  const addToCart = (newProduct: Product) => {
    const existingProductIndex = cart.findIndex(
      (product) => product.id === newProduct.id
    );

    //product founded
    if (existingProductIndex >= 0) {
      const newCart = structuredClone(cart);
      newCart[existingProductIndex].amount += 1;
      return setCart(newCart);
    }

    //product not found, add it
    setCart((prevCart) => [...prevCart, { ...newProduct, amount: 1 }]);
  };

  const clearCart = () => {
    setCart([]);
  };

  return (
    <CartContext.Provider value={{ cart, setCart, addToCart, clearCart }}>
      {children}
    </CartContext.Provider>
  );
};
