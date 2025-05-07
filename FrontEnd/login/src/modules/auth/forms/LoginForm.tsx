import IconButton from "../../../components/IconButton/IconButton";
import Input1 from "../../../components/Input1/input1";
import Checkbox from "../../../components/CheckBox/CheckBox";
import Button1 from "../../../components/Button1/button1";
import { Size, SizeBox } from "../styles/style";
import icon_user from "../../../assets/Images/Icons/filled/user_filled.png";
import icon_password from "../../../assets/Images/Icons/filled/password_filled.png";
import icon_back from "../../../assets/Images/Icons/filled/back_filled.png";

//Manejo de authentication
import AuthService from "../services/authService/authService";

type Props = {
  onSwitch: () => void;
};

const LoginForm = ({ onSwitch }: Props) => {
  return (
    <div
      id="box_l"
      className={`transition_slap ${SizeBox.BOX_L_LOG} border-black flex flex-col space-y-5 py-4 px-5 rounded-l-[80px]`}
    >
      <section className="flex justify-between w-full pt-2 px-2">
        <div className="">
          <IconButton
            style="w-[25px] max-w-[35px]"
            link="/"
            img={icon_back}
          ></IconButton>
        </div>

        <div>
          <p
            id="p_register"
            className={`${Size.MEDIUM} font-light text-[#A1B0BF] cursor-pointer`}
            onClick={onSwitch}
          >
            Registrate
          </p>
        </div>
      </section>

      <section className="flex w-full h-full">
        <form
          onSubmit={AuthService}
          id="form-login"
          className="flex flex-col relative w-full items-center"
        >
          <div className="flex flex-col items-center">
            <p className={`${Size.LARGE} font-light text-[#A1B0BF]`}>
              Bienvenido a
            </p>
            <h1 className="font-mulish text-[60px] tracking-[8px] border-none outline-none font-bold bg-gradient-to-b from-[#FF6074] to-[#FE3051] bg-clip-text text-transparent">
              Servindustria
            </h1>

            <div className="flex flex-col w-auto relative items-start justify-center mx-[8vh]">
              <p className={`${Size.SMALL_MUL} font-bold text-[#A1B0BF]`}>
                Tu acceso a la seguridad confiable
              </p>
              <p
                className={`${Size.SMALL} font-light lg:mt-5 text-[#A1B0BF] text-justify`}
              >
                Accede con confianza, tu seguridad es nuestra prioridad, Inicia
                sesión y protege lo que más importa.
              </p>
            </div>
          </div>

          <section className="flex flex-col w-full items-center gap-3 p-3 px-10 pt-4">
            <Input1
              name="email"
              type="email"
              placeholder="Ingrese su correo"
              pattern_in="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-z]{2,}$"
              img={icon_user}
            ></Input1>
            <Input1
              name="password"
              type="password"
              placeholder="Ingrese su contraseña"
              pattern_in="^(?=.*[A-Z])(?=.*[a-z])(?=.*[\?\*\-\_\!\$]).{12,}$"
              img={icon_password}
            ></Input1>
            <section className="flex w-full justify-between px-12">
              <Checkbox></Checkbox>
              <div>
                <p className={`${Size.EXTRA_SMALL} font-light mt-1`}>
                  ¿Olvidaste tu contraseña?
                </p>
              </div>
            </section>
          </section>

          <section className="flex w-full justify-center space-x-9 mt-[0.6rem]">
            <Button1
              id="ButtonLogin"
              type="submit"
              value="Iniciar Sesión"
              color="bg-gradient-to-r from-[#FF6074] to-[#FE3051]"
            />
            <Button1
              id="ButtonCrearCuenta"
              type="button"
              value="Crear Cuenta"
              color="bg-gradient-to-r from-[#A1B0BF] to-[#5C7085]"
              onClick={onSwitch}
            />
          </section>
        </form>
      </section>
    </div>
  );
};

export default LoginForm;
