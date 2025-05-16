import "../styles/styleLogin.css";
import "../index.css";
import icon_user from "../assets/Images/Icons/filled/user_filled.png";
import icon_password from "../assets/Images/Icons/filled/password_filled.png";
import icon_back from "../assets/Images/Icons/filled/back_filled.png";

import icon_face from "../assets/Images/Icons/outline/face_id.png";
import icon_gift from "../assets/Images/Icons/outline/gift.png";
import icon_pass_out from "../assets/Images/Icons/outline/password.png";
import icon_phone from "../assets/Images/Icons/outline/phone.png";
import icon_mail from "../assets/Images/Icons/outline/mail.svg";
import icon_doc from "../assets/Images/Icons/outline/document.svg";
import icon_pass_vfy from "../assets/Images/Icons/outline/pass_verify.png";

import React, { useState } from "react";
import { Button } from "@material-tailwind/react";
import { Link, useNavigate } from "react-router-dom";
const img_form =
  "bg-[url(../../src/assets/Images/backgrounds/bg_right_box.png)]";

//------Page-------//
type Props = {};

function Login({}: Props) {
  //-----------Handle Submit------------//
  const navigate = useNavigate();

  type FormData = {
    name: string;
    last_name: string;
    telephone: string;
    date_bithday: string;
    email_register: string;
    email: string;
    password_register: string;
    password_confirm: string;
    password: string;
    doc: string;
  };

  type FormErrorLogin = {
    email?: string;
    password?: string;
  };

  type FormErrorRegister = {
    name?: string;
    last_name?: string;
    phone?: string;
    birthDay?: string;
    emailToRegis?: string;
    passwordToReges?: string;
    confirmPassword?: string;
    doc?: string;
  };

  const [formData, setFormData] = useState<FormData>({
    name: "",
    last_name: "",
    telephone: "",
    date_bithday: "",
    email: "",
    email_register: "",
    password_register: "",
    password_confirm: "",
    password: "",
    doc: "",
  });

  const [formErrorLogin, setFormErrorLogin] = useState<FormErrorLogin>({});
  const [formErrorRegister, setFormErrorRegister] = useState<FormErrorRegister>(
    {}
  );

  const validateLogin = (): boolean => {
    const errors: FormErrorLogin = {};

    if (!formData.email.trim()) {
      errors.email = "El correo electrónico es obligatorio.";
    } else if (
      !/^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$/.test(formData.email)
    ) {
      errors.email = "Correo electrónico inválido.";
    }

    if (!formData.password.trim()) {
      errors.password = "Contraseña Inválida.";
    }

    setFormErrorLogin(errors);
    return Object.keys(errors).length === 0;
  };

  const validateRegister = (): boolean => {
    const errors: FormErrorRegister = {};

    if (!formData.name.trim()) {
      errors.name = "El nombre es obligatorio.";
    }

    if (!formData.last_name.trim()) {
      errors.last_name = "El apellido es obligatorio.";
    }

    if (!formData.telephone.trim()) {
      errors.phone = "El número de teléfono es obligatorio.";
    } else if (!/^\d{7,15}$/.test(formData.telephone)) {
      errors.phone = "El número de teléfono no es válido.";
    }

    if (!formData.date_bithday.trim()) {
      errors.birthDay = "La fecha de nacimiento es obligatoria.";
    } else if (!/^\d{2}\/\d{2,}\/\d{4}$/.test(formData.date_bithday)) {
      errors.birthDay = "Ingresa un formato de fecha: DD/MM/YYYY";
    }

    if (!formData.email_register.trim()) {
      errors.emailToRegis = "El correo electrónico es obligatorio.";
    } else if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
        formData.email_register
      )
    ) {
      errors.emailToRegis = "Correo electrónico inválido.";
    }

    if (!formData.password_register.trim()) {
      errors.passwordToReges = "La contraseña es obligatoria.";
    } else if (formData.password_register.length < 8) {
      errors.passwordToReges = "Debe tener al menos 8 caracteres.";
    }

    if (formData.password_confirm !== formData.password_register) {
      errors.confirmPassword = "Las contraseñas no coinciden.";
    }

    if (!formData.doc.trim()) {
      errors.doc = "El número de documento es obligatorio.";
    } else if (!/^\d{6,15}$/.test(formData.doc)) {
      errors.doc = "Documento inválido.";
    }

    setFormErrorRegister(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  //-----------Handle Login-----------//
  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!validateLogin()) {
      return;
    }

    const email = (
      document.querySelector('input[name="email"]') as HTMLInputElement
    ).value;
    const password = (
      document.querySelector('input[name="password"]') as HTMLInputElement
    ).value;

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        alert("!Login Succesfully! \n token: " + data.token);
        navigate("/cotizar");
      } else if (response.status === 403) {
        alert("Credenciales inválidas");
      } else {
        throw new Error("Ocurrio un error en la conexión");
      }
    } catch (error) {
      console.error("Error: " + error);
      alert("Ocurrió un error al intentar iniciar sesión.");
    }
  };

  //-----------HandleRegister-----------//
  const handleRegister = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!validateRegister()) {
      return;
    }

    const name = (
      document.querySelector('input[name="name"]') as HTMLInputElement
    ).value;
    const lastname = (
      document.querySelector('input[name="last_name"]') as HTMLInputElement
    ).value;
    const telephone = (
      document.querySelector('input[name="telephone"]') as HTMLInputElement
    ).value;
    const birthDay = (
      document.querySelector('input[name="date_bithday"]') as HTMLInputElement
    ).value;
    const email = (
      document.querySelector('input[name="email_register"]') as HTMLInputElement
    ).value;
    const doc = (
      document.querySelector('input[name="doc"]') as HTMLInputElement
    ).value;
    const password = (
      document.querySelector(
        'input[name="password_register"]'
      ) as HTMLInputElement
    ).value;
    const passwrod_confirm = (
      document.querySelector(
        'input[name="password_confirm"]'
      ) as HTMLInputElement
    ).value;
    console.log(`|${password}| --- |${passwrod_confirm}|`);

    try {
      if (password !== passwrod_confirm) {
        throw new Error("Las contraseñas no coinciden");
      }

      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name,
          lastname,
          telephone,
          birthDay,
          email,
          doc,
          password,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        alert("!Register Succesfully!");
        navigate("/login");
      } else {
        throw new Error("Ocurrio un error en la conexión");
      }
    } catch (error) {
      console.error("Error: " + error);
      alert(error);
    }
  };

  //---------------Mange Register and Login Form Movement--------------//
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div
      id="page box_rear"
      className={`relative flex flex-col lg:flex-row BOX_REAR rounded-[40px] lg:rounded-[80px] m-10 md:m-auto py-4 lg:py-0
    shadow-[0px_8px_80px_rgba(0,0,0,0.2)] lg:shadow-[0px_15px_150px_rgba(0,0,0,0.3)] 
    w-full max-w-[95%] sm:max-w-[90%] md:max-w-[80%] lg:max-w-[1200px]`}
    >
      <div
        className={`page flex lg:absolute lg:flex-row justify-center transition_slap  ${
          isLogin ? "contentLog" : "contentReg"
        } BOX_INNER`}
      >
        {/*-------LOGIN FORM------*/}
        {isLogin && (
          <div
            id="box_l"
            className={`flex flex-col space-y-3 lg:py-4 px-5 rounded-l-[80px]`}
          >
            <section className="relative flex justify-end w-full pt-3 px-2">
              <div className="absolute left-0 top-0">
                <Link to="/">
                  <Button
                    type="button"
                    className={`shadow-none`}
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    <img
                      className="w-[25px] max-w-[35px]"
                      src={icon_back}
                      alt="icon"
                    />
                  </Button>
                </Link>
              </div>

              <div>
                <p
                  id="p_register"
                  className={`SMALL font-light text-[#A1B0BF] cursor-pointer`}
                  onClick={() => {
                    // Limpiar los datos del formulario
                    setFormData({
                      name: "",
                      last_name: "",
                      telephone: "",
                      date_bithday: "",
                      email: "",
                      email_register: "",
                      password_register: "",
                      password_confirm: "",
                      password: "",
                      doc: "",
                    });

                    // También puedes limpiar errores si es necesario
                    setFormErrorLogin({
                      email: "",
                      password: "",
                    });

                    // Cambiar a modo registro
                    setIsLogin(false);
                  }}
                >
                  Registrate
                </p>
              </div>
            </section>

            <section className="flex w-full h-full">
              <form
                onSubmit={handleLogin}
                id="form-login"
                className="flex flex-col relative w-full items-center"
              >
                <div className="flex flex-col items-center">
                  <p className={`LARGE font-light text-[#A1B0BF]`}>
                    Bienvenido a
                  </p>
                  <h1 className="font-mulish text-[60px] tracking-[8px] border-none outline-none font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
                    Servindustria
                  </h1>

                  <div className="flex flex-col w-auto relative items-start justify-center mx-[8vh]">
                    <p className={`SMALL_MUL font-bold text-[#A1B0BF]`}>
                      Tu acceso a la seguridad confiable
                    </p>
                    <p
                      className={`SMALL font-light lg:mt-5 text-[#A1B0BF] text-justify`}
                    >
                      Accede con confianza, tu seguridad es nuestra prioridad,
                      Inicia sesión y protege lo que más importa.
                    </p>
                  </div>
                </div>

                <section className="flex flex-col w-full items-center gap-3 p-3 px-10 pt-4">
                  <div className="flex w-[350px] sm:w-[380px] md:w-[480px] lg:w-11/12 relative items-center">
                    <img src={icon_user} alt="icon" className={`logoLogin`} />
                    <input
                      placeholder="Ingrese su correo"
                      type="text"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      required
                      className={`inputLogin`}
                    />
                    {formErrorLogin.email && (
                      <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                        {formErrorLogin.email}
                      </div>
                    )}
                  </div>
                  <div className="flex w-[350px] sm:w-[380px] md:w-[480px] lg:w-11/12 relative items-center">
                    <img
                      src={icon_password}
                      alt="icon"
                      className={`logoLogin`}
                    />
                    <input
                      placeholder="Ingrese su contraseña"
                      type="password"
                      name="password"
                      onChange={handleChange}
                      className={`inputLogin`}
                      value={formData.password}
                    />
                    {formErrorLogin.password && (
                      <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                        {formErrorLogin.password}
                      </div>
                    )}
                  </div>

                  <section className="flex w-full justify-between px-12">
                    <div>
                      <input
                        type="checkbox"
                        id="cb"
                        className="p-3 cursor-pointer"
                      />
                      <label
                        htmlFor="cb"
                        className={`EXTRA_SMALL font-light m-1.5 cursor-pointer`}
                      >
                        Recuerdame
                      </label>
                    </div>
                    <div>
                      <p className={`EXTRA_SMALL font-light mt-1`}>
                        ¿Olvidaste tu contraseña?
                      </p>
                    </div>
                  </section>
                </section>
                <section className="flex w-full justify-center space-x-8 mt-[0.6rem]">
                  <div>
                    <Button
                      id="ButtonLogin"
                      type="submit"
                      className={`logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r  from-[#ff7c7c] to-[#ff002b]`}
                      placeholder={undefined}
                      onPointerEnterCapture={undefined}
                      onPointerLeaveCapture={undefined}
                    >
                      Iniciar Sesión
                    </Button>
                  </div>
                  <div>
                    <Button
                      id="ButtonCrearCuenta"
                      type="button"
                      className={`logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r from-[#A1B0BF] to-[#5C7085]`}
                      placeholder={undefined}
                      onPointerEnterCapture={undefined}
                      onPointerLeaveCapture={undefined}
                      onClick={() => {
                        {
                          // Limpiar los datos del formulario
                          setFormData({
                            name: "",
                            last_name: "",
                            telephone: "",
                            date_bithday: "",
                            email: "",
                            email_register: "",
                            password_register: "",
                            password_confirm: "",
                            password: "",
                            doc: "",
                          });

                          // También puedes limpiar errores si es necesario
                          setFormErrorLogin({
                            email: "",
                            password: "",
                          });

                          // Cambiar a modo registro
                          setIsLogin(false);
                        }
                      }}
                    >
                      Crear Cuenta
                    </Button>
                  </div>
                </section>
              </form>
            </section>
          </div>
        )}

        {/*-----------REGISTER-----------*/}
        {!isLogin && (
          <div
            id="box_r"
            className={`flex flex-col px-5 py-2 gap-8 rounded-r-[80px]`}
          >
            <section className="relative w-full pt-2 px-2">
              <div className="absolute left-0 top-0">
                <Button
                  type="button"
                  className={`shadow-none`}
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                  onClick={() => {
                    {
                      // Limpiar los datos del formulario
                      setFormData({
                        name: "",
                        last_name: "",
                        telephone: "",
                        date_bithday: "",
                        email: "",
                        email_register: "",
                        password_register: "",
                        password_confirm: "",
                        password: "",
                        doc: "",
                      });

                      // También puedes limpiar errores si es necesario
                      setFormErrorRegister({
                        emailToRegis: "",
                        passwordToReges: "",
                        birthDay: "",
                        confirmPassword: "",
                        doc: "",
                        last_name: "",
                        name: "",
                        phone: "",
                      });

                      // Cambiar a modo registro
                      setIsLogin(true);
                    }
                  }}
                >
                  <img
                    className="w-[25px] max-w-[35px]"
                    src={icon_back}
                    alt="icon"
                  />
                </Button>
              </div>
            </section>

            <section className="flex items-center w-full h-full">
              <form
                onSubmit={handleRegister}
                className="flex flex-col relative w-full items-center"
              >
                <div className="flex flex-col items-center">
                  <p
                    className={`LARGE font-medium  text-[#A1B0BF] leading-[0px]`}
                  >
                    Crear Cuenta
                  </p>
                  <h1 className="font-mulish text-[54px] tracking-[8px] outline-none font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
                    Servindustria
                  </h1>
                  <div className="flex flex-col relative items-start justify-center mx-[8vh]">
                    <p
                      className={`SMALL font-light lg:mt-2 w-auto text-[#A1B0BF] text-justify`}
                    >
                      Regístrate para acceder a nuestras soluciones
                    </p>
                  </div>
                </div>

                <section className="grid grid-cols-1 md:grid-cols-2 w-full items-center gap-2 p-3 pt-3">
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="name"
                      className={`SMALL font-light text-[#5C7085]`}
                    >
                      Nombre
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_face}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Nombre"
                        type="text"
                        name="name"
                        onChange={handleChange}
                        className={`inputRegister`}
                        value={formData.name}
                      />
                      {formErrorRegister.name && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.name}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="last_name"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Apellido
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_face}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Apellido"
                        type="text"
                        value={formData.last_name}
                        name="last_name"
                        className={`inputRegister`}
                        onChange={handleChange}
                      />
                      {formErrorRegister.last_name && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.last_name}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="telephone"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Telefono
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_phone}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Teléfono"
                        type="text"
                        value={formData.telephone}
                        onChange={handleChange}
                        name="telephone"
                        className={`inputRegister`}
                      />
                      {formErrorRegister.phone && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.phone}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="date_bithday"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Fecha de Nacimiento
                    </label>

                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_gift}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Fecha de Nacimiento"
                        type="text"
                        value={formData.date_bithday}
                        name="date_bithday"
                        onChange={handleChange}
                        className={`inputRegister`}
                      />
                      {formErrorRegister.birthDay && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.birthDay}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="email_register"
                      className={`SMALL font-light text-[#5C7085]`}
                    >
                      Correo Electrónico
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_mail}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Correo Electrónico"
                        type="text"
                        onChange={handleChange}
                        value={formData.email_register}
                        name="email_register"
                        className={`inputRegister`}
                      />
                      {formErrorRegister.emailToRegis && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.emailToRegis}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="doc"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Documento O RUC
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_doc}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="DNI / RUC"
                        type="text"
                        value={formData.doc}
                        name="doc"
                        onChange={handleChange}
                        className={`inputRegister`}
                      />

                      {formErrorRegister.doc && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.doc}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="password_register"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Contraseña
                    </label>
                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_pass_out}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Contraseña"
                        type="password"
                        onChange={handleChange}
                        value={formData.password_register}
                        name="password_register"
                        className={`inputRegister`}
                      />
                      {formErrorRegister.passwordToReges && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.passwordToReges}
                        </div>
                      )}
                    </div>
                  </div>
                  <div className="flex flex-col space-y-[2px] justify-center items-center">
                    <label
                      htmlFor="password_confirm"
                      className={`SMALL font-light text-[#5C7085] `}
                    >
                      Confirma tu contraseña
                    </label>

                    <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                      <img
                        src={icon_pass_vfy}
                        alt="icon"
                        className={`logoRegister`}
                      />
                      <input
                        placeholder="Confirmar contraseña"
                        onChange={handleChange}
                        type="password"
                        name="password_confirm"
                        value={formData.password_confirm}
                        className={`inputRegister`}
                      />
                      {formErrorRegister.confirmPassword && (
                        <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                          {formErrorRegister.confirmPassword}
                        </div>
                      )}
                    </div>
                  </div>
                </section>

                <section className="flex w-full justify-end px-5">
                  <div>
                    <p
                      className={`EXTRA_SMALL font-light cursor-pointer`}
                      onClick={() => {
                        {
                          {
                            // Limpiar los datos del formulario
                            setFormData({
                              name: "",
                              last_name: "",
                              telephone: "",
                              date_bithday: "",
                              email: "",
                              email_register: "",
                              password_register: "",
                              password_confirm: "",
                              password: "",
                              doc: "",
                            });

                            // También puedes limpiar errores si es necesario
                            setFormErrorRegister({
                              emailToRegis: "",
                              passwordToReges: "",
                              birthDay: "",
                              confirmPassword: "",
                              doc: "",
                              last_name: "",
                              name: "",
                              phone: "",
                            });

                            // Cambiar a modo registro
                            setIsLogin(true);
                          }
                        }
                      }}
                    >
                      ¿Yá tienes una cuenta creada?
                    </p>
                  </div>
                </section>
                <section className="flex w-full justify-center space-x-9 mt-[0.6rem]">
                  <div>
                    <Button
                      id="ButtonRegister"
                      type="submit"
                      className={`logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r from-[#ff7c7c] to-[#ff002b]`}
                      placeholder={undefined}
                      onPointerEnterCapture={undefined}
                      onPointerLeaveCapture={undefined}
                    >
                      Crear Cuenta
                    </Button>
                  </div>
                </section>
              </form>
            </section>
          </div>
        )}
      </div>
      <div
        className={`page hidden lg:block absolute transition_slap ${
          !isLogin ? "translate-x-0" : "translate-x-full"
        } BOX_IMG rounded-[80px] overflow-hidden `}
      >
        <div className={`${img_form} BG_IMG`}></div>
      </div>
    </div>
  );
}

export default Login;
