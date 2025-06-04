import "../../styles/styleLogin.css";
import "../../index.css";
import icon_user from "../../assets/Images/Icons/filled/user_filled.png";
import icon_password from "../../assets/Images/Icons/filled/password_filled.png";
import icon_back from "../../assets/Images/Icons/filled/back_filled.png";

import icon_face from "../../assets/Images/Icons/outline/face_id.png";
import icon_gift from "../../assets/Images/Icons/outline/gift.png";
import icon_pass_out from "../../assets/Images/Icons/outline/password.png";
import icon_phone from "../../assets/Images/Icons/outline/phone.png";
import icon_mail from "../../assets/Images/Icons/outline/mail.svg";
import icon_doc from "../../assets/Images/Icons/outline/document.svg";
import icon_pass_vfy from "../../assets/Images/Icons/outline/pass_verify.png";
import icon_gender from "../../assets/Images/Icons/filled/gender_icon.svg";

import InputRegister from "./components/inputRegister";

import React, { useCallback, useState } from "react";
import { Button } from "@material-tailwind/react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";
const img_form =
  "bg-[url(../../src/assets/Images/backgrounds/bg_right_box.png)]";

//------Page-------//
type Props = {};

function Login({}: Props) {
  //-----------Handle Submit------------//
  const navigate = useNavigate();
  const { authenticated, Login } = useAuth();

  type FormData = {
    name: string;
    last_name: string;
    gender: string;
    natural_telephone1: string;
    natural_telephone2: string;
    company_telephone1: string;
    company_telephone2: string;
    birthDay: string;
    natural_email_register: string;
    company_email_register: string;
    email: string;
    natural_password_register: string;
    company_password_register: string;
    natural_password_confirm: string;
    company_password_confirm: string;
    password: string;
    natural_type_doc: string;
    company_type_doc: string;
    natural_doc: string;
    company_doc: string;
    address: string;
    reference: string;
    nameReasonSoc: string;
  };

  const [formData, setFormData] = useState<FormData>({
    name: "",
    last_name: "",
    gender: "",
    natural_telephone1: "",
    natural_telephone2: "",
    company_telephone1: "",
    company_telephone2: "",
    birthDay: "",
    natural_email_register: "",
    company_email_register: "",
    email: "",
    natural_password_register: "",
    company_password_register: "",
    natural_password_confirm: "",
    company_password_confirm: "",
    password: "",
    natural_type_doc: "",
    company_type_doc: "",
    natural_doc: "",
    company_doc: "",
    address: "",
    reference: "",
    nameReasonSoc: "",
  });

  function cleanInputs() {
    setFormData({
      name: "",
      last_name: "",
      gender: "",
      natural_telephone1: "",
      natural_telephone2: "",
      company_telephone1: "",
      company_telephone2: "",
      birthDay: "",
      natural_email_register: "",
      company_email_register: "",
      email: "",
      natural_password_register: "",
      company_password_register: "",
      natural_password_confirm: "",
      company_password_confirm: "",
      password: "",
      natural_type_doc: "",
      company_type_doc: "",
      natural_doc: "",
      company_doc: "",
      address: "",
      reference: "",
      nameReasonSoc: "",
    });
    setFormErrorLogin({
      email: "",
      password: "",
    });
    setFormErrorRegisterNatural({
      email_register: "",
      password_register: "",
      birthDay: "",
      password_confirm: "",
      doc: "",
      last_name: "",
      name: "",
      telephone1: "",
      telephone2: "",
      type_doc: "",
    });
    setFormErrorRegisterNatural({
      email_register: "",
      password_register: "",
      birthDay: "",
      password_confirm: "",
      doc: "",
      last_name: "",
      name: "",
      telephone1: "",
      telephone2: "",
    });
  }

  type FormErrorLogin = {
    email?: string;
    password?: string;
  };

  type FormErrorRegisterNatural = {
    name?: string;
    last_name?: string;
    telephone1?: string;
    telephone2?: string;
    birthDay?: string;
    gender?: string;
    email_register?: string;
    password_register?: string;
    password_confirm?: string;
    type_doc?: string;
    doc?: string;
    address?: string;
    reference?: string;
    nameReasonSoc?: string;
  };

  type FormErrorRegisterCompany = {
    name?: string;
    last_name?: string;
    telephone1?: string;
    telephone2?: string;
    birthDay?: string;
    gender?: string;
    email_register?: string;
    password_register?: string;
    password_confirm?: string;
    type_doc?: string;
    doc?: string;
    address?: string;
    reference?: string;
    nameReasonSoc?: string;
  };

  const [formErrorLogin, setFormErrorLogin] = useState<FormErrorLogin>({});
  const [formErrorRegisterNatural, setFormErrorRegisterNatural] =
    useState<FormErrorRegisterNatural>({});
  const [formErrorRegisterCompany, setFormErrorRegisterComapany] =
    useState<FormErrorRegisterCompany>({});

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

  const validateRegisterNatural = (): boolean => {
    const errors: FormErrorRegisterNatural = {};

    if (!formData.name.trim()) {
      errors.name = "El nombre es obligatorio.";
    }

    if (!formData.last_name.trim()) {
      errors.last_name = "El apellido es obligatorio.";
    }

    if (!formData.natural_telephone1.trim()) {
      errors.telephone1 = "El número de teléfono es obligatorio.";
    } else if (!/^\d{7,15}$/.test(formData.natural_telephone1)) {
      errors.telephone1 = "El número de teléfono no es válido.";
    }

    if (!/^\d*$/.test(formData.natural_telephone2)) {
      errors.telephone2 = "El número de teléfono es inválido.";
    }

    if (!formData.birthDay.trim()) {
      errors.birthDay = "La fecha de nacimiento es obligatoria.";
    } else if (!/^\d{2}\/\d{2}\/\d{4}$/.test(formData.birthDay)) {
      errors.birthDay = "Formato inválido. Usa DD/MM/YYYY.";
    }

    if (!/^(Femenino|Masculino|No especificar)$/.test(formData.gender)) {
      errors.gender = "Debe seleccionar un sexo.";
    }

    if (!formData.natural_email_register.trim()) {
      errors.email_register = "El correo electrónico es obligatorio.";
    } else if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
        formData.natural_email_register
      )
    ) {
      errors.email_register = "Correo electrónico inválido.";
    }

    if (!formData.natural_password_register.trim()) {
      errors.password_register = "La contraseña es obligatoria.";
    } else if (formData.natural_password_register.length < 8) {
      errors.password_register = "Debe tener al menos 8 caracteres.";
    }

    if (
      formData.natural_password_confirm !== formData.natural_password_register
    ) {
      errors.password_confirm = "Las contraseñas no coinciden.";
    }

    if (!/^(DNI|RUC)$/.test(formData.natural_type_doc)) {
      errors.type_doc = "Debe seleccionar el tipo de documento.";
    }

    if (!formData.natural_doc.trim()) {
      errors.doc = "El número de documento es obligatorio.";
    } else {
      if (
        formData.natural_type_doc === "DNI" &&
        !/^\d{8}$/.test(formData.natural_doc)
      ) {
        errors.doc = "El DNI debe tener 8 dígitos.";
      } else if (
        formData.natural_type_doc === "RUC" &&
        !/^\d{11}$/.test(formData.natural_doc)
      ) {
        errors.doc = "El RUC debe tener 11 dígitos.";
      }
    }

    setFormErrorRegisterNatural(errors);
    return Object.keys(errors).length === 0;
  };

  const validateRegisterCompany = (): boolean => {
    const errors: FormErrorRegisterCompany = {};

    if (!formData.nameReasonSoc.trim()) {
      errors.name = "El Nombre/Razon es obligatorio.";
    }

    if (!formData.reference.trim()) {
      errors.name = "La referencia es obligatoria.";
    }

    if (!formData.address.trim()) {
      errors.last_name = "La dirección es obligatoria.";
    }

    if (!formData.company_telephone1.trim()) {
      errors.telephone1 = "El número de teléfono es obligatorio.";
    } else if (!/^\d{7,15}$/.test(formData.company_telephone1)) {
      errors.telephone1 = "El número de teléfono no es válido.";
    }

    if (!formData.company_email_register.trim()) {
      errors.email_register = "El correo electrónico es obligatorio.";
    } else if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
        formData.company_email_register
      )
    ) {
      errors.email_register = "Correo electrónico inválido.";
    }

    if (!formData.company_password_register.trim()) {
      errors.password_register = "La contraseña es obligatoria.";
    } else if (formData.company_password_register.length < 8) {
      errors.password_register = "Debe tener al menos 8 caracteres.";
    }

    if (
      formData.company_password_register !== formData.company_password_confirm
    ) {
      errors.password_confirm = "Las contraseñas no coinciden.";
    }

    if (!/^(DNI|RUC)$/.test(formData.company_type_doc)) {
      errors.type_doc = "Debe seleccionar el tipo de documento.";
    }

    if (!formData.company_doc.trim()) {
      errors.doc = "El número de documento es obligatorio.";
    } else {
      if (
        formData.company_type_doc === "DNI" &&
        !/^\d{8}$/.test(formData.company_doc)
      ) {
        errors.doc = "El DNI debe tener 8 dígitos.";
      } else if (
        formData.company_type_doc === "RUC" &&
        !/^\d{11}$/.test(formData.company_doc)
      ) {
        errors.doc = "El RUC debe tener 11 dígitos.";
      }
    }

    setFormErrorRegisterComapany(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = useCallback(
    (
      e: React.ChangeEvent<
        HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
      >
    ) => {
      e.preventDefault();
      const { name, value } = e.target;
      setFormData((prev) => ({ ...prev, [name]: value }));
    },
    []
  );

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
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        Login(email, password);
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

  //-----------HandleRegisterNatural-----------//
  const handleRegisterNatural = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!validateRegisterNatural()) {
      return;
    }

    const name = (
      document.querySelector('input[name="name"]') as HTMLInputElement
    ).value;
    const lastName = (
      document.querySelector('input[name="last_name"]') as HTMLInputElement
    ).value;
    const gender = (
      document.querySelector('select[name="gender"]') as HTMLSelectElement
    ).value;
    const phone1 = (
      document.querySelector(
        'input[name="natural_telephone1"]'
      ) as HTMLInputElement
    ).value;
    const phone2 = (
      document.querySelector(
        'input[name="natural_telephone2"]'
      ) as HTMLInputElement
    ).value;

    const birthDay = (
      document.querySelector('input[name="birthDay"]') as HTMLInputElement
    ).value;
    const email = (
      document.querySelector(
        'input[name="natural_email_register"]'
      ) as HTMLInputElement
    ).value;
    const documentType = (
      document.querySelector(
        'select[name="natural_type_doc"]'
      ) as HTMLSelectElement
    ).value;
    const documentNumber = (
      document.querySelector('input[name="natural_doc"]') as HTMLInputElement
    ).value;
    const password = (
      document.querySelector(
        'input[name="natural_password_register"]'
      ) as HTMLInputElement
    ).value;
    const passwrod_confirm = (
      document.querySelector(
        'input[name="natural_password_confirm"]'
      ) as HTMLInputElement
    ).value;
    const role = "CLIENT";
    try {
      if (password !== passwrod_confirm) {
        throw new Error("Las contraseñas no coinciden");
      }

      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },

        body: JSON.stringify({
          name,
          lastName,
          phone1,
          phone2,
          gender,
          birthDay,
          email,
          documentType,
          documentNumber,
          password,
          role,
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

  //-----------HandleRegisterCompany-----------//
  const handleRegisterCompany = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!validateRegisterCompany()) {
      return;
    }
    const nameReasonSoc = (
      document.querySelector('input[name="nameReasonSoc"]') as HTMLInputElement
    ).value;
    const address = (
      document.querySelector('input[name="address"]') as HTMLInputElement
    ).value;
    const reference = (
      document.querySelector('input[name="reference"]') as HTMLInputElement
    ).value;
    const phone1 = (
      document.querySelector(
        'input[name="company_telephone1"]'
      ) as HTMLInputElement
    ).value;
    const phone2 = (
      document.querySelector(
        'input[name="company_telephone2"]'
      ) as HTMLInputElement
    ).value;
    const email = (
      document.querySelector(
        'input[name="company_email_register"]'
      ) as HTMLInputElement
    ).value;
    const documentType = (
      document.querySelector(
        'select[name="company_type_doc"]'
      ) as HTMLSelectElement
    ).value;
    const documentNumber = (
      document.querySelector('input[name="company_doc"]') as HTMLInputElement
    ).value;
    const password = (
      document.querySelector(
        'input[name="company_password_register"]'
      ) as HTMLInputElement
    ).value;
    const passwrod_confirm = (
      document.querySelector(
        'input[name="company_password_confirm"]'
      ) as HTMLInputElement
    ).value;
    console.log(`|${password}| --- |${passwrod_confirm}|`);
    const role = "CLIENT";
    try {
      if (password !== passwrod_confirm) {
        throw new Error("Las contraseñas no coinciden");
      }

      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nameReasonSoc,
          reference,
          address,
          phone1,
          phone2,
          email,
          documentType,
          documentNumber,
          password,
          role,
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

  /*-----------------------------STATE TO MANAGE FORMS---------------------------*/
  const [isFormCompany, setIsFormCompany] = useState(0);
  //---------------Mange Register and Login Form Movement--------------//
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div
      id="page box_rear"
      className={`relative flex flex-col lg:flex-row transition-all duration-200 ease-in-out ${
        isFormCompany !== 0
          ? "w-full h-auto lg:w-[1400px] lg:h-[560px]"
          : "BOX_REAR"
      }  rounded-[40px] lg:rounded-[80px] m-10 md:m-auto py-4 lg:py-0
    shadow-[0px_8px_80px_rgba(0,0,0,0.2)] lg:shadow-[0px_15px_150px_rgba(0,0,0,0.3)] max-w-[95%] sm:max-w-[90%] md:max-w-[80%] lg:max-w-[1300px]`}
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
                    cleanInputs();
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
                          cleanInputs;
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
            className={`flex flex-col py-2 gap-8 rounded-r-[80px]`}
          >
            <section className="relative w-full pt-2 px-2 z-20">
              <div className="absolute left-0 top-0">
                <Button
                  type="button"
                  className={`shadow-none`}
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                  onClick={() => {
                    {
                      cleanInputs();
                      isFormCompany == 0
                        ? setIsLogin(true)
                        : setIsFormCompany(0);
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

            <section className="flex items-center w-full h-full z-10">
              <form
                onSubmit={
                  isFormCompany === 1
                    ? handleRegisterNatural
                    : isFormCompany === 2
                    ? handleRegisterCompany
                    : () => console.log("Tipo de Formulario no válido")
                }
                className={`flex flex-col relative w-full items-center ${
                  isFormCompany === 0 ? "gap-6" : "gap-0"
                }`}
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
                  <div className="flex flex-col relative items-center justify-center mx-[5vh] gap-2">
                    <p
                      className={`SMALL font-light lg:mt-2 w-auto text-[#A1B0BF] text-justify`}
                    >
                      Regístrate para acceder a nuestras soluciones
                    </p>
                    <p className="MEDIUM font-medium  text-[#A1B0BF] ">
                      !Crea una cuenta Empresarial o Personal tu Elige!
                    </p>
                  </div>
                </div>

                <div
                  className={`flex flex-col relative w-full items-center gap-4 lg:gap-0 ${
                    isFormCompany === 0 ? "gap-6" : ""
                  }`}
                >
                  <div className={isFormCompany === 1 ? "" : "hidden"}>
                    <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 w-full items-center gap-2 p-3">
                      <InputRegister
                        label="Nombre"
                        name="name"
                        placeholder="Nombre"
                        icon={icon_face}
                        value={formData.name}
                        error={formErrorRegisterNatural.name}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Apellido"
                        name="last_name"
                        placeholder="Apellido"
                        icon={icon_face}
                        value={formData.last_name}
                        error={formErrorRegisterNatural.last_name}
                        onChange={handleChange}
                      />
                      <div className="flex flex-col space-y-[2px] justify-center items-center relative ">
                        <label
                          htmlFor="gender"
                          className="SMALL font-light text-[#5C7085] mb-1"
                        >
                          Género
                        </label>
                        <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                          <img
                            src={icon_gender}
                            alt="icon"
                            className="logoRegister"
                          />
                          <select
                            name="gender"
                            required
                            onChange={handleChange}
                            value={formData.gender}
                            className="inputRegister"
                          >
                            <option value="Seleccionar Sexo">
                              Seleccionar Sexo
                            </option>
                            <option value="Masculino">Masculino</option>
                            <option value="Femenino">Femenino</option>
                            <option value="No especificar">
                              No especificar
                            </option>
                          </select>
                          {formErrorRegisterNatural.gender && (
                            <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                              {formErrorRegisterNatural.gender}
                            </div>
                          )}
                        </div>
                      </div>

                      <InputRegister
                        label="Teléfono"
                        name="natural_telephone1"
                        placeholder="Teléfono"
                        icon={icon_phone}
                        value={formData.natural_telephone1}
                        error={formErrorRegisterNatural.telephone1}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Teléfono Alternativo"
                        name="natural_telephone2"
                        placeholder="Teléfono adicional"
                        icon={icon_phone}
                        value={formData.natural_telephone2}
                        error={formErrorRegisterNatural.telephone2}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Fecha de Nacimiento"
                        name="birthDay"
                        placeholder="Fecha de Nacimiento"
                        icon={icon_gift}
                        value={formData.birthDay}
                        error={formErrorRegisterNatural.birthDay}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Correo Electrónico"
                        name="natural_email_register"
                        placeholder="Correo Electrónico"
                        icon={icon_mail}
                        value={formData.natural_email_register}
                        error={formErrorRegisterNatural.email_register}
                        onChange={handleChange}
                      />
                      <div className="flex flex-col space-y-[2px] justify-center items-center relative ">
                        <label
                          htmlFor="natural_type_doc"
                          className="SMALL font-light text-[#5C7085] mb-1"
                        >
                          Tipo de Documento
                        </label>
                        <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                          <img
                            src={icon_doc}
                            alt="icon"
                            className="logoRegister"
                          />
                          <select
                            name="natural_type_doc"
                            required
                            value={formData.natural_type_doc}
                            onChange={handleChange}
                            className="inputRegister"
                          >
                            <option value="Seleccionar Doc">
                              Seleccionar Doc
                            </option>
                            <option value="DNI">DNI</option>
                            <option value="RUC">RUC</option>
                          </select>
                          {formErrorRegisterNatural.type_doc && (
                            <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                              {formErrorRegisterNatural.type_doc}
                            </div>
                          )}
                        </div>
                      </div>

                      <InputRegister
                        label={
                          formData.natural_type_doc === "RUC"
                            ? "Número de RUC"
                            : "Número de DNI"
                        }
                        name="natural_doc"
                        placeholder={
                          formData.natural_type_doc === "RUC"
                            ? "Ingrese su RUC (11 dígitos)"
                            : "Ingrese su DNI (8 dígitos)"
                        }
                        icon={icon_doc}
                        value={formData.natural_doc}
                        error={formErrorRegisterNatural.doc}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Contraseña"
                        name="natural_password_register"
                        type="password"
                        placeholder="Contraseña"
                        icon={icon_pass_out}
                        value={formData.natural_password_register}
                        error={formErrorRegisterNatural.password_register}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Confirmar Contraseña"
                        name="natural_password_confirm"
                        type="password"
                        placeholder="Confirmar contraseña"
                        icon={icon_pass_vfy}
                        value={formData.natural_password_confirm}
                        error={formErrorRegisterNatural.password_confirm}
                        onChange={handleChange}
                      />
                    </section>
                  </div>
                  <div className={isFormCompany === 2 ? "" : "hidden"}>
                    <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 w-full items-center gap-2 p-3">
                      <InputRegister
                        label="Nombre / Razon Social"
                        name="nameReasonSoc"
                        placeholder="Nombre / Razon S."
                        icon={icon_face}
                        value={formData.nameReasonSoc}
                        error={formErrorRegisterCompany.nameReasonSoc}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Dirección"
                        name="address"
                        placeholder="Direccion"
                        icon={icon_face}
                        value={formData.address}
                        error={formErrorRegisterCompany.address}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Referencia"
                        name="reference"
                        placeholder="Referencia"
                        icon={icon_face}
                        value={formData.reference}
                        error={formErrorRegisterCompany.reference}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Teléfono"
                        name="company_telephone1"
                        placeholder="Teléfono"
                        icon={icon_phone}
                        value={formData.company_telephone1}
                        error={formErrorRegisterCompany.telephone1}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Teléfono Alternativo"
                        name="company_telephone2"
                        placeholder="Teléfono adicional"
                        icon={icon_phone}
                        value={formData.company_telephone2}
                        error={formErrorRegisterCompany.telephone2}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Correo Electrónico"
                        name="company_email_register"
                        placeholder="Correo Electrónico"
                        icon={icon_mail}
                        value={formData.company_email_register}
                        error={formErrorRegisterCompany.email_register}
                        onChange={handleChange}
                      />
                      <div className="flex flex-col space-y-[2px] justify-center items-center relative ">
                        <label
                          htmlFor="company_type_doc"
                          className="SMALL font-light text-[#5C7085] mb-1"
                        >
                          Tipo de Documento
                        </label>
                        <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
                          <img
                            src={icon_doc}
                            alt="icon"
                            className="logoRegister"
                          />
                          <select
                            name="company_type_doc"
                            required
                            onChange={handleChange}
                            value={formData.company_type_doc}
                            className="inputRegister"
                          >
                            <option value="Seleccionar Doc">
                              Seleccionar Doc
                            </option>
                            <option value="DNI">DNI</option>
                            <option value="RUC">RUC</option>
                          </select>
                          {formErrorRegisterCompany.type_doc && (
                            <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
                              {formErrorRegisterCompany.type_doc}
                            </div>
                          )}
                        </div>
                      </div>

                      <InputRegister
                        label={
                          formData.company_type_doc === "RUC"
                            ? "Número de RUC"
                            : "Número de DNI"
                        }
                        name="company_doc"
                        placeholder={
                          formData.company_type_doc === "RUC"
                            ? "Ingrese su RUC (11 dígitos)"
                            : "Ingrese su DNI (8 dígitos)"
                        }
                        icon={icon_doc}
                        value={formData.company_doc}
                        error={formErrorRegisterCompany.doc}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Contraseña"
                        name="company_password_register"
                        type="password"
                        placeholder="Contraseña"
                        icon={icon_pass_out}
                        value={formData.company_password_register}
                        error={formErrorRegisterCompany.password_register}
                        onChange={handleChange}
                      />
                      <InputRegister
                        label="Confirmar Contraseña"
                        name="company_password_confirm"
                        type="password"
                        placeholder="Confirmar contraseña"
                        icon={icon_pass_vfy}
                        value={formData.company_password_confirm}
                        error={formErrorRegisterCompany.password_confirm}
                        onChange={handleChange}
                      />
                    </section>
                  </div>

                  <section className="flex w-full justify-end px-12">
                    <div>
                      <p
                        className={`EXTRA_SMALL font-light cursor-pointer `}
                        onClick={() => {
                          {
                            {
                              cleanInputs();
                              setIsLogin(true);
                              setIsFormCompany(0);
                            }
                          }
                        }}
                      >
                        ¿Yá tienes una cuenta creada?
                      </p>
                    </div>
                  </section>
                  <section
                    className={`${
                      isFormCompany !== 0 && "hidden"
                    } flex w-full justify-center space-x-9 mt-[0.6rem]`}
                  >
                    <div className="flex gap-10">
                      <Button
                        id="showFormNaturalButton"
                        type="button"
                        className={`logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r from-[#ff7c7c] to-[#ff002b]`}
                        placeholder={undefined}
                        onClick={() => {
                          setIsFormCompany(1);
                        }}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                      >
                        Personal
                      </Button>
                      <Button
                        id="showFormCompanyButton"
                        type="button"
                        className={`logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r from-[#A1B0BF] to-[#5C7085]`}
                        placeholder={undefined}
                        onClick={() => {
                          setIsFormCompany(2);
                        }}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                      >
                        Empresarial
                      </Button>
                    </div>
                  </section>
                  <section
                    className={`${
                      isFormCompany === 0 && "hidden"
                    } flex w-full justify-center space-x-9`}
                  >
                    <div className="flex gap-10">
                      <Button
                        id="ButtonRegister"
                        type="submit"
                        className={`{${
                          isFormCompany === 0 && "hidden"
                        } flex gap-10} logButton SMALL_MUL font-bold flex items-center justify-center bg-gradient-to-r from-[#ff7c7c] to-[#ff002b]`}
                        placeholder={undefined}
                        onClick={() => {}}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                      >
                        Crear Cuenta
                      </Button>
                    </div>
                  </section>
                </div>
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
