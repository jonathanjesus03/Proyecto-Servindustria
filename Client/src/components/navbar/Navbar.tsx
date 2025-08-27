import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../../styles/styleHome.css";
import { useAuth } from "../../pages/Login/context/AuthContext";

type Props = {};

const Navbar: React.FC<Props> = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { Logout, authenticated } = useAuth();

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <nav className="bg-transparent w-full h-12 md:h-[53px] lg:h-[58px] px-4 sm:px-8 md:px-16 lg:px-[100px] my-0">
      <div className="flex h-full items-center justify-between">
        {/* Logo */}
        <p className="MEDIUM_MUL font-bold text-[#697686] cursor-default">
          Servindustria
        </p>

        <button
          className="md:hidden text-[#8291a3] focus:outline-none focus:ring-2 focus:ring-[#516070]"
          onClick={toggleMenu}
          aria-label={isOpen ? "Cerrar menú" : "Abrir menú"}
          aria-expanded={isOpen}
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            {isOpen ? (
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M6 18L18 6M6 6l12 12"
              />
            ) : (
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M4 6h16M4 12h16M4 18h16"
              />
            )}
          </svg>
        </button>

        {/* Menú de navegación */}
        <ul
          className={`${
            isOpen ? "flex" : "hidden"
          } md:flex flex-col md:flex-row gap-4 md:gap-8 lg:gap-16 absolute md:static top-12 left-0 w-full md:w-auto bg-white md:bg-transparent p-4 md:p-0 z-30 md:z-30 shadow-md md:shadow-none`}
        >
          <li>
            <Link
              to="/"
              className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
              onClick={() => setIsOpen(false)}
            >
              Home
            </Link>
          </li>
          <li>
            <Link
              to="/catalogo"
              className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
              onClick={() => setIsOpen(false)}
            >
              Catálogo
            </Link>
          </li>
          {authenticated && (
            <li>
              <Link
                to={"/cotizar"}
                className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
                onClick={() => {
                  setIsOpen(false);
                }}
              >
                <span>Cotizar</span>
              </Link>
            </li>
          )}
          <li>
            <Link
              to="/chatservis"
              className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
              onClick={() => setIsOpen(false)}
            >
              ChatServis
            </Link>
          </li>
          <li>
            <Link
              to="/contacto"
              className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
              onClick={() => setIsOpen(false)}
            >
              Contáctanos
            </Link>
          </li>
          <li>
            <Link
              to={authenticated ? "/" : "/login"}
              className="SMALL_MUL font-normal text-[#8291a3] hover:text-[#FF6B6B] transition-colors duration-300"
              onClick={() => {
                if (authenticated) {
                  setIsOpen(false);
                  Logout();
                  alert("Esperamos verte nuevamente");
                } else {
                  setIsOpen(false);
                }
              }}
            >
              {authenticated ? (
                <span>Cerrar Sesión</span>
              ) : (
                <span>Iniciar Sesión</span>
              )}
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
