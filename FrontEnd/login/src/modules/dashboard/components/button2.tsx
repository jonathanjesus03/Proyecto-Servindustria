import { Button } from "@material-tailwind/react";

type Props = {
  value: string;
  id: string;
  type: "button" | "submit" | "reset";
  color: string;
  onClick?: () => void;
};

function button1({ onClick, value, id, type, color }: Props) {
  return (
    <div>
      <Button
        id={id}
        type={type}
        className={`w-60 h-12 font-semibold text-gray-100 rounded-md ${color}`}
        onClick={onClick}
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        {value}
      </Button>
    </div>
  );
}

export default button1;
