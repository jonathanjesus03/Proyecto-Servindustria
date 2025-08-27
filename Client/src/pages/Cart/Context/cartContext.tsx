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
  productInCart: (product: number) => boolean;
  removeFromCart: (product: number) => void;
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

  const productInCart = (productId: number) => {
    return cart.findIndex((prod) => prod.id === productId) !== -1;
  };

  const removeFromCart = (productId: number) => {
    setCart(cart.filter((product) => product.id !== productId));
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        setCart,
        addToCart,
        clearCart,
        productInCart,
        removeFromCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};
