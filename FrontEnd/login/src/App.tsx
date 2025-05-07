import { Route, Routes } from "react-router-dom";
import LoginLayout from "./layouts/loginLayout";
import DashboardLayout from "./layouts/dashboardLayout";
import Login from "./modules/auth/pages/LogPage";
import DashboardPage from "./modules/dashboard/pages/dashboardPage";
import CatalogLayout from "./layouts/catalogLayout";
import CatalogPage from "./modules/catalog/pages/catalogPages";
import ChatbotPage from "./modules/chatbot/page/chatbotPage";
import ChatbotLayout from "./layouts/chatbotLayout";
import ProductViewPage from "./modules/Product_View/page/productViewPage";
import ProductViewPageLayout from "./layouts/productViewLayout";
import "./App.css";
function App() {
  return (
    <Routes>
      <Route element={<LoginLayout></LoginLayout>}>
        <Route path="/" element={<Login></Login>}></Route>
      </Route>
      <Route element={<DashboardLayout></DashboardLayout>}>
        <Route
          path="/dashboard"
          element={<DashboardPage></DashboardPage>}
        ></Route>
      </Route>
      <Route element={<CatalogLayout></CatalogLayout>}>
        <Route path="/catalog" element={<CatalogPage></CatalogPage>}></Route>
      </Route>
      <Route element={<ChatbotLayout></ChatbotLayout>}>
        <Route path="/chatbot" element={<ChatbotPage></ChatbotPage>}></Route>
      </Route>
      <Route element={<ProductViewPageLayout></ProductViewPageLayout>}>
        <Route
          path="/productView"
          element={<ProductViewPage></ProductViewPage>}
        ></Route>
      </Route>
    </Routes>
  );
}

export default App;
