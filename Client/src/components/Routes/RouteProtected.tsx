import { ReactNode } from "react";
import { useAuth } from "../../pages/Login/context/AuthContext";
import { Navigate } from "react-router-dom";

export const RouteProtected = ({ children }: { children: ReactNode }) => {
  const { authenticated } = useAuth();

  // Check if the user is authenticated
  if (!authenticated) {
    return <Navigate to={"/login"} />;
  }

  return <div>{children}</div>;
};
