import { useState } from "react";
import Navbar from "../components/navbar/Navbar";
import emailjs from "emailjs-com";
type Props = {};

const Quote = ({}: Props) => {
  const [formData, setFormData] = useState({
    nombre: "",
    correo: "",
    producto: "",
    cantidad: "",
    observaciones: "",
  });

  type FormError = {
    nombre?: string;
    correo?: string;
    producto?: string;
    cantidad?: string;
    observaciones?: string;
  };

  const [formError, setFormError] = useState<FormError>({});

  const validate = (): boolean => {
    const errors: FormError = {};

    if (!/^\d+$/.test(formData.cantidad)) {
      errors.cantidad = "Ingrese una cantidad válida.";
    }

    if (
      !/^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$/.test(
        formData.correo
      )
    ) {
      errors.correo = "Ingrese un correo válido.";
    }

    if (!formData.nombre.trim()) {
      errors.nombre = "El correo electrónico es obligatorio.";
    }

    if (!formData.observaciones.trim()) {
      errors.nombre = "El correo electrónico es obligatorio.";
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
        "template_nhwbjeq",
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
    alert("Cotización enviado correctamente!");
  };

  return (
    <div id="quote" className="page">
      <Navbar />
      <div className="min-h-screen pt-5 px-10 ">
        <section className="flex flex-col items-center gap-4">
          <h1 className="text-4xl font-semibold text-[#FE3051]">
            Cotiza Ahora!
          </h1>
          <p className="max-w-[850px] font-light text-center text-[#8291a3]">
            Ingresa tus datos y comenzaremos la cotización junto con el envio de
            tus productos por el medio mas fácil para ti.
          </p>
        </section>

        {/*Box*/}

        <section className="flex justify-center p-10">
          <div className=" w-full lg:w-3/4 p-8 rounded-[30px] bg-gradient-to-b from-[#FFFFFF] to-[#aaabac] shadow-2xl border border-gray-300">
            <h2 className="text-2xl font-bold text-[#f10028] mb-6 text-center">
              Solicita tu Cotización
            </h2>

            <form className="space-y-6" onSubmit={handleSubmit}>
              {/* Nombre del cliente */}
              <div className="flex flex-col">
                <label
                  htmlFor="nombre"
                  className="text-sm font-semibold text-[#f10028]"
                >
                  Nombre completo
                </label>
                <input
                  id="nombre"
                  name="nombre"
                  type="text"
                  value={formData.nombre}
                  onChange={handleChange}
                  required
                  placeholder="Ej: Juan Pérez"
                  className="rounded-xl border border-gray-300 p-3 text-gray-800 focus:outline-none focus:ring-2 focus:ring-[#FF6074]"
                />
              </div>

              {/* Correo */}
              <div className="flex flex-col">
                <label
                  htmlFor="correo"
                  className="text-sm font-semibold text-[#f10028]"
                >
                  Correo electrónico
                </label>
                <input
                  id="correo"
                  name="correo"
                  type="email"
                  value={formData.correo}
                  onChange={handleChange}
                  required
                  placeholder="Ej: juan@example.com"
                  className="rounded-xl border border-gray-300 p-3 text-gray-800 focus:outline-none focus:ring-2 focus:ring-[#FF6074]"
                />
              </div>

              {/* Producto/Servicio a cotizar */}
              <div className="flex flex-col">
                <label
                  htmlFor="producto"
                  className="text-sm font-semibold text-[#f10028]"
                >
                  Producto o servicio a cotizar
                </label>
                <select
                  id="producto"
                  name="producto"
                  required
                  value={formData.producto}
                  onChange={handleChange}
                  className="rounded-xl border border-gray-300 p-3 text-gray-800 focus:outline-none focus:ring-2 focus:ring-[#FF6074]"
                >
                  <option value="">Selecciona una opción</option>
                  <option value="extintor">Extintor PQS</option>
                  <option value="certificado">Certificación</option>
                  <option value="capacitacion">Capacitación</option>
                  <option value="plano">Confección de plano</option>
                  {/*mapear opciones dinámicamente desde el backend */}
                </select>
              </div>

              {/* Cantidad */}
              <div className="flex flex-col">
                <label
                  htmlFor="cantidad"
                  className="text-sm font-semibold text-[#f10028]"
                >
                  Cantidad
                </label>
                <input
                  id="cantidad"
                  name="cantidad"
                  type="number"
                  value={formData.cantidad}
                  onChange={handleChange}
                  min={1}
                  required
                  className="rounded-xl border border-gray-300 p-3 text-gray-800 focus:outline-none focus:ring-2 focus:ring-[#FF6074]"
                />
              </div>

              {/* Observaciones */}
              <div className="flex flex-col">
                <label
                  htmlFor="observaciones"
                  className="text-sm font-semibold text-[#f10028]"
                >
                  Observaciones (opcional)
                </label>
                <textarea
                  id="observaciones"
                  name="observaciones"
                  rows={4}
                  value={formData.observaciones}
                  onChange={handleChange}
                  placeholder="Especificaciones o detalles adicionales..."
                  className="rounded-xl border border-gray-300 p-3 text-gray-800 focus:outline-none focus:ring-2 focus:ring-[#FF6074]"
                ></textarea>
              </div>

              {/* Botón */}
              <div className="flex justify-center pt-4">
                <button
                  type="submit"
                  className="px-6 py-3 bg-gradient-to-br  from-[#ff7c7c] to-[#ff002b] text-white font-bold rounded-2xl shadow-md hover:scale-105 transition"
                >
                  Solicitar Cotización
                </button>
              </div>
            </form>
          </div>
        </section>
      </div>
    </div>
  );
};

export default Quote;
