import axios from "axios";
import { createContext, useContext, useEffect, useState } from "react";

type AuthContextType = {
  Login: (user: string, pass: string) => void;
  Logout: () => void;
  authenticated: boolean;
  setAuthenticated: (value: boolean) => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("UseAuth debe ser usado dentro de AuthProvider");
  }
  return context;
};

export const AuthProvider = ({ children }: any) => {
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8080/auth/isAuthenticated", {
        withCredentials: true,
      })
      .then((response) => {
        if (response.status === 200) {
          setAuthenticated(true);
        }
      })
      .catch(() => {
        setAuthenticated(false);
      });
  }, []);

  const Login = (user: string, pass: string) => {
    axios
      .post(
        "http://localhost:8080/auth/login",
        {
          email: user,
          password: pass,
        },
        { withCredentials: true }
      )
      .then((response) => {
        if (response.status === 200) {
          setAuthenticated(true);
        }
      })
      .catch((error) => {
        console.error("Error during login:", error);
      });
  };

  const Logout = () => {
    axios
      .post("http://localhost:8080/auth/logout", null, {
        withCredentials: true,
      })
      .then((response) => {
        if (response.status === 200) {
          setAuthenticated(false);
        }
      })
      .catch((error) => {
        console.error("Error during logout:", error);
      });
  };

  return (
    <AuthContext.Provider
      value={{
        authenticated,
        setAuthenticated,
        Login,
        Logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
