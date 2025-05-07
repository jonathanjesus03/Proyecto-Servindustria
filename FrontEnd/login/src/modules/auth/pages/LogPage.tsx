import { SizeBox } from "../styles/style";
//Forms
import LoginForm from "../forms/LoginForm";
import RegisterForm from "../forms/RegisterForm";
import { useState } from "react";

const img_form =
  "bg-[url(../../src/assets/Images/backgrounds/bg_right_box.png)]";

type Props = {};

const login = ({}: Props) => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div
      id="box_rear"
      className={`${
        isLogin ? "justify-start" : "justify-end"
      } flex bg-[#ffffff] ${
        SizeBox.BOX_REAR
      } rounded-[80px] m-auto shadow-[0px_15px_150px_rgba(0,0,0,0.3)]`}
    >
      {isLogin && <LoginForm onSwitch={() => setIsLogin(false)}></LoginForm>}

      <div
        className={`transition_slap ${SizeBox.BOX_L_LOG} rounded-[80px] overflow-hidden `}
      >
        <div className={`${img_form} ${SizeBox.BOX_IMG}`}></div>
      </div>

      {!isLogin && (
        <RegisterForm onSwitch={() => setIsLogin(true)}></RegisterForm>
      )}
    </div>
  );
};

export default login;
