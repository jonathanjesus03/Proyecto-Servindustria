import { Size } from "../../modules/auth/styles/style";

type Props = {};

const CheckBox = ({}: Props) => {
  return (
    <div>
      <input type="checkbox" id="cb" className="p-3 cursor-pointer" />
      <label
        htmlFor="cb"
        className={`${Size.EXTRA_SMALL} font-light m-1.5 cursor-pointer`}
      >
        Recuerdame
      </label>
    </div>
  );
};

export default CheckBox;
