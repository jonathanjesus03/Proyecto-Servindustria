import IconButton from "../../../components/IconButton/IconButton";
import Button1 from "../../../components/Button1/button1";
import { Size, SizeBox } from "../styles/style";
import icon_back from "../../../assets/Images/Icons/filled/back_filled.png";
import icon_face from "../../../assets/Images/Icons/outline/face_id.png";
import icon_gift from "../../../assets/Images/Icons/outline/gift.png";
import icon_pass_out from "../../../assets/Images/Icons/outline/password.png";
import icon_phone from "../../../assets/Images/Icons/outline/phone.png";
import icon_mail from "../../../assets/Images/Icons/outline/mail.svg";
import icon_doc from "../../../assets/Images/Icons/outline/document.svg";
import icon_pass_vfy from "../../../assets/Images/Icons/outline/pass_verify.png";
import Input2 from "../../../components/Input2/input2";

//Manejo de authentication
import RegisterService from "../services/authService/registerService";

type Props = {
  onSwitch: () => void;
};

const RegisterFrom = ({ onSwitch }: Props) => {
  return (
    <div
      id="box_r"
      className={`${SizeBox.BOX_R_LOG} flex flex-col px-5 py-2 rounded-r-[80px]`}
    >
      <section className="flex justify-start w-full pt-2 px-2">
        <div className="">
          <IconButton
            style="w-[25px] max-w-[35px]"
            link="/"
            img={icon_back}
          ></IconButton>
        </div>
      </section>

      <section className="flex w-full h-full">
        <form
          onSubmit={RegisterService}
          className="flex flex-col relative w-full items-center"
        >
          <div className="flex flex-col items-center">
            <p
              className={`${Size.LARGE} font-medium  text-[#A1B0BF] leading-[0px]`}
            >
              Crear Cuenta
            </p>
            <h1 className="font-mulish text-[54px] tracking-[8px] outline-none font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
              Servindustria
            </h1>
            <div className="flex flex-col relative items-start justify-center mx-[8vh]">
              <p
                className={`${Size.SMALL} font-light lg:mt-2 w-auto text-[#A1B0BF] text-justify`}
              >
                Regístrate para acceder a nuestras soluciones
              </p>
            </div>
          </div>

          <section className="grid grid-cols-1 md:grid-cols-2 w-full items-center gap-2 p-3 pt-3">
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="name"
                className={`${Size.SMALL} font-light text-[#5C7085]`}
              >
                Nombre
              </label>
              <Input2
                name="name"
                type="text"
                placeholder="Nombre"
                img={icon_face}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="last_name"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Apellido
              </label>
              <Input2
                name="last_name"
                type="text"
                placeholder="Apellido"
                img={icon_face}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="telephone"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Telefono
              </label>

              <Input2
                name="telephone"
                type="text"
                placeholder="Teléfono"
                img={icon_phone}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="date_bithday"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Fecha de Nacimiento
              </label>

              <Input2
                name="date_bithday"
                type="text"
                placeholder="Fecha de Nacimiento"
                img={icon_gift}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="email_register"
                className={`${Size.SMALL} font-light text-[#5C7085]`}
              >
                Correo Electrónico
              </label>

              <Input2
                name="email_register"
                type="text"
                placeholder="Correo Electrónico "
                img={icon_mail}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="doc"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Documento O RUC
              </label>

              <Input2
                name="doc"
                type="text"
                placeholder="DNI / RUC"
                img={icon_doc}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="password_register"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Contraseña
              </label>

              <Input2
                name="password_register"
                type="password"
                placeholder="Contraseña"
                img={icon_pass_out}
              ></Input2>
            </div>
            <div className="flex flex-col space-y-[2px] justify-center items-center">
              <label
                htmlFor="password_confirm"
                className={`${Size.SMALL} font-light text-[#5C7085] `}
              >
                Confirma tu contraseña
              </label>

              <Input2
                name="password_confirm"
                type="password"
                placeholder="Confirmar contraseña"
                img={icon_pass_vfy}
              ></Input2>
            </div>
          </section>

          <section className="flex w-full justify-end px-5">
            <div>
              <p
                className={`${Size.EXTRA_SMALL} font-light cursor-pointer`}
                onClick={onSwitch}
              >
                ¿Yá tienes una cuenta creada?
              </p>
            </div>
          </section>
          <section className="flex w-full justify-center space-x-9 mt-[0.6rem]">
            <Button1
              id="ButtonRegister"
              type="submit"
              value="Crear Cuenta"
              color="bg-gradient-to-r from-[#FF6074] to-[#FE3051]"
            />
          </section>
        </form>
      </section>
    </div>
  );
};

export default RegisterFrom;
