import { Route, Routes } from "react-router-dom";
import LoginLayout from "./layouts/loginLayout";
import DashboardLayout from "./layouts/homeLayout";
import Login from "./pages/Login/Login";
import Home from "./pages/Home";
import CatalogLayout from "./layouts/catalogLayout";
import Catalog from "./pages/Catalog";
import ChatServis from "./pages/ChatServis/ChatServis";
import ChatServisLayout from "./layouts/chatbotLayout";
import ProductViewPage from "./pages/ProductDetails";
import ProductViewPageLayout from "./layouts/productViewLayout";
import Contact from "./pages/Contact";
import Quote from "./pages/Quote";
import "./App.css";
import { CartProvider } from "./pages/Cart/Context/cartContext";
import { RouteProtected } from "./components/Routes/RouteProtected";
import { AuthProvider } from "./pages/Login/context/AuthContext";
function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Routes>
          <Route element={<LoginLayout></LoginLayout>}>
            <Route path="/login" element={<Login></Login>}></Route>
          </Route>
          <Route element={<DashboardLayout></DashboardLayout>}>
            <Route path="/" element={<Home></Home>}></Route>
          </Route>
          <Route element={<ChatServisLayout></ChatServisLayout>}>
            <Route
              path="/chatservis"
              element={<ChatServis></ChatServis>}
            ></Route>
          </Route>
          {/*NEW LAYOUTS*/}
          <Route element={<ProductViewPageLayout></ProductViewPageLayout>}>
            <Route path="/contacto" element={<Contact></Contact>}></Route>
          </Route>
          <Route element={<ProductViewPageLayout></ProductViewPageLayout>}>
            <Route
              path="/cotizar"
              element={
                <RouteProtected>
                  <Quote></Quote>
                </RouteProtected>
              }
            ></Route>
          </Route>
          <Route element={<ProductViewPageLayout></ProductViewPageLayout>}>
            <Route
              path="/productDetails/:id"
              element={<ProductViewPage></ProductViewPage>}
            ></Route>
            <Route element={<CatalogLayout></CatalogLayout>}>
              <Route path="/catalogo" element={<Catalog></Catalog>}></Route>
            </Route>
          </Route>
        </Routes>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
