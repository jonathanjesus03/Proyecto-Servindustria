import React, { useState } from "react";
import Navbar from "../components/navbar/Navbar";
import { Button } from "@material-tailwind/react";
import emailjs from "emailjs-com";

function Contact() {
  const [formData, setFormData] = useState({
    nombre: "",
    email: "",
    telefono: "",
    ubicacion: "",
    asunto: "",
    requerimiento: "",
  });

  type FormError = {
    nombre?: string;
    email?: string;
    ubicación?: string;
    asunto?: string;
    requerimiento?: string;
  };

  const [formError, setFormError] = useState<FormError>({});

  const validate = (): boolean => {
    const errors: FormError = {};

    if (
      !/^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$/.test(formData.email)
    ) {
      errors.email = "Ingrese un correo válido.";
    }

    if (!formData.nombre.trim()) {
      errors.nombre = "El nombre electrónico es obligatorio.";
    }

    if (!formData.asunto.trim()) {
      errors.asunto = "El asunto es obligatorio.";
    }

    if (!formData.requerimiento.trim()) {
      errors.asunto = "El requerimiento es obligatorio.";
    }
    if (!formData.ubicacion.trim()) {
      errors.nombre = "La ubicación es obligatoria.";
    }

    setFormError(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) {
      return;
    }

    emailjs
      .sendForm(
        "service_fzne03j",
        "template_bkex6rj",
        e.currentTarget as HTMLFormElement,
        "tbpX_sQ-YBU5KyYq2"
      )
      .then(
        (result) => {
          console.log("Correo Enviado " + result.text);
          alert("¡Mensaje enviado!");
        },
        (error) => {
          console.log("Error: ", error.text);
        }
      );

    console.log(formData);
    alert("Formulario enviado correctamente!");
  };

  return (
    <div id="catalog" className="page">
      <Navbar />
      <div className="min-h-screen pt-5 px-10 space-y-8 bg-gray-100">
        <section className="flex flex-col items-center gap-4">
          <h1 className="text-4xl font-semibold text-[#FE3051]">
            ¡Contáctanos!
          </h1>
          <p className="max-w-[850px] font-light text-center text-[#8291a3]">
            Si tienes alguna consulta o necesitas más información, llena el
            formulario y nos pondremos en contacto contigo.
          </p>
        </section>

        <section className="flex justify-center space-x-10">
          <div className="w-full lg:w-1/2 p-5 bg-white shadow-lg rounded-xl">
            <form onSubmit={handleSubmit} className="space-y-5">
              <div>
                <label
                  htmlFor="nombre"
                  className="block text-sm font-medium text-gray-700"
                >
                  Nombre/Razón Social (Importante)
                </label>
                <input
                  type="text"
                  id="nombre"
                  name="nombre"
                  value={formData.nombre}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                  placeholder="Ingresa tu nombre o razón social"
                />
              </div>

              <div>
                <label
                  htmlFor="email"
                  className="block text-sm font-medium text-gray-700"
                >
                  Su Email (Importante)
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                  placeholder="Ingresa tu correo electrónico"
                />
              </div>

              <div>
                <label
                  htmlFor="telefono"
                  className="block text-sm font-medium text-gray-700"
                >
                  Su Teléfono (Importante)
                </label>
                <input
                  type="number"
                  id="telefono"
                  name="telefono"
                  value={formData.telefono}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                  placeholder="Ingresa tu número de teléfono"
                />
              </div>

              <div>
                <label
                  htmlFor="ubicacion"
                  className="block text-sm font-medium text-gray-700"
                >
                  Seleccione su Ubicación
                </label>
                <select
                  id="ubicacion"
                  name="ubicacion"
                  value={formData.ubicacion}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                >
                  <option value="Barranco">Barranco</option>
                  <option value="Miraflores">Miraflores</option>
                  <option value="San Isidro">San Isidro</option>
                  <option value="Surco">Surco</option>
                  <option value="Cercado de Lima">Cercado de Lima</option>
                  <option value="San Martin de Porres">
                    San Martin de Porres
                  </option>
                  <option value="Comas">Comas</option>
                  <option value="Los Olivos">Los Olivos</option>
                  <option value="Puente Piedra">Puente Piedra</option>
                  <option value="Independecia">Independencia</option>
                </select>
              </div>

              <div>
                <label
                  htmlFor="asunto"
                  className="block text-sm font-medium text-gray-700"
                >
                  Asunto
                </label>
                <input
                  type="text"
                  id="asunto"
                  name="asunto"
                  value={formData.asunto}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                  placeholder="Asunto de tu mensaje"
                />
              </div>

              <div>
                <label
                  htmlFor="requerimiento"
                  className="block text-sm font-medium text-gray-700"
                >
                  Escriba su requerimiento
                </label>
                <textarea
                  id="requerimiento"
                  name="requerimiento"
                  value={formData.requerimiento}
                  onChange={handleChange}
                  required
                  className="mt-1 block w-full px-4 py-2 bg-gray-100 rounded-md border border-gray-300"
                  placeholder="Describe tu requerimiento"
                />
              </div>

              <div className="flex justify-center mt-4">
                <Button
                  type="submit"
                  className="dashButton bg-gradient-to-b from-[#FE3051] to-[#FF6074] text-white text-xl px-4 py-2 rounded-md"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  Enviar
                </Button>
              </div>
            </form>
          </div>

          {/* Mapa de Ubicación */}
          <div className="hidden lg:block w-full lg:w-1/2 bg-white shadow-lg rounded-xl space-y-6 p-6">
            <div className="text-center space-y-5">
              <h2 className="text-xl font-semibold text-[#FE3051]">Ubícanos</h2>
              <p className="text-[#8291a3] mt-2">
                Av. Tomas Valle 962, frente al instituto DIAT y condominio Los
                Portales – Los Olivos – Lima.
              </p>
            </div>
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3902.4724560845884!2d-77.07415272574336!3d-12.010960741233536!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9105ceee4588d5c7%3A0x2663b291e9aa1bd5!2sAv.%20Tom%C3%A1s%20Valle%20962%2C%20Los%20Olivos%2015302%2C%20Per%C3%BA!5e0!3m2!1ses!2sus!4v1746857831969!5m2!1ses!2sus"
              width="100%"
              height="300"
              frameBorder="0"
              style={{ border: 0 }}
              allowFullScreen={undefined}
              aria-hidden="false"
              tabIndex={undefined}
            ></iframe>
          </div>
        </section>
      </div>

      <section className="bg-gray-200 py-4">
        <div className="flex justify-center">
          <div className="w-full lg:w-1/2 text-center">
            <h2 className="text-xl font-bold text-[#FE3051]">
              Nuestra Oficina
            </h2>
            <p className="text-[#8291a3]">
              Dirección: Av. Tomas Valle 962, frente al instituto DIAT y
              condominio Los Portales – Los Olivos – Lima
            </p>
            <p className="text-[#8291a3]">Teléfono: 533-0912</p>
            <p className="text-[#8291a3]">Email: servinidurojas@gmail.com</p>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Contact;
